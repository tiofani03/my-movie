package com.tiooooo.core.network.helper

import android.os.Build
import com.tiooooo.core.common.ContextProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.Response
import java.text.SimpleDateFormat
import java.util.Locale

class HeaderInterceptor(
    private val contextProvider: ContextProvider,
    private val ioDispatcher: CoroutineDispatcher,
) : Interceptor {

    companion object {
        private const val HEADER_TIME_ZONE = "local_tz"
        private const val HEADER_USER_AGENT = "User-Agent"
        private const val HEADER_AUTHORIZATION = "Authorization"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val currentTimezone =
            SimpleDateFormat("Z", Locale.getDefault()).format(System.currentTimeMillis())
        val timeZone = currentTimezone.substring(1, 3).toIntOrNull() ?: 7

        val requestBuilder = request.newBuilder()
            .header(HEADER_TIME_ZONE, timeZone.toString())

        runBlocking {
            withContext(ioDispatcher) {
                requestBuilder.header(
                    HEADER_USER_AGENT,
                    getUserAgent()
                )
                requestBuilder.header(
                    HEADER_AUTHORIZATION,
                    "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2NmE5OTA1YWIyYzVjOWVmYmIyMjg3NzMxNjRhNzVhOSIsInN1YiI6IjYxZGZlOTNlZTI0YjkzMDA0MTc1MTI4NCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.sx9IXc1z3_MEeww4-dbkTdQFkDC_C8y6CmCVWGiFnKY"
                )
                requestBuilder.header("accept", "application/json")
            }
        }

        val requestBuild = requestBuilder.build()
        return chain.proceed(requestBuild)
    }

    private fun getUserAgent(): String {
        return try {
            val androidId = contextProvider.getAndroidId()
            val appVersion = "AndroidApp-"
            val manufacture = Build.MANUFACTURER
            val deviceModel = Build.MODEL
            val carrier = try {
                val telephonyManager = contextProvider.getTelephonyManager()
                telephonyManager.networkOperatorName
            } catch (e: Exception) {
                "exception"
            }

            "[android-id:$androidId]_[app-version:$appVersion]_[brand:$manufacture]_[model:$deviceModel]_[carrier:$carrier]"
        } catch (e: Exception) {
            "exception"
        }
    }
}
