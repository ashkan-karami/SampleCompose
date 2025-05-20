package com.ashkan.samplecompose.ui.screen.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
internal fun LoginRoute(
    modifier: Modifier = Modifier,
    onNavigateToHome: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
){
    val state: LoginState by viewModel.stateValue.collectAsStateWithLifecycle()

    LoginScreen(
        modifier = modifier,
        uiState = state
    )
}

@Composable
internal fun LoginScreen(
    modifier: Modifier = Modifier,
    uiState: LoginState
) {

}