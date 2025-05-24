package com.ashkan.samplecompose.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashkan.samplecompose.data.core.ApiState
import com.ashkan.samplecompose.data.core.toApiState
import com.ashkan.samplecompose.data.repository.home.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
): ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val stateValue = _state.asStateFlow()

    init {
        onAction(HomeAction.GetPosts)
    }

    fun onAction(action: HomeAction){
        when(action){
            HomeAction.GetPosts -> {
                getPosts()
            }
        }
    }

    private fun getPosts(){
        viewModelScope.launch {
            repository.getArticles().collect {
                when(val data = it.toApiState()){
                    is ApiState.Success -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            content = data.data,
                            postApiFailureMessage = null
                        )
                    }
                    is ApiState.Failure -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            content = emptyList(),
                            postApiFailureMessage = data.message
                        )
                    }
                }
            }
        }
    }
}