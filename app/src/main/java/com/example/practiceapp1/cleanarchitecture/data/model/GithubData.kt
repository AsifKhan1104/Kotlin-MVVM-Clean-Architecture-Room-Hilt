package com.example.practiceapp1.cleanarchitecture.data.model

data class GithubData(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)