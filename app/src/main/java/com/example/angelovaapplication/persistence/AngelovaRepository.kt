package com.example.angelovaapplication.persistence

import android.content.Context
import com.example.angelovaapplication.model.Category
import com.example.angelovaapplication.model.Recipe

class AngelovaRepository(context: Context) {

    private var angelovaDatabase: AngelovaDatabase? = null

    init {
        angelovaDatabase = AngelovaDatabase.getInstance(context)
    }

    suspend fun insertCategory(category: Category) {
        angelovaDatabase?.categoryDao?.insert(category)
    }

    suspend fun getAllCategories(): List<Category> = angelovaDatabase?.categoryDao?.getAllCategories() ?: listOf()



    suspend fun insertRecipe(recipe: Recipe) {
        angelovaDatabase?.recipeDao?.insert(recipe)
    }

    suspend fun getAllRecipes(): List<Recipe> = angelovaDatabase?.recipeDao?.getAllRecipes() ?: listOf()

    suspend fun updateRecipeBookmarkStatus(id: Long): Boolean {
        return angelovaDatabase?.recipeDao?.toggleBookmarkStatus(id) ?: false
    }

    suspend fun getRecipeById(id: Long): Recipe? = angelovaDatabase?.recipeDao?.getRecipeById(id)

    suspend fun getBookmarkedRecipes(): List<Recipe> = angelovaDatabase?.recipeDao?.getBookmarkedRecipes() ?: listOf()
}