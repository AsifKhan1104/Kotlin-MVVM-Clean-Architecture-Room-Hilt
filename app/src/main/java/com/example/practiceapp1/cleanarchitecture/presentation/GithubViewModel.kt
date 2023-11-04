package com.example.practiceapp1.cleanarchitecture.presentation

import androidx.lifecycle.ViewModel
import com.example.practiceapp1.cleanarchitecture.data.GitHubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Asif Khan on 03/11/23.
 */

@HiltViewModel
class GithubViewModel @Inject constructor(private val repository: GitHubRepository) : ViewModel() {
    val TAG = "GithubViewModel"

    fun loadGitHubList(queryParam: String) = repository.fetchList(queryParam)
}