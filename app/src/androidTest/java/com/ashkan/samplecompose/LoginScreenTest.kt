package com.ashkan.samplecompose

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.ashkan.samplecompose.ui.screen.login.EMAIL_TEXT_FIELD_CLEAR_TAG
import com.ashkan.samplecompose.ui.screen.login.EMAIL_TEXT_FIELD_ICON_TAG
import com.ashkan.samplecompose.ui.screen.login.EMAIL_TEXT_FIELD_TAG
import com.ashkan.samplecompose.ui.screen.login.LOGIN_BUTTON_PROGRESS_TAG
import com.ashkan.samplecompose.ui.screen.login.LOGIN_BUTTON_TAG
import com.ashkan.samplecompose.ui.screen.login.LOGIN_BUTTON_TEXT_TAG
import com.ashkan.samplecompose.ui.screen.login.LOGIN_DESCRIPTION_TAG
import com.ashkan.samplecompose.ui.screen.login.LOGIN_HEADER_TITLE
import com.ashkan.samplecompose.ui.screen.login.LOGIN_TERM_AND_CONDITION_TAG
import com.ashkan.samplecompose.ui.screen.login.LoginScreen
import com.ashkan.samplecompose.ui.screen.login.LoginState
import com.ashkan.samplecompose.ui.screen.login.PASS_TEXT_FIELD_CLEAR_TAG
import com.ashkan.samplecompose.ui.screen.login.PASS_TEXT_FIELD_ICON_TAG
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val defaultLoginState = LoginState(
        emailAddress = "email@test.com",
        isEmailValid = true,
        password = "123456",
        isPasswordValid = true,
        isLoading = false
    )

    // Checks if header is displayed
    @Test
    fun loginScreen_showHeader(){
        composeTestRule.setContent {
            LoginScreen(
                uiState = LoginState(),
                onAction = {}
            )
        }

        // title is displayed
        composeTestRule
            .onNodeWithTag(LOGIN_HEADER_TITLE)
            .assertIsDisplayed()

        // description is displayed
        composeTestRule
            .onNodeWithTag(LOGIN_DESCRIPTION_TAG)
            .assertIsDisplayed()
    }

    // Checks if term and condition is displayed
    @Test
    fun loginScreen_showTermAndCondition(){
        composeTestRule.setContent {
            LoginScreen(
                uiState = LoginState(),
                onAction = {}
            )
        }
        composeTestRule
            .onNodeWithTag(LOGIN_TERM_AND_CONDITION_TAG)
            .assertIsDisplayed()
    }

    // Checks if email field is focused when starting
    @Test
    fun loginScreenStarts_emailTextField_isFocused(){
        composeTestRule.setContent {
            LoginScreen(
                uiState = LoginState(),
                onAction = {}
            )
        }

        composeTestRule
            .onNodeWithTag(EMAIL_TEXT_FIELD_TAG)
            .assertIsFocused()
    }

    // checks if email field icon is displayed
    @Test
    fun loginScreen_emailTextField_showIcon(){
        composeTestRule.setContent {
            LoginScreen(
                uiState = LoginState(),
                onAction = {}
            )
        }
        composeTestRule
            .onNodeWithContentDescription(EMAIL_TEXT_FIELD_ICON_TAG)
            .assertIsDisplayed()
    }

    // Checks if clear IconButton is displayed when email entered
    @Test
    fun loginScreen_emailTextField_showClearIcon(){
        composeTestRule.setContent {
            LoginScreen(
                uiState = defaultLoginState,
                onAction = {}
            )
        }
        composeTestRule
            .onNodeWithTag(EMAIL_TEXT_FIELD_CLEAR_TAG)
            .assertIsDisplayed()
    }

    // checks if password field icon is displayed
    @Test
    fun loginScreen_passwordTextField_showIcon(){
        composeTestRule.setContent {
            LoginScreen(
                uiState = LoginState(),
                onAction = {}
            )
        }
        composeTestRule
            .onNodeWithContentDescription(PASS_TEXT_FIELD_ICON_TAG)
            .assertIsDisplayed()
    }

    // Checks if clear IconButton is displayed when password entered
    @Test
    fun loginScreen_passwordTextField_showClearIcon(){
        composeTestRule.setContent {
            LoginScreen(
                uiState = defaultLoginState,
                onAction = {}
            )
        }
        composeTestRule
            .onNodeWithTag(PASS_TEXT_FIELD_CLEAR_TAG)
            .assertIsDisplayed()
    }

    // Checks if button is displayed
    @Test
    fun loginScreen_showButton(){
        composeTestRule.setContent {
            LoginScreen(
                uiState = defaultLoginState,
                onAction = {}
            )
        }

        composeTestRule
            .onNodeWithTag(LOGIN_BUTTON_TAG)
            .assertIsDisplayed()
    }

    // Checks if progressBar is displayed when loading
    @Test
    fun loginScreen_showLoading(){
        composeTestRule.setContent {
            LoginScreen(
                uiState = defaultLoginState.copy(isLoading = true),
                onAction = {}
            )
        }

        composeTestRule
            .onNodeWithTag(LOGIN_BUTTON_PROGRESS_TAG)
            .assertIsDisplayed()
    }
}