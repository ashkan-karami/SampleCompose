package com.ashkan.samplecompose.ui.screen.splash

sealed interface SplashAction {
    data object OnCacheCheck : SplashAction
    data object OnGetToken: SplashAction
}