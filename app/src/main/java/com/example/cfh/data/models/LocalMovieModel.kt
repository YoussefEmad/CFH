package com.example.cfh.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalMovieModel(
    val type : String?,
    @PrimaryKey
    val id: Int?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val release_date: String?,
    val title: String?,
    val vote_average: Double?,
    val vote_count: Int?
)
