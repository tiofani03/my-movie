package com.tiooooo.mymovie.helper

import androidx.paging.PagingData
import com.tiooooo.data.movie.api.model.list.MovieResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

val dummyMovie = MovieResult(
    adult = randomBoolean(),
    backdropPath = randomString(),
    id = randomLong(),
    originalLanguage = randomString(),
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

fun generateDummyMoviePagingData(): Flow<PagingData<MovieResult>> {
    return flow {
        emit(PagingData.from(List(4) { dummyMovie }))
    }
}
