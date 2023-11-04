package com.example.practiceapp1.cleanarchitecture.data

import com.example.practiceapp1.cleanarchitecture.data.model.GithubData
import com.example.practiceapp1.cleanarchitecture.data.source.network.Api
import com.example.practiceapp1.cleanarchitecture.util.DataHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Created by Asif Khan on 03/11/23.
 */

class GitHubRepository @Inject constructor(private val api: Api) {

    fun fetchList(queryParam: String): Flow<DataHandler<GithubData>> =
        flow {
            emit(DataHandler.LOADING())
            val response = api.getGitHubList(queryParam)
            lateinit var result: DataHandler<GithubData>

            if (response.isSuccessful) {
                val responseBody = response.body()
                responseBody?.let {
                    result = DataHandler.SUCCESS(it)
                }
            } else {
                result = DataHandler.FAILURE(msg = response.errorBody().toString())
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
}