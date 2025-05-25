package com.ashkan.samplecompose.ui.screen.home

sealed interface HomeAction {
    object GetPosts: HomeAction
    data class UpdateSearchingMode(val activate: Boolean): HomeAction
    data class OnSearchPhraseChanged(val searchPhrase: String): HomeAction
}