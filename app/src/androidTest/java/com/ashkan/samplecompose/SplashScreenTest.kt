package com.ashkan.samplecompose

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.ashkan.samplecompose.data.core.defaultErrorMessage
import com.ashkan.samplecompose.ui.screen.splash.SplashScreen
import com.ashkan.samplecompose.ui.screen.splash.SplashState
import org.junit.Rule
import org.junit.Test

class SplashScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val testVersionName = "1.1.0-test"

    @Test
    fun splashScreen_whenLoading_showIcon() {
        composeTestRule.setContent {
            SplashScreen(
                uiState = SplashState(isLoading = true),
                versionName = testVersionName,
                onDialogDismissed = {}
            )
        }
        composeTestRule
            .onNodeWithContentDescription("SplashIcon")
            .assertIsDisplayed()
    }

    @Test
    fun splashScreen_whenLoading_VersionCode() {
        val version = "1.0.0"
        composeTestRule.setContent {
            SplashScreen(
                uiState = SplashState(isLoading = true),
                versionName = version,
                onDialogDismissed = {}
            )
        }
        composeTestRule
            .onNodeWithText("Version: $version")
            .assertExists()
    }

    @Test
    fun splashScreen_whenLoading_showLoading() {
        composeTestRule.setContent {
            SplashScreen(
                uiState = SplashState(isLoading = true),
                versionName = testVersionName,
                onDialogDismissed = {}
            )
        }
        composeTestRule
            .onNodeWithTag("SplashProgress")
            .assertExists()
    }

    @Test
    fun splashScreen_whenFailure_showMessage() {
        val message = "Sorry! failed"
        composeTestRule.setContent {
            SplashScreen(
                uiState = SplashState(isLoading = false, appConfigFailureMessage = message),
                versionName = testVersionName,
                onDialogDismissed = {}
            )
        }
        composeTestRule
            .onNodeWithText(message)
            .assertExists()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun alertDialog_whenNewUpdate_isDisplayed() {
        val title = "UpdateTitle"
        val message = "Version 1.2.3 is available"
        composeTestRule.setContent {
            SplashScreen(
                uiState = SplashState(
                    isLoading = false,
                    showUpdateDialog = true,
                    newVersionCode = "1.2.3",
                    updateDialogTitle = title,
                    updateDialogMessage = message
                    ),
                versionName = testVersionName,
                onDialogDismissed = {}
            )
        }

        composeTestRule.onNodeWithText(title).assertIsDisplayed()
        composeTestRule.onNodeWithText(message).assertIsDisplayed()
    }
}