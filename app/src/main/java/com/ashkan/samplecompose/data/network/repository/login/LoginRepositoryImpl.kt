package com.ashkan.samplecompose.data.network.repository.login

import com.ashkan.samplecompose.data.network.api.LoginApiService
import com.ashkan.samplecompose.data.network.core.apiWrapper
import com.ashkan.samplecompose.data.network.model.login.LoginResponseModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val apiService: LoginApiService
): LoginRepository {

    override fun login(email: String, password: String): Flow<Result<LoginResponseModel>> =
        apiWrapper {
            val response = apiService.login(
                email = email,
                password = password
            )
            response.copy(token = "fakeToken", refreshToken = "fakeRefreshToken")
        }
}