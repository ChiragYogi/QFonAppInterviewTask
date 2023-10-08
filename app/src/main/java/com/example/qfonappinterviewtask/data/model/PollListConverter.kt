package com.example.qfonappinterviewtask.data.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


object PollListConverter {
    @TypeConverter
    @JvmStatic
    fun fromJson(value: String): List<Polls> {
        val listType = object : TypeToken<List<Polls>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    @JvmStatic
    fun toJson(list: List<Polls>): String {
        return Gson().toJson(list)
    }
}
