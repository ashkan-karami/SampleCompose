package com.ashkan.samplecompose.splash

import com.ashkan.samplecompose.MainDispatcherRule
import com.ashkan.samplecompose.data.model.splash.AppConfigModel
import com.ashkan.samplecompose.data.repository.splash.SplashRepository
import com.ashkan.samplecompose.ui.screen.splash.SplashViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.mockito.kotlin.mock

@OptIn(ExperimentalCoroutinesApi::class)
class SplashViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: SplashViewModel
    private val mockedSplashRepository: SplashRepository = mock()
    private val successSplashResponseModel: AppConfigModel = mock()

    @Before
    fun setup() {
        viewModel = SplashViewModel(mockedSplashRepository)
    }
}