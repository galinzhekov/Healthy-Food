package com.example.angelovaapplication.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.angelovaapplication.model.Recipe

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: Recipe)

    @Query("SELECT * FROM recipes")
    suspend fun getAllRecipes(): List<Recipe>

    @Query("SELECT * FROM recipes WHERE id = :recipeId")
    suspend fun getRecipeById(recipeId: Long): Recipe?

    @Transaction
    suspend fun toggleBookmarkStatus(recipeId: Long): Boolean {
        val recipe = getRecipeById(recipeId) ?: return false
        recipe.isBookmarked = !recipe.isBookmarked
        updateRecipe(recipe)
        return true // Success
    }

    @Update
    suspend fun updateRecipe(recipe: Recipe): Int

    @Query("SELECT * FROM recipes WHERE isBookmarked = 1")
    suspend fun getBookmarkedRecipes(): List<Recipe>
}