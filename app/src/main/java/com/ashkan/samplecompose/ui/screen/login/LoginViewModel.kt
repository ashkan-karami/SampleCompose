package com.ashkan.samplecompose.ui.screen.login

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashkan.samplecompose.data.cache.DataStoreManager
import com.ashkan.samplecompose.data.network.core.ApiState
import com.ashkan.samplecompose.data.network.core.toApiState
import com.ashkan.samplecompose.data.network.repository.login.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val dataStoreManager: DataStoreManager,
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val emailAddress = savedStateHandle.getStateFlow(key = KEY_EMAIL, initialValue = "")
    private val password = savedStateHandle.getStateFlow(key = KEY_PASSWORD, initialValue = "")
    private val isLoading = MutableStateFlow(false)
    private val loginFailure = MutableStateFlow<String?>(null)
    private val navigateToHome = MutableStateFlow(false)

    val stateValue = combine(
        emailAddress, password, isLoading, loginFailure, navigateToHome
    ) { emailAddress, password, isLoading, loginFailure, navigateToHome ->
        LoginState(
            emailAddress = emailAddress,
            isEmailValid = emailAddress.isNotEmpty() && isValidEmail(emailAddress),
            password = password,
            isPasswordValid = password.length > 4,
            isLoading = isLoading,
            loginFailure = loginFailure,
            navigateToHome = navigateToHome
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), LoginState())

    fun onAction(intent: LoginAction) {
        when (intent) {
            is LoginAction.EmailChanged -> {
                savedStateHandle[KEY_EMAIL] = intent.email
            }
            is LoginAction.PasswordChanged -> {
                savedStateHandle[KEY_PASSWORD] = intent.password
            }
            LoginAction.SubmitLogin -> {
                performLogin()
            }
            LoginAction.MoveFocusToPassword -> {}
        }
    }

    private fun performLogin(){
        viewModelScope.launch {
            isLoading.value = true
            loginRepository.login(
                email = emailAddress.value,
                password = password.value
            ).collect {
                isLoading.value = false
                when (val apiState = it.toApiState()) {
                    is ApiState.Success -> {
                        with(apiState.data){
                            if (token.isNullOrEmpty().not() && refreshToken.isNullOrEmpty().not()) {
                                saveInfoIntoDB(
                                    token = token,
                                    refreshToken = refreshToken,
                                    email = emailAddress.value
                                )
                                navigateToHome.value = true
                            } else {
                                loginFailure.value = "Failed to get response, please try again later."
                            }
                        }
                    }
                    is ApiState.Failure -> {
                        loginFailure.value = apiState.message
                    }
                }
            }
        }
    }

    private fun saveInfoIntoDB(
        token: String,
        refreshToken: String,
        email: String,
    ){
        viewModelScope.launch {
            dataStoreManager.saveToken(token)
            dataStoreManager.saveRefreshToken(refreshToken)
            dataStoreManager.saveEmailAddress(email)
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$")
        return email.matches(emailRegex)
    }

    private companion object {
        const val KEY_EMAIL = "EMAIL_ADDRESS"
        const val KEY_PASSWORD = "PASSWORD"
    }
}