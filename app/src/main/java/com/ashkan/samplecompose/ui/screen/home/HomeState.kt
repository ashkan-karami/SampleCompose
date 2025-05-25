package com.ashkan.samplecompose.ui.screen.home

import com.ashkan.samplecompose.data.model.post.PostModel

data class HomeState(
    val isLoading: Boolean = false,
    val content: List<PostModel> = emptyList(),
    val postApiFailureMessage: String? = null,
    val isSearching: Boolean = false,
    val searchPhrase: String = "",

)
