package com.example.qfonappinterviewtask.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.qfonappinterviewtask.data.model.PollsList
import com.example.qfonappinterviewtask.data.dao.PollDao
import com.example.qfonappinterviewtask.data.model.PollListConverter

@Database(entities = [PollsList::class], version = 1, exportSchema = false)
@TypeConverters(PollListConverter::class)
abstract class PollDatabase:RoomDatabase() {
    abstract fun getPollSDao(): PollDao
}