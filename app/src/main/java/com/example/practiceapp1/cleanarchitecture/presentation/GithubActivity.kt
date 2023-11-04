package com.example.practiceapp1.cleanarchitecture.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
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
                // load initial data from remote
                viewModel.loadGitHubListRemote(queryParam)

                // Now collect data from database
                viewModel.loadGitHubList().collect { it ->

                    mBinding.progressBar.visibility = View.GONE
                    mBinding.recyclerView.apply {
                        layoutManager = LinearLayoutManager(this@GithubActivity)
                        adapter = GithubAdapter(
                            this@GithubActivity,
                            it
                        )
                    }

                    //when (true) {
                    /*is DataHandler.SUCCESS -> {*/

                    //}

                    /* is DataHandler.FAILURE -> {
                         mBinding.progressBar.visibility = View.GONE
                         Log.v(TAG, it.toString())
                     }

                     is DataHandler.LOADING -> {
                         mBinding.progressBar.visibility = View.VISIBLE
                     }*/

                    /*else -> {

                    }
                }*/
                }
            }
        }
    }
}