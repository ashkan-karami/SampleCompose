package com.ashkan.samplecompose.repository

import com.ashkan.samplecompose.data.network.model.login.LoginResponseModel
import com.ashkan.samplecompose.data.network.repository.login.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeLoginRepository: LoginRepository {

    override suspend fun login(email: String, password: String): Flow<Result<LoginResponseModel>> =
        flow {
            emit(
                Result.success(LoginResponseModel("",""))
            )
        }
}