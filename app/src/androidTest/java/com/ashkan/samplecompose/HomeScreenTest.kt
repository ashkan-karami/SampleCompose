package com.ashkan.samplecompose

import androidx.activity.ComponentActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import com.ashkan.samplecompose.data.model.post.PostModel
import com.ashkan.samplecompose.data.repository.home.HomeRepository
import com.ashkan.samplecompose.ui.screen.home.HomeAction
import com.ashkan.samplecompose.ui.screen.home.HomeScreen
import com.ashkan.samplecompose.ui.screen.home.HomeState
import com.ashkan.samplecompose.ui.screen.home.HomeViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
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

    @Inject lateinit var repository: HomeRepository
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        hiltRule.inject()
        viewModel = HomeViewModel(repository)
    }

    @Test
    fun toolbarDisplaysProperly(){
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

    @Test
    fun searchActivation(){
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

        composeTestRule.onNodeWithTag(
            "HomeToolbarSearchClose",
            useUnmergedTree = true).assertIsDisplayed()
    }

    @Test
    fun whenApiCallLoadingDisplays(){
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

    @Test
    fun whenApiSuccessPostsDisplay(){
        val items = List(10) { PostModel(it,it,"Title #$it", "Body #$it") }

        composeTestRule.setContent {
            HomeScreen(
                state = HomeState(content = items),
                onReloadCLicked = {},
                onSearchClicked = {},
                onSearchPhraseChanged = {},
                onSearchClosed = {}
            )
        }

        composeTestRule.onNodeWithTag("HomePostLazyColumn").assertIsDisplayed()

        composeTestRule.onNodeWithTag(items[5].body?:"", useUnmergedTree = true)
            .performScrollTo().assertIsDisplayed()
    }

    @Test
    fun whenApiFailedMessageDisplays(){
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