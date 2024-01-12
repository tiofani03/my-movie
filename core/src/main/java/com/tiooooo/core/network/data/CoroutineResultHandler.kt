package com.tiooooo.core.network.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


fun <R, C> resultFlow(
    networkCall: suspend () -> ApiResponse<R>,
    convert: (R?) -> C,
): Flow<States<C>> {
    return flow {
        emit(States.Loading)
        val result = try {
            val response = networkCall.invoke()
            val data = response.data

            when {
                response.success == false -> States.Error(message = response.message ?: "")
                data == null -> States.Empty
                else -> States.Success(convert.invoke(data))
            }
        } catch (e: Exception) {
            e.toError()
        }
        emit(result)
    }.flowOn(Dispatchers.IO)
}

fun resultFlowNoData(
    networkCall: suspend () -> ApiResponseNoData,
): Flow<States<Boolean>> {
    return flow {
        emit(States.Loading)
        val result = try {
            val response = networkCall.invoke()

            when (response.success) {
                false -> States.Error(message = response.message ?: "")
                else -> States.Success(true)
            }
        } catch (e: Exception) {
            e.toError()
        }
        emit(result)
    }.flowOn(Dispatchers.IO)
}
