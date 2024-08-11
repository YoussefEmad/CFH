package com.example.cfh


import androidx.paging.*
import com.example.cfh.data.models.LocalMovieModel
import com.example.cfh.data.source.local.MovieDao
import com.example.cfh.domain.models.MovieModel
import com.example.cfh.domain.models.MovieResult
import com.example.cfh.domain.usecases.GetMovieUseCase
import com.example.cfh.domain.utils.Resource
import com.example.cfh.presentaion.MovieRemoteMediator

import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.HttpException

@Suppress("DEPRECATION")
class MovieRemoteMediatorTest {

    @Mock
    private lateinit var getMovieUseCase: GetMovieUseCase

    @Mock
    private lateinit var movieDao: MovieDao

    private lateinit var movieRemoteMediator: MovieRemoteMediator

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        movieRemoteMediator = MovieRemoteMediator(1,getMovieUseCase, movieDao, "popular")
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun `load REFRESH load type returns success result`() = runBlocking {
        // Given
        val pagingState = PagingState<Int, LocalMovieModel>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 20),
            leadingPlaceholderCount = 0
        )

        Mockito.`when`(getMovieUseCase.getMovies("popular", 1)).thenReturn(
            Resource.Success(
            MovieModel(
                page = 1,
                movieResults = listOf( MovieResult(
                    id = 1,
                    title = "Movie Title",
                    overview = "Movie Description",
                    poster_path = "https://example.com/image.jpg",
                    release_date = "2023-07-28",
                    vote_average = 4.5,
                    vote_count = 100,
                    original_title = "Movie Title",
                    popularity = 0.0,
                     )
                ) ,
                total_pages = 20

        )
        ))

        // When
        val result = movieRemoteMediator.load(LoadType.REFRESH, pagingState)

        // Then
        assert(result is RemoteMediator.MediatorResult.Success)
        assert(!(result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun `load REFRESH load type returns error result`() = runBlocking {
        // Given
        val pagingState = PagingState<Int, LocalMovieModel>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 20),
            leadingPlaceholderCount = 0
        )

        Mockito.`when`(getMovieUseCase.getMovies("popular", 1))
            .thenThrow(HttpException::class.java)

        // When
        val result = movieRemoteMediator.load(LoadType.REFRESH, pagingState)

        // Then
        assert(result is RemoteMediator.MediatorResult.Error)
    }
}