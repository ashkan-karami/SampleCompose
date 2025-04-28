package com.ashkan.samplecompose.ui.screen.splash

data class SplashState(
    val isLoading: Boolean = false,
    val navigateToLogin: Boolean = false,
    val navigateToHome: Boolean = false,
    val appConfigFailureMessage: String? = null
)
