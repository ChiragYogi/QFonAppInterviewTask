package com.example.qfonappinterviewtask.ui.home.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qfonappinterviewtask.databinding.HistoryFragmentBinding
import com.example.qfonappinterviewtask.ui.home.adepter.ItemDecoration
import com.example.qfonappinterviewtask.ui.home.adepter.PollsListRecyclerView
import com.example.qfonappinterviewtask.ui.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private lateinit var binding: HistoryFragmentBinding

    private val homeViewModel:HomeViewModel by viewModels()

    private lateinit var pollsListRecyclerView: PollsListRecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private fun observePollsListLiveData() {
        homeViewModel.historyPollResponse.observe(viewLifecycleOwner) { pollList ->

            if (pollList.isNotEmpty()){
                binding.historyPollView.visibility = View.VISIBLE
                binding.textViewCanAdd5Answer.visibility = View.GONE
                pollsListRecyclerView.pollsList = pollList.toMutableList()
            }else{
                binding.historyPollView.visibility = View.GONE
                binding.textViewCanAdd5Answer.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HistoryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewRecyclerView()
        observePollsListLiveData()
    }

    private fun setUpViewRecyclerView() {
        pollsListRecyclerView = PollsListRecyclerView(true) { data, chiledIndex, patentIndex ->

        }
        binding.historyPollView.apply {
            adapter = pollsListRecyclerView
            this.layoutManager = LinearLayoutManager(requireContext())
        }

        binding.historyPollView.addItemDecoration(ItemDecoration(48))

        homeViewModel.navigatedToNextScreenConfirm(false)
    }


}