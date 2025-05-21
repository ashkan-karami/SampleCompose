package com.ashkan.samplecompose.ui.screen.login

sealed class LoginAction {
    data class EmailChanged(val email: String) : LoginAction()
    data class PasswordChanged(val password: String) : LoginAction()
    object SubmitLogin : LoginAction()
}