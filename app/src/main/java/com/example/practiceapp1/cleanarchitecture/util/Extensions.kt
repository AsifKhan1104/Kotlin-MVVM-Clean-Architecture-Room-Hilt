package com.example.practiceapp1.cleanarchitecture.util

import com.example.practiceapp1.cleanarchitecture.data.model.Item
import com.example.practiceapp1.cleanarchitecture.data.model.LocalGitHubData

/**
 * Created by Asif Khan on 04/11/23.
 */
object Extensions {
    fun Item.toLocalGitHubData(): LocalGitHubData =
        LocalGitHubData(
            id = id,
            name = name,
            description = description,
            url = url,
            imageUrl = owner.avatar_url
        )
}