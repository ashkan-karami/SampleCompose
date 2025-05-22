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
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
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
    private val mockedException: NetworkExceptions = mock()


    @Test
    fun `on EmailChanged updates state with valid email`() = runTest {
        val savedStateHandle = SavedStateHandle()
        viewModel = LoginViewModel(savedStateHandle, mockedDataStoreManager, mockedLoginRepository)

        viewModel.onAction(LoginAction.EmailChanged("test@example.com"))

        //val state = viewModel.stateValue.first { it.emailAddress == "test@example.com" }

        //assertEquals("test@example.com", state.emailAddress)
        //assertTrue(state.isEmailValid)
    }
}