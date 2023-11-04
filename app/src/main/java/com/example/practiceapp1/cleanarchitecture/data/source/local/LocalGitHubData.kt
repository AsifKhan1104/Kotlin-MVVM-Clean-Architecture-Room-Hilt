package com.example.practiceapp1.cleanarchitecture.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Asif Khan on 04/11/23.
 */

@Entity(tableName = "github_table")
data class LocalGitHubData(
    @PrimaryKey(autoGenerate = false) val id:Int? = null,
    val name: String,
    val description: String,
    val url: String,
    val imageUrl: String
)