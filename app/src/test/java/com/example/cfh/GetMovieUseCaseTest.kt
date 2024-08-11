package com.example.cfh

import com.example.cfh.data.repository.MovieRepository
import com.example.cfh.data.models.LocalMovieModel
import com.example.cfh.domain.usecases.GetMovieUseCase
import com.example.cfh.domain.utils.Resource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@Suppress("DEPRECATION")
class GetMovieUseCaseTest {

    @Mock
    private lateinit var movieRepository: MovieRepository

    private lateinit var getMovieUseCase: GetMovieUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getMovieUseCase = GetMovieUseCase(movieRepository)
    }

    @Test
    fun `getMovie returns movie data`() = runBlocking {
        // Given
        val movieId = 1
        val expectedMovie = LocalMovieModel(
            id = movieId,
            title = "Movie Title",
            overview = "Movie Description",
            poster_path = "https://example.com/image.jpg",
            release_date = "2023-07-28",
            vote_average = 4.5,
            vote_count = 100,
            original_title = "Movie Title",
            popularity = 0.0,
            type = "Movie"
        )
        Mockito.`when`(movieRepository.getMovie(movieId))
            .thenReturn(Resource.Success(expectedMovie))

        // When
        val result = getMovieUseCase.getMovie(movieId)

        // Then
        assertEquals(expectedMovie, result.getSuccessData())
    }
}




