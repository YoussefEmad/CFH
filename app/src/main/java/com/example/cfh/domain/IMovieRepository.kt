package com.example.cfh.domain

import com.example.cfh.data.models.LocalMovieModel
import com.example.cfh.domain.models.MovieModel
import com.example.cfh.domain.utils.Resource

interface IMovieRepository{

    suspend fun getMovies(endpoint: String,page: Int): Resource<MovieModel>
    suspend fun getMovie(id: Int): Resource<LocalMovieModel>

}