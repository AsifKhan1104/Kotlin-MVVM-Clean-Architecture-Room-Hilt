package com.example.practiceapp1.cleanarchitecture.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practiceapp1.cleanarchitecture.util.DataHandler
import com.example.practiceapp1.cleanarchitecture.data.model.GithubData
import com.example.practiceapp1.databinding.ActivityGithubBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GithubActivity : AppCompatActivity() {
    private val viewModel: GithubViewModel by viewModels()
    private val TAG = "GithubActivity"
    private val queryParam = "android"
    private lateinit var mBinding: ActivityGithubBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityGithubBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadGitHubList(queryParam).collect { it ->
                    when (it) {
                        is DataHandler.SUCCESS -> {
                            mBinding.progressBar.visibility = View.GONE
                            mBinding.recyclerView.apply {
                                layoutManager = LinearLayoutManager(this@GithubActivity)
                                adapter = GithubAdapter(
                                    this@GithubActivity,
                                    (it.data as GithubData).items
                                )
                            }
                        }

                        is DataHandler.FAILURE -> {
                            mBinding.progressBar.visibility = View.GONE
                            Log.v(TAG, it.toString())
                        }

                        is DataHandler.LOADING -> {
                            mBinding.progressBar.visibility = View.VISIBLE
                        }

                        else -> {

                        }
                    }
                }
            }
        }
    }
}