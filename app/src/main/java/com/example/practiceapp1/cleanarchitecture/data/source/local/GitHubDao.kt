package com.example.practiceapp1.cleanarchitecture.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.practiceapp1.cleanarchitecture.data.model.LocalGitHubData
import kotlinx.coroutines.flow.Flow

/**
 * Created by Asif Khan on 04/11/23.
 */

@Dao
interface GitHubDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: List<LocalGitHubData>)

    @Update
    fun update(data: LocalGitHubData)

    @Delete
    fun delete(data: LocalGitHubData)

    @Query("delete from github_table")
    fun deleteAllData()

    // To observe changes in database, we use Flow
    @Query("Select * from github_table order by id")
    fun getAllData(): Flow<List<LocalGitHubData>>

    @Query("Select name from github_table where id = :id")
    fun getNameById(id: Int): String
}