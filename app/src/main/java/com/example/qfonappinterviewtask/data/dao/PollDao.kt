package com.example.qfonappinterviewtask.data.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.qfonappinterviewtask.data.model.PollsList
import kotlinx.coroutines.flow.Flow
@Dao
interface PollDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPollsList(pollsList: PollsList)

    @Query("SELECT * FROM polls_database WHERE pollQuestionAnswered == 1 ORDER BY id DESC")
    fun getHistoryPollList(): Flow<List<PollsList>>

    @Query("SELECT * FROM polls_database WHERE pollQuestionAnswered == 0 ORDER BY id DESC")
    fun getCurrentPollList(): Flow<List<PollsList>>

    @Update
    suspend fun updatePollsList(pollsList: PollsList)

}