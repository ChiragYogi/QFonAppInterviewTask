package com.example.qfonappinterviewtask.model

data class PollsList (
    val pollQuestionTitle:String,
    val pollsList:List<Polls>
)

data class Polls(
    val pollAnswerTitle:String,
    val pollProgress:String,
    val pollSelected:Boolean = false
)