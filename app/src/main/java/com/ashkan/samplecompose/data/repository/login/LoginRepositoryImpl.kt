package com.ashkan.samplecompose.data.repository.login

import com.ashkan.samplecompose.data.api.LoginApiService
import com.ashkan.samplecompose.data.core.apiWrapper
import com.ashkan.samplecompose.data.model.login.LoginResponseModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val apiService: LoginApiService
): LoginRepository {

    override fun login(email: String, password: String): Flow<Result<LoginResponseModel>> =
        apiWrapper {
            apiService.login(
                email = email,
                password = password
            )
        }
}