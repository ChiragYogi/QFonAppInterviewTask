package com.example.qfonappinterviewtask.ui.home.adepter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.qfonappinterviewtask.databinding.RowPollItemViewBinding
import com.example.qfonappinterviewtask.data.model.Polls
import com.example.qfonappinterviewtask.data.model.PollsList
import com.example.qfonappinterviewtask.ui.home.adepter.PollsListRecyclerView.*

class PollsListRecyclerView(val fromHistory:Boolean,val onPollItemClick: (List<Polls>, Int, Int) -> Unit) :
    RecyclerView.Adapter<PollsListViewAdepter>() {


    var pollsList = mutableListOf<PollsList>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    inner class PollsListViewAdepter(private val binding: RowPollItemViewBinding) :
        ViewHolder(binding.root) {
        fun bind(item: PollsList) {

            binding.textViewPollTitle.text = item.pollQuestionTitle

            binding.innerRecyclerView.apply {
                adapter = PollsInnerRecyclerView(fromHistory = fromHistory ,onPollItemClick = { pollIten, childIndex ->
                    onPollItemClick.invoke(pollIten, childIndex, adapterPosition)
                }).also {
                    it.innerPollList = item.pollsList.toMutableList()
                }
                layoutManager = LinearLayoutManager(context)
                setRecycledViewPool(recycledViewPool)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PollsListViewAdepter {
        val currentView =
            RowPollItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PollsListViewAdepter(currentView)
    }

    override fun getItemCount(): Int {
        return pollsList.size
    }

    override fun onBindViewHolder(holder: PollsListViewAdepter, position: Int) {
        val item = pollsList[position]
        holder.bind(item)

    }
}