package com.example.practiceapp1.cleanarchitecture.data.source.network

import com.example.practiceapp1.cleanarchitecture.data.model.GithubData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Asif Khan on 03/11/23.
 */
interface Remote {

    @GET("/search/repositories")
    suspend fun getGitHubList(@Query("q") queryParam: String): Response<GithubData>
}