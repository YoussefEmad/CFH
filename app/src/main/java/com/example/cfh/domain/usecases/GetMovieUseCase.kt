package com.example.cfh.domain.usecases

import com.example.cfh.data.models.LocalMovieModel
import com.example.cfh.domain.IMovieRepository
import com.example.cfh.domain.models.MovieModel
import com.example.cfh.domain.utils.Resource
import javax.inject.Inject

class GetMovieUseCase @Inject
constructor(private val movieRepository: IMovieRepository) {


    suspend fun getMovies(endpoint: String,page: Int): Resource<MovieModel> {
        return movieRepository.getMovies(endpoint,page)
    }

    suspend fun getMovie(id: Int): Resource<LocalMovieModel> {
        return movieRepository.getMovie(id)
    }


}