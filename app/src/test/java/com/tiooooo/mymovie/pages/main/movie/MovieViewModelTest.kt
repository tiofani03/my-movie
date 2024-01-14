package com.tiooooo.mymovie.pages.main.movie

import app.cash.turbine.test
import com.tiooooo.core.network.data.States
import com.tiooooo.data.movie.api.model.list.Genre
import com.tiooooo.data.movie.api.model.list.GenreList
import com.tiooooo.data.movie.api.repository.MovieRepository
import com.tiooooo.mymovie.helper.CoroutineTestRule
import com.tiooooo.mymovie.helper.dummyMovie
import com.tiooooo.mymovie.helper.randomInt
import com.tiooooo.mymovie.helper.randomString
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MovieViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var dispatcherRule = CoroutineTestRule()

    @get:Rule
    var mockkRule = MockKRule(this)

    @MockK
    lateinit var movieRepository: MovieRepository

    private lateinit var viewModel: MovieViewModel

    @Before
    fun setUp() {
        viewModel = MovieViewModel(
            movieRepository = movieRepository,
        )
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test get movie genres`() = runTest {

        val data = Genre(
            id = randomInt(),
            name = randomString(),
        )
        val dummyGenre = List(4) {
            data
        }

        val genreList = GenreList(dummyGenre)

        coEvery {
            movieRepository.getGenres()
        } returns flow {
            emit(States.Loading)
            emit(States.Success(genreList))
        }

        viewModel.genres.test {
            viewModel.getGenres()
            assert(awaitItem() is States.Loading)
            assert(awaitItem() is States.Success)
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `test get movie now playing`() = runTest {
        val type = randomString()
        val dummyData = List(4) {
            dummyMovie
        }

        coEvery {
            movieRepository.getMovieByType(type)
        } returns flow {
            emit(States.Loading)
            emit(States.Success(dummyData))
        }

        viewModel.nowPlaying.test {
            viewModel.getNowPlaying(type)
            assert(awaitItem() is States.Loading)
            assert(awaitItem() is States.Success)
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `test get movie popular`() = runTest {
        val type = randomString()
        val dummyData = List(4) {
            dummyMovie
        }

        coEvery {
            movieRepository.getMovieByType(type)
        } returns flow {
            emit(States.Loading)
            emit(States.Success(dummyData))
        }

        viewModel.popular.test {
            viewModel.getPopular(type)
            assert(awaitItem() is States.Loading)
            assert(awaitItem() is States.Success)
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `test get movie top rated`() = runTest {
        val type = randomString()
        val dummyData = List(4) {
            dummyMovie
        }

        coEvery {
            movieRepository.getMovieByType(type)
        } returns flow {
            emit(States.Loading)
            emit(States.Success(dummyData))
        }

        viewModel.topRated.test {
            viewModel.getTopRated(type)
            assert(awaitItem() is States.Loading)
            assert(awaitItem() is States.Success)
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `test get movie upcoming`() = runTest {
        val type = randomString()
        val dummyData = List(4) {
            dummyMovie
        }

        coEvery {
            movieRepository.getMovieByType(type)
        } returns flow {
            emit(States.Loading)
            emit(States.Success(dummyData))
        }

        viewModel.upComing.test {
            viewModel.getUpComing(type)
            assert(awaitItem() is States.Loading)
            assert(awaitItem() is States.Success)
            ensureAllEventsConsumed()
        }
    }
}
