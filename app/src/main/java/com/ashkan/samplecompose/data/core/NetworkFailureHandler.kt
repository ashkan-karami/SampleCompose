package com.ashkan.samplecompose.data.core

import kotlinx.coroutines.TimeoutCancellationException
import retrofit2.HttpException
import java.io.IOException


fun <Output> Throwable.toNetworkExceptions(): Result<Output> {
    return Result.failure(
        when (this) {
            is IOException -> {
                NetworkExceptions.IOException(
                    this.message,
                    this
                )
            }
            is TimeoutCancellationException -> {
                NetworkExceptions.TimeOutException(
                    this.message,
                    this
                )
            }
            is HttpException -> {
                when (this.code()) {
                    500 -> {
                        NetworkExceptions.InternalServerError(
                            this.message,
                            this
                        )
                    }
                    401 -> {
                        NetworkExceptions.UnauthorizedException(
                            this.message,
                            this
                        )
                    }
                    400 -> {
                        NetworkExceptions.BadRequestException(
                            this.message,
                            this
                        )
                    }
                    403 -> {
                        NetworkExceptions.VpnUsingException(
                            this.message,
                            this
                        )
                    }
                    else -> NetworkExceptions.UnExpectedException(
                        "unexpected exception occur",
                        this
                    )
                }
            }
            else -> {
                NetworkExceptions.UnExpectedException(
                    "unexpected exception occur",
                    this
                )
            }
        }
    )
}