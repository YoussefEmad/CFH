package com.example.cfh.presentaion.details

import com.example.cfh.base.BaseViewModel
import com.example.cfh.domain.usecases.GetMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel

import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase,

    ) : BaseViewModel<DeatalisContract.State, DeatalisContract.Event, DeatalisContract.Action>(
    initialState = DeatalisContract.State(
        true,
        null,
        null
    )
) {
    override suspend fun handleAction(action: DeatalisContract.Action) {
        when (action) {
            is DeatalisContract.Action.GetMovie -> fetchMovie(action.id)
        }
    }

    private suspend fun fetchMovie(id: Int) {
        val movie = getMovieUseCase.getMovie(id)
        setState { copy(isLoading = false, movie = movie) }
    }
}

