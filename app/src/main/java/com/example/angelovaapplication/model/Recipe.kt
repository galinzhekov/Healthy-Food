package com.example.angelovaapplication.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "recipes")
@Parcelize
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var name: String = "",
    var description: String = "",
    var imageResourceId: Int = 0,
    var categoriesList: ArrayList<Int> = arrayListOf(),
    var informationLabelsList: ArrayList<Pair<String, String>> = arrayListOf(),
    var isBookmarked: Boolean = false
): Parcelable