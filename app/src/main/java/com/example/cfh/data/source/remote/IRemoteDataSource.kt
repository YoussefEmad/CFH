package com.example.cfh.data.source.remote

import com.example.cfh.data.models.RemoteMovieModel
import com.example.cfh.domain.utils.Resource

interface IRemoteDataSource {

    suspend fun getMovies(endpoint: String,page: Int): Resource<RemoteMovieModel>

}