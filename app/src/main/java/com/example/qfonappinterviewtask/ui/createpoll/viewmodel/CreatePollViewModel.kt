package com.example.qfonappinterviewtask.ui.createpoll.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qfonappinterviewtask.data.dao.PollDao
import com.example.qfonappinterviewtask.data.model.PollsList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePollViewModel @Inject constructor(private val pollDao: PollDao) : ViewModel() {



    fun addPoll(pollsList: PollsList) =  viewModelScope.launch{
        pollDao.insertPollsList(pollsList)
    }

}