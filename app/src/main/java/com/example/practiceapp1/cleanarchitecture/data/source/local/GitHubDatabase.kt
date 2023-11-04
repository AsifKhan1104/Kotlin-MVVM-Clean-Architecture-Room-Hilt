package com.example.practiceapp1.cleanarchitecture.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.practiceapp1.cleanarchitecture.data.model.LocalGitHubData

/**
 * Created by Asif Khan on 04/11/23.
 */

@Database(entities = [LocalGitHubData::class], version = 1)
abstract class GitHubDatabase : RoomDatabase() {
    abstract fun githubDao(): GitHubDao

    companion object {
        private var instance: GitHubDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): GitHubDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    ctx.applicationContext,
                    GitHubDatabase::class.java,
                    "github_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()
            }
            return instance!!
        }

        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                //populateDatabase(instance!!)
            }
        }

        /*private fun populateDatabase(db: GitHubDatabase) {
            val gDao = db.githubDao()
            subscribeOnBackground {
                gDao.insert(LocalGitHubData())
            }
        }*/
    }
}