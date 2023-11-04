package com.example.practiceapp1.cleanarchitecture.di

import android.content.Context
import com.example.practiceapp1.cleanarchitecture.data.source.local.GitHubDao
import com.example.practiceapp1.cleanarchitecture.data.source.local.GitHubDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Asif Khan on 04/11/23.
 */

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun providesGitHubDao(database: GitHubDatabase): GitHubDao {
        return database.githubDao()
    }

    @Provides
    @Singleton
    fun providesGitHubDatabase(@ApplicationContext appContext: Context): GitHubDatabase {
        return GitHubDatabase.getInstance(appContext)
    }
}