package com.example.qfonappinterviewtask.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "polls_database")
data class PollsList (
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    val pollQuestionTitle:String,
    var pollQuestionAnswered:Boolean = false,
    @TypeConverters(PollListConverter::class) val pollsList:List<Polls>
)
@Entity
data class Polls(
    val pollAnswerTitle:String,
    var pollSelected:Boolean = false
)

const val POL_TABLE_NAME = "polls_table"