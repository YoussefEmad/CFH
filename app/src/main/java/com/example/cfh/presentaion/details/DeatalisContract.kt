package com.example.cfh.presentaion.details

import com.example.cfh.base.ViewAction
import com.example.cfh.base.ViewEvent
import com.example.cfh.base.ViewState
import com.example.cfh.data.models.LocalMovieModel
import com.example.cfh.domain.utils.Resource

class DeatalisContract {

    sealed class Action : ViewAction {
        data class GetMovie(val id :Int) : Action()
    }

    sealed class Event : ViewEvent {

        data class ShowError(val error: String) : Event()

    }

    data class State(
        val isLoading: Boolean = false,
        val movie: Resource<LocalMovieModel>? =null,
        val error: String? = null
    ) : ViewState

}



