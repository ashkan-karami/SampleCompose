package com.ashkan.samplecompose.ui.screen.login

import android.util.Patterns
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashkan.samplecompose.data.cache.DataStoreManager
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
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val emailAddress = savedStateHandle.getStateFlow(key = KEY_EMAIL, initialValue = "")
    private val password = savedStateHandle.getStateFlow(key = KEY_PASSWORD, initialValue = "")
    private val isLoading = MutableStateFlow(false)
    private val navigateToHome = MutableStateFlow(false)

    val stateValue = combine(
        emailAddress, password, isLoading, navigateToHome
    ) { emailAddress, password, isLoading, navigateToHome ->
        LoginState(
            emailAddress = emailAddress,
            isEmailValid = emailAddress.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches(),
            password = password,
            isPasswordValid = password.length > 4,
            isLoading = isLoading,
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
        }
    }

    private companion object {
        const val KEY_EMAIL = "EMAIL_ADDRESS"
        const val KEY_PASSWORD = "PASSWORD"
    }
}