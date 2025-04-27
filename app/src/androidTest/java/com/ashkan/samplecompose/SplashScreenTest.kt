package com.ashkan.samplecompose

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.ashkan.samplecompose.ui.screen.splash.SplashScreen
import com.ashkan.samplecompose.ui.screen.splash.SplashState
import org.junit.Rule
import org.junit.Test

class SplashScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun splashScreen_whenLoading_showIcon() {
        composeTestRule.setContent {
            SplashScreen(
                uiState = SplashState(isLoading = true)
            )
        }
        composeTestRule
            .onNodeWithContentDescription("SplashIcon")
            .assertExists()
    }

    @Test
    fun splashScreen_whenLoading_VersionCode() {
        val version = "1.0.0"
        composeTestRule.setContent {
            SplashScreen(
                uiState = SplashState(isLoading = true),
                versionName = version
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
                uiState = SplashState(isLoading = true)
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
                uiState = SplashState(isLoading = false, tokenError = SplashTokenError(
                    errorCode = 0, errorMessage = message
                ))
            )
        }
        composeTestRule
            .onNodeWithText(message)
            .assertExists()
    }
}