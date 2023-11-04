package com.example.practiceapp1.cleanarchitecture.data

import com.example.practiceapp1.cleanarchitecture.data.model.GithubData
import com.example.practiceapp1.cleanarchitecture.data.model.Item
import com.example.practiceapp1.cleanarchitecture.data.model.MainGitHubData
import com.example.practiceapp1.cleanarchitecture.data.source.local.GitHubDao
import com.example.practiceapp1.cleanarchitecture.data.source.network.Remote
import com.example.practiceapp1.cleanarchitecture.util.DataHandler
import com.example.practiceapp1.cleanarchitecture.util.Extensions.toMainGitHubData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Created by Asif Khan on 03/11/23.
 */

class GitHubRepository @Inject constructor(
    private val remoteApi: Remote,
    private val localDao: GitHubDao
) {

    fun fetchListLocal(): Flow<DataHandler<GithubData>> =
        flow {
            emit(DataHandler.LOADING())

            localDao.getAllData()
        }

    fun fetchListRemote(queryParam: String): Flow<DataHandler<List<MainGitHubData>>> =
        flow {
            emit(DataHandler.LOADING())
            val response = remoteApi.getGitHubList(queryParam)
            lateinit var result: DataHandler<List<MainGitHubData>>

            if (response.isSuccessful) {
                val responseBody = response.body()
                responseBody?.let {
                    result = DataHandler.SUCCESS(getCorrectFormattedData(it.items))
                }
            } else {
                result = DataHandler.FAILURE(msg = response.errorBody().toString())
            }
            emit(result)
        }.flowOn(Dispatchers.IO)

    private fun getCorrectFormattedData(list: List<Item>): List<MainGitHubData> {
        var finalList: MutableList<MainGitHubData> = ArrayList()
        for (item in list) {
            finalList.add(item.toMainGitHubData())
        }
        return finalList
    }
}