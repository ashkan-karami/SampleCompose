package com.ashkan.samplecompose.ui.screen.login

data class LoginState(
    val emailAddress: String = "",
    val isEmailValid: Boolean = false,
    val password: String = "",
    val isPasswordValid: Boolean = false,
    val isLoading: Boolean = false,
    val navigateToHome: Boolean = false
)
