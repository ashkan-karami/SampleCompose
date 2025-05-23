package com.ashkan.samplecompose.login

import androidx.lifecycle.SavedStateHandle
import com.ashkan.samplecompose.MainDispatcherRule
import com.ashkan.samplecompose.data.cache.DataStoreManager
import com.ashkan.samplecompose.data.core.NetworkExceptions
import com.ashkan.samplecompose.data.model.login.LoginResponseModel
import com.ashkan.samplecompose.data.repository.login.LoginRepository
import com.ashkan.samplecompose.ui.screen.login.LoginAction
import com.ashkan.samplecompose.ui.screen.login.LoginViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: LoginViewModel
    private val savedStateHandle = SavedStateHandle()
    private val mockedLoginRepository: LoginRepository = mock()
    private val successLoginResponseModel: LoginResponseModel = mock()
    private val mockedDataStoreManager: DataStoreManager = mock()
    private val fakeErrorMessage = "Failed to connect to the server!"
    private val fakeException: NetworkExceptions = NetworkExceptions.IOException(fakeErrorMessage)
    val fakeEmail = "fake@email.com"
    val fakePass = "Pass123456"

    @Test
    fun `on EmailChanged updates state with valid email`() = runTest {
        viewModel = LoginViewModel(savedStateHandle, mockedDataStoreManager, mockedLoginRepository)

        viewModel.onAction(LoginAction.EmailChanged(fakeEmail))

        val state = viewModel.stateValue.first()

        assertEquals(fakeEmail, state.emailAddress)
        assertTrue(state.isEmailValid)
    }

    @Test
    fun `on Api calls progressBar displays`() = runTest {
        whenever(mockedLoginRepository.login(fakeEmail, fakePass)).thenReturn(flow {
            delay(2000)
            emit(Result.success(successLoginResponseModel))
        })
        viewModel = LoginViewModel(savedStateHandle, mockedDataStoreManager, mockedLoginRepository)
        viewModel.onAction(LoginAction.EmailChanged(fakeEmail))
        viewModel.onAction(LoginAction.PasswordChanged(fakePass))
        viewModel.onAction(LoginAction.SubmitLogin)
        assertTrue(viewModel.stateValue.first().isLoading)
    }

    @Test
    fun `on login success navigates to home`() = runTest {
        whenever(mockedLoginRepository.login(email = fakeEmail, password = fakePass)).thenReturn(
            flow {
                emit(Result.success(successLoginResponseModel))
            })
        viewModel = LoginViewModel(savedStateHandle, mockedDataStoreManager, mockedLoginRepository)
        viewModel.onAction(LoginAction.EmailChanged(fakeEmail))
        viewModel.onAction(LoginAction.PasswordChanged(fakePass))
        viewModel.onAction(LoginAction.SubmitLogin)
        assertTrue(viewModel.stateValue.first().navigateToHome)
        assertEquals(viewModel.stateValue.first().loginFailure, null)
    }

    @Test
    fun `on login failure adds failure message`() = runTest {
        whenever(mockedLoginRepository.login(email = fakeEmail, password = fakePass)).thenReturn(
            flow {
                emit(Result.failure(fakeException))
            })
        viewModel = LoginViewModel(savedStateHandle, mockedDataStoreManager, mockedLoginRepository)
        viewModel.onAction(LoginAction.EmailChanged(fakeEmail))
        viewModel.onAction(LoginAction.PasswordChanged(fakePass))
        viewModel.onAction(LoginAction.SubmitLogin)
        assertEquals(false, viewModel.stateValue.first().navigateToHome)
        assertEquals(fakeErrorMessage, viewModel.stateValue.first().loginFailure)
    }
}