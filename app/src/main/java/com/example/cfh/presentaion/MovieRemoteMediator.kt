package com.example.cfh.presentaion

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.cfh.data.mappers.toLocal
import com.example.cfh.data.models.LocalMovieModel
import com.example.cfh.data.models.MovieKeys
import com.example.cfh.data.source.local.MovieDao
import com.example.cfh.domain.usecases.GetMovieUseCase
import com.example.cfh.domain.utils.Resource
import javax.inject.Inject
@Suppress("UNREACHABLE_CODE", "NAME_SHADOWING")
@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator @Inject constructor(
    private val initialPage: Int = 1,
    private val getPagerBlogsUseCase: GetMovieUseCase,
    private val movieDao: MovieDao,
    private val type: String
) : RemoteMediator<Int, LocalMovieModel>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, LocalMovieModel>): MediatorResult {
        Log.d("RemoteMediator", "loadType: $loadType")
        return try {
            val page: Int = when (loadType) {
                LoadType.APPEND -> {
                    val remoteKeys = getLastKey(state)
                    Log.d("RemoteMediator", "LoadType.APPEND: page = ${remoteKeys?.next}")

                    remoteKeys?.next ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getFirstKey(state)
                    Log.d("RemoteMediator", "LoadType.PREPEND: page = ${remoteKeys?.prev}")

                    remoteKeys?.prev ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                LoadType.REFRESH -> 1
            }


            val response = getPagerBlogsUseCase.getMovies(type, page)
            val movies = response.getSuccessData().movieResults ?: emptyList()
            val endOfPagination = movies.isEmpty()

            Log.d("RemoteMediator", "Fetched page $page, size: ${movies.size}")

            if (response is Resource.Success) {
                if (loadType == LoadType.REFRESH) {
                    movieDao.deleteAllBlogKey(type)
                    movieDao.deleteAllItems(type)
                }

                val prevKey = if (page == initialPage) null else page - 1
                val nextKey = if (endOfPagination) null else page + 1
                val keys = response.getSuccessData().movieResults?.map {
                    MovieKeys(id = it.id, prev = prevKey, next = nextKey, type = type)
                }
                if (keys == null) {
                    keys?.let { movieDao.insertAllBlogKeys(it) }
                }
                val movies = response.getSuccessData().movieResults?.map { it.toLocal(type) }

                movies?.let { movieDao.insertAllBlogs(it) }
                Log.d("RemoteMediator", "Inserted ${movies?.size} movies with nextKey=$nextKey")

            }

            MediatorResult.Success(endOfPaginationReached = endOfPagination)
        } catch (e: Exception) {
            Log.e("RemoteMediator", "Error in load", e)
            MediatorResult.Error(e)
        }
    }

    private suspend fun getLastKey(state: PagingState<Int, LocalMovieModel>): MovieKeys? {
        return state.lastItemOrNull()?.let { movieDao.getAllKeys(it.id ?: 0,type) }
    }

//    private suspend fun getClosestKey(state: PagingState<Int, LocalMovieModel>): MovieKeys? {
//        return state.anchorPosition?.let {
//            state.closestItemToPosition(it)?.let { movieDao.getAllKeys(it.id ?: 0,type) }
//        }
//    }
    private suspend fun getFirstKey(state: PagingState<Int, LocalMovieModel>): MovieKeys? {
        return state.firstItemOrNull()?.let { movieDao.getAllKeys(it.id ?: 0,type) }
    }
}
