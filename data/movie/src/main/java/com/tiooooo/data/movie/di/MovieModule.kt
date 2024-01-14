package com.tiooooo.data.movie.di

import com.tiooooo.core.common.ContextProvider
import com.tiooooo.core.di.IoDispatcher
import com.tiooooo.data.movie.api.repository.MovieRepository
import com.tiooooo.data.movie.implementation.local.MovieDb
import com.tiooooo.data.movie.implementation.local.dao.SearchHistoryDao
import com.tiooooo.data.movie.implementation.remote.api.MovieApi
import com.tiooooo.data.movie.implementation.repository.MovieRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieModule {

    @Provides
    @Singleton
    fun provideMovieApi(retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(
        movieApi: MovieApi,
        searchHistoryDao: SearchHistoryDao,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): MovieRepository {
        return MovieRepositoryImpl(
            movieApi = movieApi,
            searchHistoryDao = searchHistoryDao,
            ioDispatcher = ioDispatcher,
        )
    }

    @Singleton
    @Provides
    fun provideAccountDB(contextProvider: ContextProvider): MovieDb {
        return MovieDb.getInstance(contextProvider.getContext())
    }

    @Singleton
    @Provides
    fun providePhoneDao(movieDb: MovieDb) = movieDb.searchHistoryDao()


}
