package com.ashkan.samplecompose.ui.screen.login

data class LoginState(
    val emailAddress: String = "",
    val password: String = "",
    val isLoading: Boolean = false
)
