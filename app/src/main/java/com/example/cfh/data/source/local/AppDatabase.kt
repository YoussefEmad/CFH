package com.example.cfh.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cfh.data.models.LocalMovieModel
import com.example.cfh.data.models.MovieKeys

@Database(entities = [LocalMovieModel::class , MovieKeys::class], version = 1  , exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}