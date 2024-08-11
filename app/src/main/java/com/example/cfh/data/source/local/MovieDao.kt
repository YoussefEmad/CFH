package com.example.cfh.data.source.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cfh.data.models.LocalMovieModel
import com.example.cfh.data.models.MovieKeys

@Dao
interface MovieDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllBlogs(list: List<LocalMovieModel>)

    @Query("SELECT * FROM LocalMovieModel where type=:type")
    fun getAllMovie(type: String): PagingSource<Int, LocalMovieModel>

    @Query("SELECT * FROM LocalMovieModel where  id=:id")
     suspend fun getMovie(id:Int): LocalMovieModel


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllBlogKeys(l: List<MovieKeys>)


    @Query("SELECT * FROM MovieKeys WHERE id=:id and type=:type")
    suspend fun getAllKeys(id: Int,type: String): MovieKeys


    @Query("DELETE FROM MovieKeys WHERE type=:type")
    suspend fun deleteAllBlogKey(type: String)

    @Query("DELETE FROM LocalMovieModel where type=:type")
    suspend fun deleteAllItems(type: String)

}