package com.ashkan.samplecompose

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollTo
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ashkan.samplecompose.data.model.post.PostModel
import com.ashkan.samplecompose.ui.screen.home.HomeScreen
import com.ashkan.samplecompose.ui.screen.home.HomeState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun whenApiCallLoadingDisplays(){
        composeTestRule.setContent {
            HomeScreen(
                state = HomeState(isLoading = true),
                onReloadCLicked = {}
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
                onReloadCLicked = {}
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
                onReloadCLicked = {}
            )
        }

        // Failure message is displayed
        composeTestRule.onNodeWithText(fakeApiFailureMessage).assertIsDisplayed()
        // Reload Button is displayed
        composeTestRule.onNodeWithText("Reload").assertIsDisplayed()
    }
}