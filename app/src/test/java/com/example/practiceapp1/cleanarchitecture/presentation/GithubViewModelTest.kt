package com.example.practiceapp1.cleanarchitecture.presentation

import com.example.practiceapp1.cleanarchitecture.data.GitHubRepository
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * Created by Asif Khan on 05/11/23.
 */
class GithubViewModelTest {

    @Mock
    lateinit var repository: GitHubRepository
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @After
    fun tearDown() {
    }
}