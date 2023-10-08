package com.example.qfonappinterviewtask.ui.createpoll.adepter

import android.annotation.SuppressLint
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.qfonappinterviewtask.databinding.RowPollAnswerOptionItemBinding
import com.example.qfonappinterviewtask.ui.createpoll.adepter.PollAnswerAdepter.*

class PollAnswerAdepter(val onImeOptionNext:(Int) -> Unit) : RecyclerView.Adapter<PollAnswerViewHolder>() {

    var pollAnswerList = mutableListOf<String>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    inner class PollAnswerViewHolder(private val binding: RowPollAnswerOptionItemBinding) :
        ViewHolder(binding.root) {
        fun bind(currentItem: String) {
            binding.editTextAnswer.setText(currentItem)
            if (adapterPosition == pollAnswerList.size -1 && pollAnswerList.size == 5 ) {
                binding.editTextAnswer.setImeOptions(EditorInfo.IME_ACTION_DONE);
                binding.editTextAnswer.setRawInputType(InputType.TYPE_CLASS_TEXT);
            } else {
                binding.editTextAnswer.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                binding.editTextAnswer.setRawInputType(InputType.TYPE_CLASS_TEXT);
            }
        }

        init {
            binding.editTextAnswer.addTextChangedListener(object :TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(input: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (input?.isNotEmpty() == true && input.isNotBlank()){
                        pollAnswerList[adapterPosition] = input.toString()
                    }
                }

                override fun afterTextChanged(input: Editable?) {
                    if (input?.isNotEmpty() == true && input.isNotBlank()){
                        if (hasDuplicateValue(adapterPosition)){
                            binding.editTextAnswer.setError("already exit")
                        }
                    }


                }
            })

            binding.editTextAnswer.setOnEditorActionListener { textView, id, keyEvent ->
                if (id == EditorInfo.IME_ACTION_NEXT) {
                    onImeOptionNext.invoke(adapterPosition)
                    return@setOnEditorActionListener true
                }
                false
            }
        }
    }

    fun hasDuplicateValue(currentPosition: Int): Boolean {
        val currentText = pollAnswerList[currentPosition]

        for ((index, item) in pollAnswerList.withIndex()) {
            if (index != currentPosition && item == currentText) {
                return true
            }
        }
        return false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PollAnswerViewHolder {
        val binding = RowPollAnswerOptionItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PollAnswerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return pollAnswerList.size
    }
    override fun onBindViewHolder(holder: PollAnswerViewHolder, position: Int) {
        val currentItem = pollAnswerList[position]
        holder.bind(currentItem)
    }
}