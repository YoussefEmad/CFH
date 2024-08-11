package com.example.cfh.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieKeys(
    @PrimaryKey
    var id: Int,
    var type:String,
    var prev: Int?,
    var next: Int?
)