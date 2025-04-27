package com.ashkan.samplecompose.ui.screen.splash

data class SplashState(
    val isLoading: Boolean = true,
    val navigateToLogin: Boolean = false,
    val navigateToHome: Boolean = false,
    val tokenError: SplashTokenError? = null
)
