package com.tiooooo.mymovie.pages.detail.movie

import app.cash.turbine.test
import com.tiooooo.core.network.data.States
import com.tiooooo.data.movie.api.model.casts.Cast
import com.tiooooo.data.movie.api.model.detail.MovieDetail
import com.tiooooo.data.movie.api.model.review.MovieReview
import com.tiooooo.data.movie.api.model.video.MovieVideo
import com.tiooooo.data.movie.api.repository.MovieRepository
import com.tiooooo.mymovie.helper.CoroutineTestRule
import com.tiooooo.mymovie.helper.randomBoolean
import com.tiooooo.mymovie.helper.randomDouble
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
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class DetailMovieViewModelTest {

    @get:Rule
    var dispatcherRule = CoroutineTestRule()

    @get:Rule
    var mockkRule = MockKRule(this)

    @MockK
    lateinit var movieRepository: MovieRepository

    private lateinit var viewModel: DetailMovieViewModel

    @Before
    fun setUp() {
        viewModel = DetailMovieViewModel(
            movieRepository = movieRepository,
        )
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test get movie detail`() = runTest {
        val movieId = randomString()

        val data = MovieDetail(
            backdropPath = randomString(),
            genres = List(3) { randomString() },
            id = randomInt(),
            originalTitle = randomString(),
            overview = randomString(50),
            popularity = randomDouble(),
            posterPath = randomString(),
            releaseDate = randomString(),
            title = randomString(),
            video = randomBoolean(),
            voteAverage = randomDouble(),
            voteCount = randomInt()
        )

        coEvery {
            movieRepository.getDetailMovie(movieId)
        } returns flow {
            emit(States.Loading)
            emit(States.Success(data))
        }

        viewModel.movie.test {
            viewModel.getMovieDetail(movieId)
            assert(awaitItem() is States.Loading)
            assert(awaitItem() is States.Success)
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `test getCasts`() = runTest {
        val movieId = randomString()
        val data = List(4) {
            Cast(
                adult = randomBoolean(),
                gender = randomInt(),
                id = randomInt(),
                knownForDepartment = randomString(),
                name = randomString(),
                originalName = randomString(),
                popularity = randomDouble(),
                profilePath = randomString(),
                castId = randomInt(),
                character = randomString(),
                creditId = randomString(),
                order = randomInt(),
            )
        }
        coEvery {
            movieRepository.getMovieCasts(movieId)
        } returns flow {
            emit(States.Loading)
            emit(States.Success(data))
        }

        viewModel.cast.test {
            viewModel.getMovieCast(movieId)
            assert(awaitItem() is States.Loading)
            assert(awaitItem() is States.Success)
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `test get movie review`() = runTest {
        val movieId = randomString()
        val data = List(5) {
            MovieReview(
                author = randomString(),
                authorAva = randomString(),
                content = randomString(),
                createdAt = randomString(),
                id = randomString(),
                updatedAt = randomString(),
                url = randomString(),
            )
        }

        coEvery {
            movieRepository.getMovieReviews(movieId)
        } returns flow {
            emit(States.Loading)
            emit(States.Success(data))
        }

        viewModel.review.test {
            viewModel.getMovieReview(movieId)
            assert(awaitItem() is States.Loading)
            assert(awaitItem() is States.Success)
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `test get movie video`() = runTest {
        val movieId = randomString()
        val data = List(4) {
            MovieVideo(
                id = randomString(),
                key = randomString(),
                name = randomString(),
                site = randomString(),
            )
        }
        coEvery {
            movieRepository.getMovieVideos(movieId)
        } returns flow {
            emit(States.Loading)
            emit(States.Success(data))
        }

        viewModel.videos.test {
            viewModel.getMovieVideo(movieId)
            assert(awaitItem() is States.Loading)
            assert(awaitItem() is States.Success)
            ensureAllEventsConsumed()
        }
    }
}
