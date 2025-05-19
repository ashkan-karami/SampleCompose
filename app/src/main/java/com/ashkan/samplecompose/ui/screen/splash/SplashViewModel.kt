package com.ashkan.samplecompose.ui.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashkan.samplecompose.data.cache.DataStoreManager
import com.ashkan.samplecompose.data.core.ApiState
import com.ashkan.samplecompose.data.core.toApiState
import com.ashkan.samplecompose.data.repository.splash.SplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val repository: SplashRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SplashState())
    val stateValue = _state.asStateFlow()

    init {
        onAction(SplashAction.OnGetAppConfig)
    }

    fun onAction(action: SplashAction) {
        when (action) {
            SplashAction.OnGetAppConfig -> {
                viewModelScope.launch {
                    if (dataStoreManager.isTokenSaved()) {
                        _state.emit(_state.value.copy(isLoading = true))
                        getAppConfig()
                    } else {
                        _state.emit(_state.value.copy(isLoading = false, navigateToLogin = true))
                    }
                }
            }

            SplashAction.UpdateDialogDismissed -> {
                _state.tryEmit(_state.value.copy(isLoading = false, navigateToHome = true))
            }
        }
    }

    private suspend fun getAppConfig() {
            repository.getAppConfig().collect {
                when (val apiState = it.toApiState()) {
                    is ApiState.Success -> {
                        if (apiState.data.updateAvailable == true) {
                            _state.emit(
                                _state.value.copy(
                                    isLoading = false,
                                    showUpdateDialog = true,
                                    newVersionCode = apiState.data.newVersionCode
                                )
                            )
                        } else {
                            _state.emit(_state.value.copy(isLoading = false, navigateToHome = true))
                        }
                    }

                    is ApiState.Failure -> {
                        _state.emit(
                            _state.value.copy(
                                isLoading = false,
                                appConfigFailureMessage = apiState.message
                            )
                        )
                    }
                }
            }
    }

    fun onResetState() {
        _state.value = SplashState()
    }
}