package com.ashkan.samplecompose.ui.screen.home

sealed interface HomeAction {
    object GetPosts: HomeAction
}