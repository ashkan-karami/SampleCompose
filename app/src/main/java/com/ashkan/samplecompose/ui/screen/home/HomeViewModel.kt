package com.ashkan.samplecompose.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashkan.samplecompose.data.network.core.ApiState
import com.ashkan.samplecompose.data.network.core.toApiState
import com.ashkan.samplecompose.data.network.model.post.PostModel
import com.ashkan.samplecompose.data.network.repository.home.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
): ViewModel() {

    private val allPostsList = mutableListOf<PostModel>()
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
            is HomeAction.UpdateSearchingMode -> {
                updateSearchingMode(action.activate)
            }
            is HomeAction.OnSearchPhraseChanged -> {
                searchPhrase(action.searchPhrase)
            }
        }
    }

    private fun getPosts(){
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            repository.getPosts().collect {
                when(val data = it.toApiState()){
                    is ApiState.Success -> {
                        allPostsList.addAll(data.data)
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

    private fun updateSearchingMode(activate: Boolean){
        viewModelScope.launch {
            if (activate){
                if (_state.value.isLoading.not() &&
                    _state.value.isSearching.not()) {
                    _state.emit(
                        _state.value.copy(
                            isSearching = true
                        )
                    )
                }
            } else {
                _state.emit(
                    _state.value.copy(
                        content = allPostsList,
                        isSearching = false,
                        searchPhrase = ""
                    )
                )
            }
        }
    }

    private fun searchPhrase(phrase: String){
        viewModelScope.launch {
            _state.emit(
                _state.value.copy(
                    content = allPostsList.filter {
                        it.title?.lowercase()?.contains(phrase) == true ||
                                it.body?.lowercase()?.contains(phrase) == true
                    },
                    searchPhrase = phrase
                )
            )
        }
    }
}