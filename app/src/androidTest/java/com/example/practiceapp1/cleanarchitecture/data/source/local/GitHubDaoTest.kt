package com.example.practiceapp1.cleanarchitecture.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import com.example.practiceapp1.cleanarchitecture.data.model.LocalGitHubData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by Asif Khan on 05/11/23.
 */
class GitHubDaoTest {

    @get: Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var gitHubDatabase: GitHubDatabase
    private lateinit var gitHubDao: GitHubDao

    @Before
    fun setUp() {
        gitHubDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            GitHubDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        gitHubDao = gitHubDatabase.githubDao()
    }

    @Test
    fun test_insert_values() = runBlocking {
        val data = listOf<LocalGitHubData>(
            LocalGitHubData(1, "asif", "engineer saheb", "google.com", ""),
            LocalGitHubData(2, "khan", "saheb", "brave.com", "")
        )

        gitHubDao.insert(data)

        val result = gitHubDao.getAllData().test {
            // it will return the first flow item,
            // since flow is a continuous data stream
            val list = awaitItem()
            Assert.assertEquals(2, list.size)
            // cancel, as we don't want next data streams
            cancel()
        }
    }

    @Test
    fun test_with2insert_statements() = runBlocking {
        val data = listOf<LocalGitHubData>(
            LocalGitHubData(1, "asif", "engineer saheb", "google.com", ""),
            LocalGitHubData(2, "khan", "saheb", "brave.com", "")
        )

        gitHubDao.insert(data)

        launch {
            delay(500)

            val data = listOf<LocalGitHubData>(
                LocalGitHubData(3, "asif", "engineer saheb", "google.com", ""),
                LocalGitHubData(4, "khan", "saheb", "brave.com", "")
            )

            gitHubDao.insert(data)
        }

        val result = gitHubDao.getAllData().test {
            // it will return the first flow item,
            // since flow is a continuous data stream
            val list = awaitItem()
            Assert.assertEquals(2, list.size)
            // since some items are inserted after sometime
            val list2 = awaitItem()
            Assert.assertEquals(4, list2.size)
            // cancel, as we don't want next data streams
            cancel()
        }
    }

    @Test
    fun test_get_name() {
        val data = listOf<LocalGitHubData>(
            LocalGitHubData(1, "asif", "engineer saheb", "google.com", ""),
            LocalGitHubData(2, "khan", "saheb", "brave.com", "")
        )

        gitHubDao.insert(data)

        val result = gitHubDao.getNameById(1)

        Assert.assertEquals("asif", result)
    }

    @Test
    fun test_delete_data() {
        val data = listOf<LocalGitHubData>(
            LocalGitHubData(1, "asif", "engineer saheb", "google.com", ""),
            LocalGitHubData(2, "khan", "saheb", "brave.com", "")
        )

        gitHubDao.insert(data)
        gitHubDao.delete(LocalGitHubData(1, "asif", "engineer saheb", "google.com", ""))

        val result = runBlocking {
            gitHubDao.getAllData().test {
                val list = awaitItem()
                Assert.assertEquals(1, list.size)

                gitHubDao.deleteAllData()
                val list1 = awaitItem()
                Assert.assertEquals(0, list1.size)
            }
        }
    }

    @After
    fun tearDown() {
        gitHubDatabase.close()
    }
}