package com.tiooooo.data.movie.api.repository

import com.tiooooo.core.network.data.States
import com.tiooooo.data.movie.api.model.GenreList
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getGenres(): Flow<States<GenreList>>
}
