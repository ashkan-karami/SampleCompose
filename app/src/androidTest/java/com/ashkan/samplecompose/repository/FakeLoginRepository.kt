package com.ashkan.samplecompose.repository

import com.ashkan.samplecompose.data.core.apiWrapper
import com.ashkan.samplecompose.data.model.login.LoginResponseModel
import com.ashkan.samplecompose.data.repository.login.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeLoginRepository: LoginRepository {

    override fun login(email: String, password: String): Flow<Result<LoginResponseModel>> =
        flow {
            emit(
                Result.success(LoginResponseModel("",""))
            )
        }
}