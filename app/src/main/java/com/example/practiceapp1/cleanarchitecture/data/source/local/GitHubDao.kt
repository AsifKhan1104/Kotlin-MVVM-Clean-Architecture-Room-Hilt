package com.example.practiceapp1.cleanarchitecture.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.practiceapp1.cleanarchitecture.data.model.LocalGitHubData

/**
 * Created by Asif Khan on 04/11/23.
 */

@Dao
interface GitHubDao {

    @Insert
    fun insert(data: LocalGitHubData)

    @Update
    fun update(data: LocalGitHubData)

    @Delete
    fun delete(data: LocalGitHubData)

    @Query("delete from github_table")
    fun deleteAllData()

    @Query("Select * from github_table")
    fun getAllData(): List<LocalGitHubData>
}