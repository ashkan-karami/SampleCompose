package com.ashkan.samplecompose.ui.screen.splash

data class SplashState(
    val isLoading: Boolean = false,
    val navigateToLogin: Boolean = false,
    val navigateToHome: Boolean = false,
    val showUpdateDialog: Boolean = false,
    val newVersionCode: String? = null,
    val appConfigFailureMessage: String? = null
)
