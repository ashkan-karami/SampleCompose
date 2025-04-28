package com.ashkan.samplecompose.data.core

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

inline fun <Left, reified Right> apiWrapper(noinline mapper: (Left) -> Right,
                                            noinline request: suspend () -> NetworkResponse<Left>): Flow<Result<Right>> {

    return flow {
        kotlin.runCatching {
            val response = request.invoke()
            Log.i("aaaaaaaaaaaaaaa","wrapper=>" + response)
            if (response.status) {
                emit(response.data.wrapResponse(mapper))
            } else {
                emit(Result.failure(NetworkExceptions.ServerStatusFalse(response.message, null)))
            }
        }.onFailure { throwable ->
            emit(
                throwable.toNetworkExceptions<Right>()
            )
        }
    }
}
