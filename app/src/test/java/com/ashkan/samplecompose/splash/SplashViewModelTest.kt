package com.ashkan.samplecompose.splash

import com.ashkan.samplecompose.MainDispatcherRule
import com.ashkan.samplecompose.data.cache.DataStoreManager
import com.ashkan.samplecompose.data.model.splash.AppConfigModel
import com.ashkan.samplecompose.data.repository.splash.SplashRepository
import com.ashkan.samplecompose.ui.screen.splash.SplashAction
import com.ashkan.samplecompose.ui.screen.splash.SplashViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class SplashViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: SplashViewModel
    private val mockedSplashRepository: SplashRepository = mock()
    private val successSplashResponseModel: AppConfigModel = mock()
    private val mockedDataStoreManager: DataStoreManager = mock()

    @Test
    fun splashState_whenTokenSaved_appConfigCalled() = runTest {
        whenever(mockedSplashRepository.getAppConfig()).thenReturn( flow { emit(Result.success(successSplashResponseModel)) })
        whenever(mockedDataStoreManager.isTokenSaved()).thenReturn(true)
        viewModel = SplashViewModel(mockedDataStoreManager, mockedSplashRepository)
        verify(mockedSplashRepository, times(1)).getAppConfig()
    }

    @Test
    fun splashState_whenCallingApi_showsProgress() = runTest {
        whenever(mockedSplashRepository.getAppConfig()).thenReturn( flow {
            delay(2000)
            emit(Result.success(successSplashResponseModel))
        })
        whenever(mockedDataStoreManager.isTokenSaved()).thenReturn(true)
        viewModel = SplashViewModel(mockedDataStoreManager, mockedSplashRepository)
        assertTrue(viewModel.stateValue.value.isLoading)
    }

    @Test
    fun splashState_whenApiSuccess_thenResultIsSuccess() = runTest {
        whenever(mockedSplashRepository.getAppConfig()).thenReturn( flow { emit(Result.success(successSplashResponseModel)) })
        whenever(mockedDataStoreManager.isTokenSaved()).thenReturn(true)
        viewModel = SplashViewModel(mockedDataStoreManager, mockedSplashRepository)
        assertTrue(viewModel.stateValue.first().navigateToHome)
        assertEquals(viewModel.stateValue.first().appConfigFailureMessage, null)
    }
}