package com.example.qfonappinterviewtask.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.qfonappinterviewtask.data.dao.PollDao
import com.example.qfonappinterviewtask.data.model.PollsList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val pollDao: PollDao) : ViewModel() {



    private val _currentPollResponse = pollDao.getCurrentPollList().asLiveData()
    val currentPollResponse: LiveData<List<PollsList>> get() = _currentPollResponse

    private val _historyPollResponse =  pollDao.getHistoryPollList().asLiveData()

    val historyPollResponse: LiveData<List<PollsList>> get() = _historyPollResponse



    //navigation and save data
    private val _onNavigateToNextScreen= MutableLiveData<Boolean>()
    val onNavigateToNextScreen:LiveData<Boolean> get() = _onNavigateToNextScreen
    fun navigatedToNextScreenConfirm(isNavigating: Boolean){
        Log.d("navigate to next screen value is", "value is in view model ${isNavigating}")
        _onNavigateToNextScreen.postValue(isNavigating)
    }
    fun updateDataInDao(list: MutableList<PollsList>){
        Log.d("list in viewmodel","${list}")
        val currentSleetedPollList = arrayListOf<PollsList>()
        for (i in list){
            if (i.pollQuestionAnswered){
                for (j in i.pollsList){
                    if (j.pollSelected){
                        currentSleetedPollList.add(i)
                    }
                }
            }

        }

        if (currentSleetedPollList.isNotEmpty()){
            viewModelScope.launch {
                currentSleetedPollList.forEach {
                    pollDao.updatePollsList(it)
                }
              _onNavigateToNextScreen.postValue(false)
            }
        }


        Log.d("currentlist","${currentSleetedPollList}")

    }




}