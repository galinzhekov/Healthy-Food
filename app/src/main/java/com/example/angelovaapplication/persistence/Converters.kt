package com.example.angelovaapplication.persistence

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Converters {
    @TypeConverter
    @JvmStatic
    fun fromIntList(value: ArrayList<Int>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    @JvmStatic
    fun toIntList(value: String): ArrayList<Int> {
        val listType = object : TypeToken<ArrayList<Int>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    @JvmStatic
    fun fromPairList(value: ArrayList<Pair<String, String>>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    @JvmStatic
    fun toPairList(value: String): ArrayList<Pair<String, String>> {
        val listType = object : TypeToken<ArrayList<Pair<String, String>>>() {}.type
        return Gson().fromJson(value, listType)
    }
}