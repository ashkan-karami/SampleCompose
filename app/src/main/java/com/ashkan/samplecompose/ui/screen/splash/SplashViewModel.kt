package com.ashkan.samplecompose.ui.screen.splash

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(): ViewModel() {

    private val state = MutableStateFlow(SplashState())
    val stateValue = state.asStateFlow()

    init {
        onAction(SplashAction.OnCacheCheck)
    }

    fun onAction(action: SplashAction){
        when(action){
            SplashAction.OnCacheCheck -> {}
            SplashAction.OnGetToken -> {}
        }
    }

    fun onResetState() {
        state.value = SplashState()
    }
}