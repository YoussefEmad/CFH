package com.example.cfh.di

import com.example.cfh.data.repository.MovieRepository
import com.example.cfh.data.source.local.MovieDao
import com.example.cfh.data.source.remote.IRemoteDataSource
import com.example.cfh.data.source.remote.MovieApi
import com.example.cfh.data.source.remote.RemoteDataSource
import com.example.cfh.domain.IMovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@InstallIn(ViewModelComponent::class)
@Module
object RepoModule {


    @Provides
    fun provideRemoteDateSource(movieApi: MovieApi): IRemoteDataSource {
        return RemoteDataSource(movieApi)
    }
    @Provides
    fun provideMovieRepository(iRemoteDataSource: IRemoteDataSource, moveDao: MovieDao): IMovieRepository {
        return MovieRepository(iRemoteDataSource,moveDao)
    }



}