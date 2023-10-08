package com.example.qfonappinterviewtask.ui.createpoll

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Message
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.qfonappinterviewtask.R
import com.example.qfonappinterviewtask.data.model.Polls
import com.example.qfonappinterviewtask.data.model.PollsList
import com.example.qfonappinterviewtask.databinding.CreatePollActivityBinding
import com.example.qfonappinterviewtask.ui.createpoll.adepter.PollAnswerAdepter
import com.example.qfonappinterviewtask.ui.createpoll.viewmodel.CreatePollViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Collections


@AndroidEntryPoint
class CreatePollActivity : AppCompatActivity() {

    private lateinit var binding: CreatePollActivityBinding

    private lateinit var pollAnswerAdepter: PollAnswerAdepter

    private val createPollViewModel: CreatePollViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CreatePollActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpClickListener()
        setUpRecyclerView()
        observeLiveData()
    }

    private fun observeLiveData() {

    }

    private fun setUpRecyclerView() {
        pollAnswerAdepter = PollAnswerAdepter {
            if (pollAnswerAdepter.pollAnswerList.size <= 5) {
                addEditTextViewInRvView()
            }
        }
        binding.pollAnswerOptionRv.apply {
            adapter = pollAnswerAdepter
            layoutManager = LinearLayoutManager(this@CreatePollActivity)
            setHasFixedSize(true)
        }

        val moveItemCallback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END,
                0
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fromPosition = viewHolder.adapterPosition
                val toPosition = target.adapterPosition
                Collections.swap(pollAnswerAdepter.pollAnswerList, fromPosition, toPosition)
                recyclerView.adapter!!.notifyItemMoved(fromPosition, toPosition)
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
        }

        val itemTouchHelper = ItemTouchHelper(moveItemCallback)
        itemTouchHelper.attachToRecyclerView(binding.pollAnswerOptionRv)


    }


    @SuppressLint("SetTextI18n")
    private fun setUpClickListener() {
        binding.createPollButton.setOnClickListener {
            validateAndAddPollInDataBase()

        }

        binding.addOptionButton.setOnClickListener {
            addEditTextViewInRvView()
        }


        binding.backButton.setOnClickListener {
            this.finish()
        }
    }

    private fun addEditTextViewInRvView() {
        if (pollAnswerAdepter.pollAnswerList.size <= 4) {

            pollAnswerAdepter.pollAnswerList.add("")
            pollAnswerAdepter.notifyDataSetChanged()


            if (binding.pollAnswerOptionRv.visibility == View.GONE) {
                binding.pollAnswerOptionRv.visibility = View.VISIBLE
            }

            if (pollAnswerAdepter.pollAnswerList.size == 5) {
                binding.addOptionButton.isEnabled = false
                binding.textViewCanAdd5Answer.visibility = View.GONE
            } else {
                val index = 5 - pollAnswerAdepter.pollAnswerList.size
                binding.textViewCanAdd5Answer.text =
                    "${getString(R.string.you_can_add)} $index ${getString(R.string.label_more_option)} "
            }

        } else {
            binding.addOptionButton.isEnabled = false
        }
    }

    private fun validateAndAddPollInDataBase() {
        val listItemNotEmpty =
            pollAnswerAdepter.pollAnswerList.filter { it.isNotEmpty() && it.isNotBlank() }

        if (binding.questionEditText.text.isNotEmpty() && binding.questionEditText.text.isNotBlank()) {
            if (listItemNotEmpty.size >= 2) {
                val list = arrayListOf<Polls>()

                if (!hasDuplicateItems(pollAnswerAdepter.pollAnswerList)){
                    for (i in pollAnswerAdepter.pollAnswerList) {
                        if (i.isNotEmpty() && i.isNotBlank()){
                            list.add(Polls(i))
                        }

                    }
                    val pollQuestion = PollsList(
                        pollQuestionTitle = binding.questionEditText.text.toString(),
                        pollsList = list
                    )

                    createPollViewModel.addPoll(pollQuestion)
                    this.finish()
                }else{
                    showToast(getString(R.string.validation_please_remove_duplicate_value))
                }

            } else {
                showToast(getString(R.string.validation_please_add_minimum_2_option_answer_for_poll))
            }

        } else {
            showToast(getString(R.string.validation_please_add_valid_poll_question))

        }


    }

    fun hasDuplicateItems(list: MutableList<String>): Boolean {
        val uniqueItems = HashSet<String>()

        for (i in list) {
            if (!uniqueItems.add(i)) {
                // A duplicate item was found
                return true
            }
        }

        // No duplicate items found
        return false
    }

    fun showToast(message: String){
        Toast.makeText(this,message, Toast.LENGTH_LONG).show()
    }
}