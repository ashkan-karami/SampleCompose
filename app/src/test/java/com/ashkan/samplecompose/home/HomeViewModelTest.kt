package com.ashkan.samplecompose.home

import com.ashkan.samplecompose.MainDispatcherRule
import com.ashkan.samplecompose.data.network.core.NetworkExceptions
import com.ashkan.samplecompose.data.network.model.post.PostModel
import com.ashkan.samplecompose.data.network.repository.home.HomeRepository
import com.ashkan.samplecompose.ui.screen.home.HomeViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: HomeViewModel
    private val mockedHomeRepository: HomeRepository = mock()
    private val fakePostList = List(10) { PostModel(it,it,"Title #$it", "Body #$it") }
    private val fakeErrorMessage = "Failed to connect to the server!"
    private val fakeException: NetworkExceptions = NetworkExceptions.IOException(fakeErrorMessage)

    @Before
    fun setUp(){
        whenever(mockedHomeRepository.getPosts()).thenReturn(
            flow { emit(Result.success(fakePostList)) }
        )
        viewModel = HomeViewModel(mockedHomeRepository)
    }

    @Test
    fun `on Api calls progressBar displays`() = runTest {
        whenever(mockedHomeRepository.getPosts()).thenReturn(
            flow {
                delay(3000)
                emit(Result.success(fakePostList))
            }
        )
        viewModel = HomeViewModel(mockedHomeRepository)
        assertTrue(viewModel.stateValue.first().isLoading)
    }

    @Test
    fun `on Api call success stores list`() = runTest {
        assertTrue(viewModel.stateValue.first().isLoading.not())
        // check if size is correct
        assertEquals(fakePostList.size, viewModel.stateValue.first().content.size)
        // check some items
        assertEquals(fakePostList[0], viewModel.stateValue.first().content[0])
    }

    @Test
    fun `on api call failure adds failure message`() = runTest {
        whenever(mockedHomeRepository.getPosts()).thenReturn(
            flow {
                emit(Result.failure(fakeException))
            })
        viewModel = HomeViewModel(mockedHomeRepository)
        assertTrue(viewModel.stateValue.first().isLoading.not())
        assertEquals(fakeErrorMessage, viewModel.stateValue.first().postApiFailureMessage)
        assertTrue(viewModel.stateValue.first().content.isEmpty())
    }
}