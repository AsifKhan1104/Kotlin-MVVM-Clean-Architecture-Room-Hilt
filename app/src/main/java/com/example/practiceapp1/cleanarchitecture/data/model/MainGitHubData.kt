package com.example.practiceapp1.cleanarchitecture.data.model


/**
 * Created by Asif Khan on 04/11/23.
 */

data class MainGitHubData(
    val id: Int,
    val name: String,
    val description: String? = "",
    val url: String,
    val imageUrl: String
)