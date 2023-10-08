package com.example.qfonappinterviewtask.di

import android.content.Context
import androidx.room.Room
import com.example.qfonappinterviewtask.data.dao.PollDao
import com.example.qfonappinterviewtask.data.database.PollDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    fun createRoomDataBase(@ApplicationContext appContext:Context): PollDatabase {
        return Room.databaseBuilder(
            appContext,
            PollDatabase::class.java,
            "polls_database"
        ).build()
    }


    @Singleton
    @Provides
    fun providePollDaoDao(pollDatabase: PollDatabase) : PollDao {
        return pollDatabase.getPollSDao()
    }
}