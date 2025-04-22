package com.ashkan.samplecompose.ui.screen.splash

import com.ashkan.samplecompose.domain.model.SplashTokenError

data class SplashState(
    val isLoading: Boolean = true,
    val navigateToLogin: Boolean = false,
    val navigateToHome: Boolean = false,
    val tokenError: SplashTokenError? = null
)
