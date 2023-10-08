package com.example.qfonappinterviewtask.ui.home.currentpoll

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.qfonappinterviewtask.data.model.PollsList
import com.example.qfonappinterviewtask.databinding.CurrentPollFragmentBinding
import com.example.qfonappinterviewtask.ui.home.adepter.ItemDecoration
import com.example.qfonappinterviewtask.ui.home.adepter.PollsListRecyclerView
import com.example.qfonappinterviewtask.ui.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrentPollFragment : Fragment() {

    private lateinit var binding: CurrentPollFragmentBinding

    private val homeViewModel: HomeViewModel by activityViewModels()


    private lateinit var pollsListRecyclerView: PollsListRecyclerView

    private val currentPoll = arrayListOf<PollsList>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CurrentPollFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewRecyclerView()
        observePollsListLiveData()
    }


    private fun setUpViewRecyclerView() {

        pollsListRecyclerView = PollsListRecyclerView(false) { data, chiledIndex, patentIndex ->

            Log.d(
                "current poll",
                "data is ${data} and at chield is ${chiledIndex} parent is ${patentIndex}"
            )
            if (currentPoll.isNotEmpty()) {
                val question = pollsListRecyclerView.pollsList[patentIndex].pollQuestionTitle
                val id = pollsListRecyclerView.pollsList[patentIndex].id

                val hasSameItemInList = currentPoll.find { it.pollQuestionTitle == question }

                if (hasSameItemInList != null) {
                    val index = currentPoll.indexOf(hasSameItemInList)
                    currentPoll[index] =  PollsList(
                        id=id,
                        pollQuestionTitle = question,
                        pollQuestionAnswered = true,
                        pollsList = data
                    )
                } else {
                    currentPoll.add(
                        PollsList(
                            id=id,
                            pollQuestionTitle = question,
                            pollQuestionAnswered = true,
                            pollsList = data
                        )
                    )
                }
            } else {
                currentPoll.add(
                    PollsList(
                        id=pollsListRecyclerView.pollsList[patentIndex].id,
                        pollQuestionTitle = pollsListRecyclerView.pollsList[patentIndex].pollQuestionTitle,
                        pollQuestionAnswered = true, pollsList = data
                    )
                )
            }

        }



        binding.currentPollView.apply {
            adapter = pollsListRecyclerView
            this.layoutManager = LinearLayoutManager(requireContext())
        }

        binding.currentPollView.addItemDecoration(ItemDecoration(48))



    }

    private fun observePollsListLiveData() {
        homeViewModel.currentPollResponse.observe(requireActivity()) { pollList ->

            if (pollList.isNotEmpty()) {
                binding.textViewCanAdd5Answer.visibility = View.GONE
                binding.currentPollView.visibility = View.VISIBLE
                pollsListRecyclerView.pollsList = pollList.toMutableList()
            } else {
                binding.currentPollView.visibility = View.GONE
                binding.textViewCanAdd5Answer.visibility = View.VISIBLE
            }
        }

        homeViewModel.onNavigateToNextScreen.observe(requireActivity()) {

            if (it) {
                if (currentPoll.isNotEmpty()){
                    homeViewModel.updateDataInDao(currentPoll)
                    currentPoll.clear()
                }
            }
        }
    }




}


