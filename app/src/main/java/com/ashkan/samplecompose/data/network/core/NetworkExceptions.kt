package com.ashkan.samplecompose.data.network.core

abstract class NetworkExceptionsBase(
    override val message: String? = null,
    override val cause: Throwable? = null
) :Throwable()


sealed class NetworkExceptions : NetworkExceptionsBase(){
    class IOException(override var message: String? = null , override var cause: Throwable? = null) :NetworkExceptions()
    class TimeOutException(override var message: String? = null , override var cause: Throwable? = null) :NetworkExceptions()
    class BadRequestException(override var message: String? = null , override var cause: Throwable? = null) :NetworkExceptions()
    class UnauthorizedException(override var message: String? = null, override var cause: Throwable? = null) :NetworkExceptions()
    class InternalServerError(override var message: String? = null , override var cause: Throwable? = null) :NetworkExceptions()
    class ServerStatusFalse(override var message: String? = null , override var cause: Throwable? = null) :NetworkExceptions()
    class UnExpectedException(override var message: String? = null , override var cause: Throwable? = null) :NetworkExceptions()
    class VpnUsingException(override var message: String? = null , override var cause: Throwable? = null) :NetworkExceptions()
}


