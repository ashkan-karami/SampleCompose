package com.ashkan.samplecompose.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashkan.samplecompose.data.network.core.ApiState
import com.ashkan.samplecompose.data.network.core.defaultErrorMessage
import com.ashkan.samplecompose.data.network.core.toApiState
import com.ashkan.samplecompose.data.network.model.post.PostModel
import com.ashkan.samplecompose.data.network.repository.home.HomeRepository
import com.ashkan.samplecompose.util.ConnectivityManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val connectivityManager: ConnectivityManager,
    private val repository: HomeRepository
): ViewModel() {

    private val allPostsList = mutableListOf<PostModel>() // Holds cached & fresh posts.
    private val _state = MutableStateFlow(HomeState())
    val stateValue = _state.asStateFlow()

    init {
        fetchCachedPosts()
        onAction(HomeAction.GetPosts)
    }

    fun onAction(action: HomeAction){
        when(action){
            HomeAction.GetPosts -> {
                getFreshPosts()
            }
            is HomeAction.UpdateSearchingMode -> {
                updateSearchingMode(action.activate)
            }
            is HomeAction.OnSearchPhraseChanged -> {
                searchPhrase(action.searchPhrase)
            }
        }
    }

    private fun fetchCachedPosts(){
        viewModelScope.launch {
            allPostsList.addAll(repository.cachedPosts())
            _state.emit(_state.value.copy(
                content = allPostsList
            ))
        }
    }

    private fun getFreshPosts(){
        if (connectivityManager.isConnected.not()){
            _state.value = _state.value.copy(
                isLoading = false,
                postApiFailureMessage = defaultErrorMessage
            )
            return
        }
        viewModelScope.launch {
            if (_state.value.content.isEmpty())
                _state.value = _state.value.copy(isLoading = true)
            repository.getRemotePosts().collect {
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