package com.tiooooo.core.network.helper

import android.util.Log
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.GsonBuilder
import com.tiooooo.core.common.ContextProvider
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkProvider(
    private val contextProvider: ContextProvider,
    private val ioDispatcher: CoroutineDispatcher,
    private val url: String,
) {

    companion object {
        private const val TAG = "NetworkModule"
    }

    fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(createOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    private fun createOkHttpClient(): OkHttpClient {
        val httpBuilder = OkHttpClient.Builder()
            .readTimeout(3, TimeUnit.MINUTES)
            .writeTimeout(3, TimeUnit.MINUTES)
            .retryOnConnectionFailure(true)
            .addInterceptor(
                HeaderInterceptor(
                    contextProvider = contextProvider,
                    ioDispatcher = ioDispatcher,
                )
            )
            .addInterceptor(getChuckerInterceptor())
            .addInterceptor(getHttpLoggingInterceptor())

        return httpBuilder.build()
    }

    private fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.d("Interceptor", message)
            }
        })
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    private fun getChuckerInterceptor(): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(contextProvider.getContext()).build()
    }

}
