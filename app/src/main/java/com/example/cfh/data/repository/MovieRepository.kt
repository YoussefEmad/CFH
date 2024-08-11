package com.example.cfh.data.repository

import com.example.cfh.data.mappers.toDomain
import com.example.cfh.data.models.LocalMovieModel
import com.example.cfh.data.source.local.MovieDao
import com.example.cfh.data.source.remote.IRemoteDataSource
import com.example.cfh.domain.IMovieRepository
import com.example.cfh.domain.models.MovieModel
import com.example.cfh.domain.utils.Resource
import javax.inject.Inject

class MovieRepository @Inject constructor(val remoteDataSource: IRemoteDataSource, val moveDao: MovieDao)
    : IMovieRepository {
    override suspend fun getMovies(endpoint: String,page: Int): Resource<MovieModel> {
        val movieData = remoteDataSource.getMovies(endpoint,page)
         return if (movieData.isSuccess()){
           Resource.success( movieData.getSuccessData().toDomain())
        }else{
            return movieData as Resource.Failure
        }
    }

    override suspend fun getMovie(id: Int): Resource<LocalMovieModel> {

        return Resource.success( moveDao.getMovie(id) )
    }


}