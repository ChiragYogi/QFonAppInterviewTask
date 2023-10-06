package com.example.qfonappinterviewtask.ui.adepter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.qfonappinterviewtask.databinding.RowPollItemViewBinding
import com.example.qfonappinterviewtask.model.PollsList
import com.example.qfonappinterviewtask.ui.adepter.PollsListRecyclerView.*

class PollsListRecyclerView:RecyclerView.Adapter<PollsListViewAdepter>() {


    var pollsList = mutableListOf<PollsList>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    class PollsListViewAdepter(private val binding: RowPollItemViewBinding):ViewHolder(binding.root) {
        fun bind(item: PollsList) {

            binding.textViewPollTitle.text  = item.pollQuestionTitle



        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PollsListViewAdepter {
        val currentView = RowPollItemViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
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