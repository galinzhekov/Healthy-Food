package com.example.angelovaapplication.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.angelovaapplication.model.Category
import com.example.angelovaapplication.model.Recipe


@Database(entities = [Category::class, Recipe::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AngelovaDatabase : RoomDatabase() {
    abstract val categoryDao: CategoryDao?
    abstract val recipeDao: RecipeDao?

    companion object {
        const val DATABASE_NAME = "angelova_db"
        var instance: AngelovaDatabase? = null
        fun getInstance(context: Context): AngelovaDatabase? {
            if (instance == null) {
                instance = databaseBuilder(
                    context.applicationContext, AngelovaDatabase::class.java, DATABASE_NAME
                ).build()
                return instance
            } else
            return instance
        }
    }
}