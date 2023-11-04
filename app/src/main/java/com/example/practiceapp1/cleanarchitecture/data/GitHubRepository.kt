package com.example.practiceapp1.cleanarchitecture.data

import com.example.practiceapp1.cleanarchitecture.data.model.Item
import com.example.practiceapp1.cleanarchitecture.data.model.LocalGitHubData
import com.example.practiceapp1.cleanarchitecture.data.source.local.GitHubDao
import com.example.practiceapp1.cleanarchitecture.data.source.network.Remote
import com.example.practiceapp1.cleanarchitecture.util.Extensions.toLocalGitHubData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Asif Khan on 03/11/23.
 */

// Database will be Single Source of Truth for data in app
// Whenever database runs out of data, it will hit network api
// & save new data in database
class GitHubRepository @Inject constructor(
    private val remoteApi: Remote,
    private val localDao: GitHubDao
) {
    fun fetchListLocal(): Flow<List<LocalGitHubData>> = localDao.getAllData()

    /*fun fetchListRemote(queryParam: String): Flow<DataHandler<List<LocalGitHubData>>> =
        flow {
            emit(DataHandler.LOADING())
            val response = remoteApi.getGitHubList(queryParam)
            lateinit var result: DataHandler<List<LocalGitHubData>>

            if (response.isSuccessful) {
                val responseBody = response.body()
                responseBody?.let {
                    var formattedData = getCorrectFormattedData(it.items)
                    result = DataHandler.SUCCESS(formattedData)
                    localDao.insert(formattedData)
                }
            } else {
                result = DataHandler.FAILURE(msg = response.errorBody().toString())
            }
            emit(result)
        }.flowOn(Dispatchers.IO)*/


    // fetches the data from network & stores in Room Db
    fun fetchListRemoteAndSaveInDb(queryParam: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = remoteApi.getGitHubList(queryParam)

            if (response.isSuccessful) {
                val responseBody = response.body()
                responseBody?.let {
                    var formattedData = getCorrectFormattedData(it.items)
                    localDao.insert(formattedData)
                }
            } else {
                // handle exception
            }
        }
    }

    private fun getCorrectFormattedData(list: List<Item>): List<LocalGitHubData> {
        var finalList: MutableList<LocalGitHubData> = ArrayList()
        for (item in list) {
            finalList.add(item.toLocalGitHubData())
        }
        return finalList
    }
}