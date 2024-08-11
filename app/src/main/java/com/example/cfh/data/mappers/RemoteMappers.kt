package com.example.cfh.data.mappers

import com.example.cfh.data.models.LocalMovieModel
import com.example.cfh.data.models.RemoteMovieModel
import com.example.cfh.data.models.RemoteResult
import com.example.cfh.domain.models.MovieModel
import com.example.cfh.domain.models.MovieResult


fun RemoteMovieModel.toDomain(): MovieModel {
    return MovieModel(
        page = page,
        movieResults = results?.map {
            it.toDomain()
        },
        total_pages = total_pages
    )
}

fun RemoteResult.toDomain(): MovieResult {
    return MovieResult(
        id = id ?: 0,
        original_title = original_title,
        overview = overview,
        popularity = popularity,
        poster_path = poster_path,
        release_date = release_date,
        title = title,
        vote_average = vote_average,
        vote_count = vote_count
    )
}

fun MovieResult.toLocal(type: String): LocalMovieModel {

    return LocalMovieModel(
        type = type,
        id = id,
        original_title = original_title,
        overview = overview,
        popularity = popularity,
        poster_path = poster_path,
        release_date = release_date,
        title = title,
        vote_average = vote_average,
        vote_count = vote_count
    )
}

fun LocalMovieModel.toDomain(): MovieResult {
    return MovieResult(
        id = id ?: 0,
        original_title = original_title,
        overview = overview,
        popularity = popularity,
        poster_path = poster_path,
        release_date = release_date,
        title = title,
        vote_average = vote_average,
        vote_count = vote_count
    )
}