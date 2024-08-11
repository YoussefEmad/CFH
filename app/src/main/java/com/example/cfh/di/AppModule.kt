package com.example.cfh.di

import android.content.Context
import androidx.room.Room
import com.example.cfh.data.source.local.AppDatabase
import com.example.cfh.data.source.local.MovieDao
import com.example.cfh.data.source.remote.MovieApi
import com.example.cfh.data.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .connectTimeout(50, TimeUnit.SECONDS)
            .writeTimeout(50, TimeUnit.SECONDS)
            .readTimeout(50, TimeUnit.SECONDS)
            .callTimeout(50, TimeUnit.SECONDS)
            .addInterceptor(Interceptor { chain ->
                val originalRequest = chain.request()
                val originalUrl = originalRequest.url
                val url = originalUrl.newBuilder()
                    .addQueryParameter("api_key", Constants.API_KEY)
                    .build()
                val requestBuilder = originalRequest.newBuilder().url(url)
                    .addHeader("Accept", "application/json")
                val request = requestBuilder.build()
                val response = chain.proceed(request)

                response
            })
            .build()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl(Constants.BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }
    @Singleton
    @Provides
    fun provideRoomDatabase (@ApplicationContext context: Context) : AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "movie_database"
        ).fallbackToDestructiveMigration().build()
    }
    @Singleton
    @Provides
    fun provideMovieDao (appDatabase : AppDatabase) : MovieDao {
        return appDatabase.movieDao()
    }


}