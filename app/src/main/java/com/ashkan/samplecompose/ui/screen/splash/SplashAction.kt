package com.ashkan.samplecompose.ui.screen.splash

sealed interface SplashAction {
    object OnGetAppConfig: SplashAction
    object UpdateDialogDismissed: SplashAction
}