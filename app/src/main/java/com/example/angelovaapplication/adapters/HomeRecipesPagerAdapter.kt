package com.example.angelovaapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.example.angelovaapplication.R
import com.example.angelovaapplication.holders.HomeRecipesViewHolder
import com.example.angelovaapplication.listeners.OnItemListener
import com.example.angelovaapplication.model.Recipe
import kotlin.math.abs

class HomeRecipesPagerAdapter(recipes: ArrayList<Recipe>, onItemListener: OnItemListener) :
    RecyclerView.Adapter<HomeRecipesViewHolder>() {

    private var context: Context? = null
    private var recipes: ArrayList<Recipe>
    private var onItemListener: OnItemListener

    init {
        this.recipes = recipes
        this.onItemListener = onItemListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecipesViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.home_recipe_item_layout, parent, false)
        return HomeRecipesViewHolder(view, onItemListener)
    }

    override fun onBindViewHolder(holder: HomeRecipesViewHolder, position: Int) {
        val recipe: Recipe = recipes[position]
        holder.setTvTitle(recipe.name)
        holder.setIvBackground(recipe.imageResourceId)
        if (recipe.isBookmarked)
            holder.setBookmarkImage(R.drawable.ic_saved_bookmark)
        else
            holder.setBookmarkImage(R.drawable.ic_bookmark_border)

        holder.itemView.scaleX = 0.8f
        holder.itemView.scaleY = 0.8f
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    fun updateData(filteredRecipes: ArrayList<Recipe>) {
        recipes = filteredRecipes
        notifyDataSetChanged()
    }
}