package com.example.practiceapp1.cleanarchitecture.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.practiceapp1.cleanarchitecture.data.model.Item
import com.example.practiceapp1.cleanarchitecture.data.model.MainGitHubData
import com.example.practiceapp1.databinding.ItemGithubBinding

/**
 * Created by Asif Khan on 04/11/23.
 */
class GithubAdapter(private val context: Context, private val list: List<MainGitHubData>) :
    RecyclerView.Adapter<GithubAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: ItemGithubBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemGithubBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {
                // set image
                Glide.with(context)
                    .load(imageUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .into(binding.imageViewIcon)

                binding.textViewRepoName.text = name
                binding.textViewRepoDesc.text = description
                binding.textViewRepoUrl.text = url
            }
        }
    }
}