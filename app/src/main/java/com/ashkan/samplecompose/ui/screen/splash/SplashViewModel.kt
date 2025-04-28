package com.ashkan.samplecompose.ui.screen.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashkan.samplecompose.data.core.ApiState
import com.ashkan.samplecompose.data.core.toApiState
import com.ashkan.samplecompose.domain.repository.SplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: SplashRepository
): ViewModel() {

    private val _state = MutableStateFlow(SplashState())
    val stateValue = _state.asStateFlow()

    init {
        onAction(SplashAction.OnGetAppConfig)
    }

    fun onAction(action: SplashAction){
        when(action){
            SplashAction.OnGetAppConfig -> {
                if (needLogin()){
                    // TODO Go to login page
                } else {
                    _state.tryEmit(_state.value.copy(isLoading = true))
                    getAppConfig()
                }
            }
        }
    }

    private fun needLogin(): Boolean{
        //TODO to be developed later
        return false
    }

    private fun getAppConfig(){
        viewModelScope.launch{
            repository.getAppConfig().collect{
                val apiState =  it.toApiState()
                Log.i("aaaaaaaaaaaaaaaa","view model response="+apiState)
                when(apiState){
                    is ApiState.Success -> {
                        _state.emit(_state.value.copy(isLoading = false, navigateToHome = true))
                        // TODO cache temporary data
                    }
                    is ApiState.Failure -> {
                        _state.emit(_state.value.copy(isLoading = false, appConfigFailureMessage = apiState.message))
                    }
                }
            }
        }
    }

    fun onResetState() {
        _state.value = SplashState()
    }
}