package com.example.qfonappinterviewtask.utills

import android.content.Context
import android.view.View
import android.widget.Toast

fun View.show(){
    this.visibility = View.VISIBLE
}

fun View.hide(){
    this.visibility = View.INVISIBLE
}

fun View.gone(){
    this.visibility = View.GONE
}

