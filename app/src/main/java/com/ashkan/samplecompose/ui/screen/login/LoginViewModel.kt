package com.ashkan.samplecompose.ui.screen.login

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ashkan.samplecompose.data.cache.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    val emailAddress = savedStateHandle.getStateFlow(key = EMAIL_ADDRESS, initialValue = "")

    private val _state = MutableStateFlow(LoginState())
    val stateValue = _state.asStateFlow()
}

private const val EMAIL_ADDRESS = "email_address"