
package com.example.cfh.presentaion.home

import androidx.paging.PagingData
import com.example.cfh.base.ViewAction
import com.example.cfh.base.ViewEvent
import com.example.cfh.base.ViewState
import com.example.cfh.data.models.LocalMovieModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow


class HomeContractor {

    sealed class Action : ViewAction {
       data class GetMovies(var Type: String) : Action()
        object NavigateToDetails : Action()

    }

    sealed class Event : ViewEvent {

        data class ShowError(val error: String) : Event()

    }

    data class State(
        val isLoading: Boolean = false,
        val nowPlayingMovies: Flow<PagingData<LocalMovieModel>> = emptyFlow(),
        val popularMovies: Flow<PagingData<LocalMovieModel>> = emptyFlow(),
        val upcomingMovies: Flow<PagingData<LocalMovieModel>> = emptyFlow()
    ) : ViewState
}
