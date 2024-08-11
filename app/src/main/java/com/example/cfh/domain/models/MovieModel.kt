package com.example.cfh.domain.models

data class MovieModel(
    val page: Int?,
    val movieResults: List<MovieResult>?,
    val total_pages: Int?
)

data class MovieResult(
    val id : Int,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val release_date: String?,
    val title: String?,
    val vote_average: Double?,
    val vote_count: Int?
)