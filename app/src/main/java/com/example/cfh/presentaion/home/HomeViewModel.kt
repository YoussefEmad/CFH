package com.example.cfh.presentaion.home

import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.cfh.base.BaseViewModel
import com.example.cfh.data.models.LocalMovieModel
import com.example.cfh.data.source.local.MovieDao
import com.example.cfh.domain.models.MovieTypes
import com.example.cfh.domain.usecases.GetMovieUseCase
import com.example.cfh.presentaion.MovieRemoteMediator
import com.example.cfh.presentaion.home.HomeContractor.Action
import com.example.cfh.presentaion.home.HomeContractor.Event
import com.example.cfh.presentaion.home.HomeContractor.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase,
    private val movieDao: MovieDao
) : BaseViewModel<State, Event, Action>(initialState = State()){


    init {
        fetchMovies(MovieTypes.PLAYING_NOW.value)
        fetchMovies(MovieTypes.POPULAR.value)
        fetchMovies(MovieTypes.UPCOMING.value)
    }
    override suspend fun handleAction(action: HomeContractor.Action) {
        when (action) {
            is HomeContractor.Action.GetMovies -> fetchMovies(action.Type)
            HomeContractor.Action.NavigateToDetails -> { /* Navigate to details */ }
        }
    }


    private fun fetchMovies(type: String) {
        viewModelScope.launch {
            val flow = getMoviesPaging(type)
            flow.collectLatest {
                when (type) {
                    MovieTypes.PLAYING_NOW.value -> setState { copy(nowPlayingMovies = flow) }
                    MovieTypes.POPULAR.value -> setState { copy(popularMovies = flow) }
                    MovieTypes.UPCOMING.value -> setState { copy(upcomingMovies = flow) }
                }
            }
        }
    }


    fun getMoviesPaging(type:String ): Flow<PagingData<LocalMovieModel>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 10,
                initialLoadSize = 20,
                enablePlaceholders = false
            ),
            remoteMediator = MovieRemoteMediator(
                getPagerBlogsUseCase = getMovieUseCase,
                movieDao = movieDao,
                type = type
            )
        )
        {
            movieDao.getAllMovie(type)
        }.flow.cachedIn(viewModelScope)
    }
}
