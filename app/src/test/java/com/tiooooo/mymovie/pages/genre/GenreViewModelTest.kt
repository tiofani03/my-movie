package com.tiooooo.mymovie.pages.genre

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import com.google.common.truth.Truth.assertThat
import com.tiooooo.data.movie.api.model.list.MovieResult
import com.tiooooo.data.movie.api.repository.MovieRepository
import com.tiooooo.mymovie.helper.CoroutineTestRule
import com.tiooooo.mymovie.helper.dummyMovie
import com.tiooooo.mymovie.helper.randomString
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import io.mockk.slot
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class GenreViewModelTest {

    @get:Rule
    var dispatcherRule = CoroutineTestRule()

    @get:Rule
    var mockkRule = MockKRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var movieRepository: MovieRepository

    private lateinit var viewModel: GenreViewModel

    @Before
    fun setUp() {
        viewModel = GenreViewModel(
            movieRepository = movieRepository,
        )
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test get movies by genre`() = runTest {
        // Mock response data
        val genreId = randomString()
        val dummyMovies = PagingData.from(List(4) { dummyMovie })

        // Mock repository response
        coEvery {
            movieRepository.getDiscoverMovie(genreId)
        } returns flow {
            emit(dummyMovies)
        }

        // Call the view model function
        viewModel.getMoviesByType(genreId)

        // Ensure LiveData is updated
        val observer = mockk<Observer<PagingData<MovieResult>>>(relaxed = true)
        viewModel.movies.observeForever(observer)

        // Wait for the events to be consumed
        advanceUntilIdle()

        // Verify the interactions
        coVerify { movieRepository.getDiscoverMovie(genreId) }

        // Capture argument passed to onChanged
        val slot = slot<PagingData<MovieResult>>()
        verify { observer.onChanged(capture(slot)) }

        assertThat(dummyMovies).isEqualTo(slot.captured)
    }


}
