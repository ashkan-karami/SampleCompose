package com.ashkan.samplecompose.ui.screen.splash

data class SplashState(
    val isLoading: Boolean = false,
    val navigateToLogin: Boolean = false,
    val navigateToHome: Boolean = false,
    val appConfigFailureMessage: String? = null,
    val showUpdateDialog: Boolean = false,
    val newVersionCode: String? = null,
    val updateDialogTitle: String = "New version",
    val updateDialogMessage: String = "New version is available, do you want to update?"
)
