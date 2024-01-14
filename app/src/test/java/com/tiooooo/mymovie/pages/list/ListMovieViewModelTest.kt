package com.tiooooo.mymovie.pages.list

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
class ListMovieViewModelTest {

    @get:Rule
    var dispatcherRule = CoroutineTestRule()

    @get:Rule
    var mockkRule = MockKRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var movieRepository: MovieRepository

    private lateinit var viewModel: ListMovieViewModel

    @Before
    fun setUp() {
        viewModel = ListMovieViewModel(
            movieRepository = movieRepository,
        )
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test get movies by type`() = runTest {
        // Mock response data
        val type = randomString()
        val dummyMovies = PagingData.from(List(4) { dummyMovie })

        // Mock repository response
        coEvery {
            movieRepository.getAllMovieByType(type)
        } returns flow {
            emit(dummyMovies)
        }

        // Call the view model function
        viewModel.getMoviesByType(type)

        // Ensure LiveData is updated
        val observer = mockk<Observer<PagingData<MovieResult>>>(relaxed = true)
        viewModel.movies.observeForever(observer)

        // Wait for the events to be consumed
        advanceUntilIdle()

        // Verify the interactions
        coVerify { movieRepository.getAllMovieByType(type) }

        // Capture argument passed to onChanged
        val slot = slot<PagingData<MovieResult>>()
        verify { observer.onChanged(capture(slot)) }

        assertThat(dummyMovies).isEqualTo(slot.captured)
    }

    @Test
    fun `test get movies by query`() = runTest {
        // Mock response data
        val query = randomString()
        val dummyMovies = PagingData.from(List(4) { dummyMovie })

        // Mock repository response
        coEvery {
            movieRepository.getMovieByQuery(query)
        } returns flow {
            emit(dummyMovies)
        }

        // Call the view model function
        viewModel.getMovieByQuery(query)

        // Ensure LiveData is updated
        val observer = mockk<Observer<PagingData<MovieResult>>>(relaxed = true)
        viewModel.movies.observeForever(observer)

        // Wait for the events to be consumed
        advanceUntilIdle()

        // Verify the interactions
        coVerify { movieRepository.getMovieByQuery(query) }

        // Capture argument passed to onChanged
        val slot = slot<PagingData<MovieResult>>()
        verify { observer.onChanged(capture(slot)) }

        assertThat(dummyMovies).isEqualTo(slot.captured)
    }
}
