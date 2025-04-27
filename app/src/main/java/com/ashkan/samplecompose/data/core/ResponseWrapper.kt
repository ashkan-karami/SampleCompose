package com.ashkan.samplecompose.data.core


inline fun <Left, reified Right> Left.wrapResponse(noinline mapper: (Left) -> Right): Result<Right> {
    return when (this) {
        is String,
        is Int,
        is Boolean,
        is Float,
        is Double -> {
            return Result.success(this as Right)
        }

        //TODO
//        is List<*> -> {
//            return Result.success(this.map { mapper(it) } as Right)
//        }

        else -> {
            return Result.success(mapper(this))
        }
    }
}