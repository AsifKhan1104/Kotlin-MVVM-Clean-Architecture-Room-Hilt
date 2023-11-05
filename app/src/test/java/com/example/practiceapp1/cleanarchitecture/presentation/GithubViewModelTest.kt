package com.example.practiceapp1.cleanarchitecture.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.practiceapp1.cleanarchitecture.data.GitHubRepository
import com.example.practiceapp1.cleanarchitecture.util.DataHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Created by Asif Khan on 05/11/23.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class GithubViewModelTest {
    private val queryParam = "android"
    private val testDispatcher = StandardTestDispatcher()

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var gitHubRepository: GitHubRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun test_getList_success() = runTest {
        Mockito.`when`(gitHubRepository.fetchListRemote(queryParam))
            .thenReturn(flowOf(DataHandler.SUCCESS(emptyList())))

        val viewModel = GithubViewModel(gitHubRepository)
        val result = viewModel.getList(queryParam).test {
            testDispatcher.scheduler.advanceUntilIdle()
            Assert.assertEquals(0, awaitItem().data?.size)
            awaitComplete()
        }
    }

    @Test
    fun test_getList_error() = runTest {
        Mockito.`when`(gitHubRepository.fetchListRemote(queryParam))
            .thenReturn(flowOf(DataHandler.FAILURE(emptyList(), "Something Went Wrong")))

        val viewModel = GithubViewModel(gitHubRepository)
        val result = viewModel.getList(queryParam).test {
            testDispatcher.scheduler.advanceUntilIdle()
            Assert.assertEquals(true, awaitItem() is DataHandler.FAILURE)
            awaitComplete()
        }
    }

    @Test
    fun test_getList_error_msg() = runTest {
        Mockito.`when`(gitHubRepository.fetchListRemote(queryParam))
            .thenReturn(flowOf(DataHandler.FAILURE(emptyList(), "Something Went Wrong")))

        val viewModel = GithubViewModel(gitHubRepository)
        val result = viewModel.getList(queryParam).test {
            testDispatcher.scheduler.advanceUntilIdle()
            Assert.assertEquals("Something Went Wrong", awaitItem().msg)
            awaitComplete()
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}