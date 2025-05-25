package com.ashkan.samplecompose

import androidx.activity.ComponentActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.lifecycle.SavedStateHandle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ashkan.samplecompose.data.cache.DataStoreManager
import com.ashkan.samplecompose.data.network.repository.login.LoginRepository
import com.ashkan.samplecompose.ui.screen.login.EMAIL_TEXT_FIELD_CLEAR_TAG
import com.ashkan.samplecompose.ui.screen.login.EMAIL_TEXT_FIELD_ICON_TAG
import com.ashkan.samplecompose.ui.screen.login.EMAIL_TEXT_FIELD_TAG
import com.ashkan.samplecompose.ui.screen.login.LOGIN_BUTTON_PROGRESS_TAG
import com.ashkan.samplecompose.ui.screen.login.LOGIN_BUTTON_TAG
import com.ashkan.samplecompose.ui.screen.login.LOGIN_DESCRIPTION_TAG
import com.ashkan.samplecompose.ui.screen.login.LOGIN_HEADER_TITLE
import com.ashkan.samplecompose.ui.screen.login.LOGIN_TERM_AND_CONDITION_TAG
import com.ashkan.samplecompose.ui.screen.login.LoginScreen
import com.ashkan.samplecompose.ui.screen.login.LoginState
import com.ashkan.samplecompose.ui.screen.login.LoginViewModel
import com.ashkan.samplecompose.ui.screen.login.PASS_TEXT_FIELD_CLEAR_TAG
import com.ashkan.samplecompose.ui.screen.login.PASS_TEXT_FIELD_ICON_TAG
import com.ashkan.samplecompose.ui.screen.login.PASS_TEXT_FIELD_TAG
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class LoginScreenTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Inject lateinit var repository: LoginRepository
    @Inject lateinit var dataStoreManager: DataStoreManager
    private val savedStateHandle = SavedStateHandle()
    private lateinit var viewModel: LoginViewModel

    private val fakeLoginState = LoginState(
        emailAddress = "email@test.com",
        isEmailValid = true,
        password = "pass123456",
        isPasswordValid = true,
        isLoading = false
    )

    @Before
    fun setup() {
        hiltRule.inject()
        viewModel = LoginViewModel(
            savedStateHandle,
            dataStoreManager,
            repository
        )
    }

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
                uiState = fakeLoginState,
                onAction = {}
            )
        }
        composeTestRule
            .onNodeWithTag(EMAIL_TEXT_FIELD_CLEAR_TAG)
            .assertIsDisplayed()
    }

    // While typing/clearing
    @Test
    fun loginScreen_emailTextField_changeContent(){
        composeTestRule.setContent {
            val state by viewModel.stateValue.collectAsState()

            LoginScreen(
                uiState = state,
                onAction = {
                    viewModel.onAction(it)
                }
            )
        }

        composeTestRule.onNodeWithTag(EMAIL_TEXT_FIELD_TAG).performTextInput(fakeLoginState.emailAddress)
        composeTestRule.onNodeWithTag(EMAIL_TEXT_FIELD_TAG).assertTextContains(fakeLoginState.emailAddress)
        composeTestRule.onNodeWithTag(EMAIL_TEXT_FIELD_CLEAR_TAG).assertIsDisplayed()

        composeTestRule.onNodeWithTag(EMAIL_TEXT_FIELD_CLEAR_TAG).performClick()
        composeTestRule.onNodeWithTag(EMAIL_TEXT_FIELD_TAG).assertTextContains("")
        composeTestRule.onNodeWithTag(EMAIL_TEXT_FIELD_CLEAR_TAG).assertIsNotDisplayed()
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
                uiState = fakeLoginState,
                onAction = {}
            )
        }
        composeTestRule
            .onNodeWithTag(PASS_TEXT_FIELD_CLEAR_TAG)
            .assertIsDisplayed()
    }

    // While typing/clearing
    @Test
    fun loginScreen_passwordTextField_changeContent(){
        composeTestRule.setContent {
            val state by viewModel.stateValue.collectAsState()

            LoginScreen(
                uiState = state,
                onAction = {
                    viewModel.onAction(it)
                }
            )
        }

        composeTestRule.onNodeWithTag(PASS_TEXT_FIELD_TAG).performTextInput(fakeLoginState.password)
        composeTestRule.onNodeWithTag(PASS_TEXT_FIELD_TAG).assertTextContains(fakeLoginState.password)
        composeTestRule.onNodeWithTag(PASS_TEXT_FIELD_CLEAR_TAG).assertIsDisplayed()

        composeTestRule.onNodeWithTag(PASS_TEXT_FIELD_CLEAR_TAG).performClick()
        composeTestRule.onNodeWithTag(PASS_TEXT_FIELD_TAG).assertTextContains("")
        composeTestRule.onNodeWithTag(PASS_TEXT_FIELD_CLEAR_TAG).assertIsNotDisplayed()
    }

    // Checks if button is displayed
    @Test
    fun loginScreen_showButton(){
        composeTestRule.setContent {
            LoginScreen(
                uiState = fakeLoginState,
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
                uiState = fakeLoginState.copy(isLoading = true),
                onAction = {}
            )
        }

        composeTestRule
            .onNodeWithTag(LOGIN_BUTTON_PROGRESS_TAG)
            .assertIsDisplayed()
    }

    // Check the failure message
    @Test
    fun loginScreen_whenLoginFailed_showMessage(){
        val fakeMessage = "Fake message from server"
        composeTestRule.setContent {
            LoginScreen(
                uiState = fakeLoginState.copy(loginFailure = fakeMessage),
                onAction = {}
            )
        }

        composeTestRule
            .onNodeWithText(fakeMessage)
            .assertIsDisplayed()
    }
}