package com.ashkan.samplecompose

import androidx.activity.ComponentActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
import com.ashkan.samplecompose.data.model.post.PostModel
import com.ashkan.samplecompose.data.repository.home.HomeRepository
import com.ashkan.samplecompose.ui.screen.home.HomeAction
import com.ashkan.samplecompose.ui.screen.home.HomeScreen
import com.ashkan.samplecompose.ui.screen.home.HomeState
import com.ashkan.samplecompose.ui.screen.home.HomeViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class HomeScreenTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Inject
    lateinit var repository: HomeRepository
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        hiltRule.inject()
        viewModel = HomeViewModel(repository)
    }

    // Check if HomeToolbar and its content is displayed peoperly
    @Test
    fun toolbarDisplaysProperly() {
        composeTestRule.setContent {
            HomeScreen(
                state = HomeState(),
                onReloadCLicked = {},
                onSearchClicked = {},
                onSearchPhraseChanged = {},
                onSearchClosed = {}
            )
        }

        composeTestRule.onNodeWithText("For You").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("HomeToolbarSearch").assertIsDisplayed()
    }

    // After clicking on search button, search mode will be activated and Toolbar's content changes.
    @Test
    fun searchActivation() {
        composeTestRule.setContent {
            val state by viewModel.stateValue.collectAsState()

            HomeScreen(
                state = state,
                onReloadCLicked = {},
                onSearchClicked = {
                    viewModel.onAction(HomeAction.UpdateSearchingMode(activate = true))
                },
                onSearchPhraseChanged = {},
                onSearchClosed = {}
            )
        }

        composeTestRule.onNodeWithContentDescription("HomeToolbarSearch").performClick()

        composeTestRule.onNodeWithTag("HomeToolbarSearchClose").assertIsDisplayed()
        composeTestRule.onNodeWithTag("HomeSearchTextField").assertIsDisplayed()
        composeTestRule.onNodeWithTag("HomeToolbarSearch").assertIsNotDisplayed()
        composeTestRule.onNodeWithText("For You").assertIsNotDisplayed()
    }

    // When calling api, before getting response, user is seeing a Loading.
    @Test
    fun whenApiCallLoadingDisplays() {
        composeTestRule.setContent {
            HomeScreen(
                state = HomeState(isLoading = true),
                onReloadCLicked = {},
                onSearchClicked = {},
                onSearchPhraseChanged = {},
                onSearchClosed = {}
            )
        }

        composeTestRule.onNodeWithTag("ApiCallProgress").isDisplayed()
    }

    // After getting post list successfully, a list of all items will be displayed.
    @Test
    fun whenApiSuccessPostsDisplay() {
        composeTestRule.setContent {
            val state by viewModel.stateValue.collectAsState()
            viewModel.onAction(HomeAction.GetPosts)
            HomeScreen(
                state = state,
                onReloadCLicked = {},
                onSearchClicked = {},
                onSearchPhraseChanged = {},
                onSearchClosed = {}
            )
        }

        composeTestRule.onNodeWithTag("HomePostLazyColumn").assertIsDisplayed()

        composeTestRule.onNodeWithTag(
            viewModel.stateValue.value.content[2].title ?: "",
            useUnmergedTree = true
        ).performScrollTo().assertIsDisplayed()
        val count = composeTestRule.onAllNodesWithTag("PostBody", useUnmergedTree = true)
        count.assertCountEquals(viewModel.stateValue.value.content.size) // All items are tagged with 'PostBody'
    }

    // When typing on TextField, filter operation executes and shorter list will be displayed
    // Checks only items match with input text are exist.
    @Test
    fun typeInSearchTextField() {
        val searchPhrase = "Title #2"
        composeTestRule.setContent {
            val state by viewModel.stateValue.collectAsState()
            viewModel.onAction(HomeAction.GetPosts)

            HomeScreen(
                state = state,
                onReloadCLicked = {},
                onSearchClicked = {
                    viewModel.onAction(HomeAction.UpdateSearchingMode(activate = true))
                },
                onSearchPhraseChanged = {
                    viewModel.onAction(HomeAction.OnSearchPhraseChanged(it))
                },
                onSearchClosed = {}
            )
        }

        composeTestRule.onNodeWithContentDescription("HomeToolbarSearch").performClick()
        composeTestRule.onNodeWithTag("HomeSearchTextField").performTextInput(searchPhrase)
        // Search TextField content changes properly
        composeTestRule.onNodeWithTag("HomeSearchTextField").assertTextContains(searchPhrase)
        // Check if search result is displayed in LazyColumn
        val items = composeTestRule.onAllNodesWithTag(searchPhrase, useUnmergedTree = true)
        items.assertCountEquals(1) // We have only one item with 'Title #2'
    }

    // If api failed, appropriate failure message will be displayed.
    @Test
    fun whenApiFailedMessageDisplays() {
        val fakeApiFailureMessage = "Api failed"
        composeTestRule.setContent {
            HomeScreen(
                state = HomeState(postApiFailureMessage = fakeApiFailureMessage),
                onReloadCLicked = {},
                onSearchClicked = {},
                onSearchPhraseChanged = {},
                onSearchClosed = {}
            )
        }

        // Failure message is displayed
        composeTestRule.onNodeWithText(fakeApiFailureMessage).assertIsDisplayed()
        // Reload Button is displayed
        composeTestRule.onNodeWithText("Reload").assertIsDisplayed()
    }
}