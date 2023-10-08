package com.example.qfonappinterviewtask.ui.home.adepter


import android.animation.ValueAnimator
import android.annotation.SuppressLint

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.qfonappinterviewtask.data.model.Polls
import com.example.qfonappinterviewtask.databinding.RowPollInnerItemViewBinding
import com.example.qfonappinterviewtask.ui.home.adepter.PollsInnerRecyclerView.*
import com.example.qfonappinterviewtask.ui.home.adepter.PollsListRecyclerView.*
import com.example.qfonappinterviewtask.utills.gone
import com.example.qfonappinterviewtask.utills.hide
import com.example.qfonappinterviewtask.utills.show


class PollsInnerRecyclerView(val fromHistory:Boolean,val onPollItemClick:(List<Polls>, Int) -> Unit):RecyclerView.Adapter<PollsInnerViewAdepter>() {

    private var selectedItem = -1
    var innerPollList = mutableListOf<Polls>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class PollsInnerViewAdepter(private val binding: RowPollInnerItemViewBinding):ViewHolder(binding.root) {
        fun bind(item: Polls) {
            binding.textViewPollAnswerName.text = item.pollAnswerTitle
            if (!fromHistory){
                val position = adapterPosition
                binding.rowItemRadioButton.isChecked = item.pollSelected
                binding.rowItemRadioButton.visibility = if (!item.pollSelected) View.VISIBLE else View.INVISIBLE
                binding.itemProgressBar.visibility = View.VISIBLE
                binding.pollAnsearedImage.visibility =  if (item.pollSelected) View.VISIBLE else View.GONE

                if (selectedItem != -1){
                    if (item.pollSelected){
                        binding.rowItemRadioButton.hide()
                        binding.progressCount.show()
                        startProgressBarAnimationFor(binding.itemProgressBar, 0,100,binding.progressCount)
                    }else{
                        binding.rowItemRadioButton.hide()
                        binding.progressCount.show()
                        startProgressBarAnimationFor(binding.itemProgressBar, 100,0,binding.progressCount)
                    }
                }

            }else{


                if (item.pollSelected){
                    binding.rowItemRadioButton.isChecked = item.pollSelected
                    binding.rowItemRadioButton.hide()
                    binding.pollAnsearedImage.show()
                    binding.progressCount.show()
                    binding.progressCount.text = "100%"
                    binding.itemProgressBar.progress = 100
                    binding.itemProgressBar.visibility = View.VISIBLE
                }else{
                    binding.rowItemRadioButton.hide()
                    binding.pollAnsearedImage.gone()
                    binding.progressCount.show()
                    binding.progressCount.text = "0%"
                    binding.itemProgressBar.progress = 0
                    binding.itemProgressBar.visibility = View.GONE
                }
            }

        }

        init {
            binding.rowItemRadioButton.setOnClickListener {
                updateView()
            }
            binding.progressCount.setOnClickListener {
                updateView()
            }

            binding.textViewPollAnswerName.setOnClickListener {
                updateView()
            }

           /* binding.root.setOnClickListener {
                onPollItemClick.invoke(innerPollList,adapterPosition)
            }*/
        }

        fun updateView(){
            if (!fromHistory){
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {

                    val  lastSelectedItem = selectedItem;
                    selectedItem = position

                    if (lastSelectedItem != selectedItem){

                        innerPollList.forEach { it.pollSelected = false }
                        innerPollList[selectedItem].pollSelected = true
                        onPollItemClick.invoke(innerPollList,adapterPosition)
                        if (lastSelectedItem != selectedItem){

                            notifyItemChanged(lastSelectedItem);
                            startProgressBarAnimationFor(binding.itemProgressBar, 100,0,binding.progressCount)
                            notifyItemChanged(selectedItem);

                           // notifyDataSetChanged()
                        }

                    }

                }
            }
        }
    }

    private fun startProgressBarAnimationFor(
        progressBar: ProgressBar,initialValue:Int,finalValue:Int,
        textView:TextView) {
        val animator = ValueAnimator.ofInt(initialValue, finalValue)
        animator.duration = 500L
        animator.interpolator = DecelerateInterpolator()

        animator.addUpdateListener { valueAnimator ->
            val animatedValue = valueAnimator.animatedValue as Int
            progressBar.progress = animatedValue
            textView.text = "$animatedValue%"
        }
        animator.start()

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