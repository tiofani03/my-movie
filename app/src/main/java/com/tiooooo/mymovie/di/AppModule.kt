package com.tiooooo.mymovie.di

import android.content.Context
import com.tiooooo.core.common.ContextProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    @AppScope
    fun provideAppCoroutineScope(): CoroutineScope {
        return CoroutineScope(context = SupervisorJob() + Dispatchers.Main.immediate)
    }

    @Singleton
    @Provides
    fun provideContextProvider(
        @ApplicationContext context: Context
    ): ContextProvider {
        return ContextProvider(context)
    }
}
