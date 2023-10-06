package com.example.qfonappinterviewtask.ui.adepter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.qfonappinterviewtask.databinding.RowPollInnerItemViewBinding
import com.example.qfonappinterviewtask.model.Polls
import com.example.qfonappinterviewtask.model.PollsList
import com.example.qfonappinterviewtask.ui.adepter.PollsInnerRecyclerView.*
import com.example.qfonappinterviewtask.ui.adepter.PollsListRecyclerView.*

class PollsInnerRecyclerView:RecyclerView.Adapter<PollsInnerViewAdepter>() {


    var innerPollList = mutableListOf<Polls>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class PollsInnerViewAdepter(private val binding: RowPollInnerItemViewBinding):ViewHolder(binding.root) {
        fun bind(item: Polls) {





        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PollsInnerViewAdepter {
        val currentView = RowPollInnerItemViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PollsInnerViewAdepter(currentView)
    }

    override fun getItemCount(): Int {
       return innerPollList.size
    }

    override fun onBindViewHolder(holder: PollsInnerViewAdepter, position: Int) {
        val item = innerPollList[position]
        holder.bind(item)

    }
}