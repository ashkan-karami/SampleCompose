package com.ashkan.samplecompose.ui.screen.splash

sealed interface SplashAction {
    data object OnGetAppConfig: SplashAction
}