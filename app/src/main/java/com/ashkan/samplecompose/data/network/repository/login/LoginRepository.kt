package com.ashkan.samplecompose.data.network.repository.login

import com.ashkan.samplecompose.data.network.model.login.LoginResponseModel
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    fun login(
        email: String,
        password: String
    ): Flow<Result<LoginResponseModel>>
}