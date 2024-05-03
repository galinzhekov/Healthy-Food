package com.example.angelovaapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.angelovaapplication.R
import com.example.angelovaapplication.listeners.OnItemListener
import com.example.angelovaapplication.model.Recipe

class BookmarkedRecipesAdapter(recipes: List<Recipe>, onItemListener: OnItemListener) :
    RecyclerView.Adapter<BookmarkedRecipesAdapter.RecipeViewHolder>() {

    private var recipes: List<Recipe>
    private var onItemListener: OnItemListener

    init {
        this.recipes = recipes
        this.onItemListener = onItemListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.bookmark_recipe_item_layout, parent, false)
        return RecipeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        // Bind data to views
        holder.bind(recipe)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val textView: TextView = itemView.findViewById(R.id.textView)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(recipe: Recipe) {
            // Set image and text data
            imageView.setImageResource(recipe.imageResourceId)
            textView.text = recipe.name
        }

        override fun onClick(v: View) {
            onItemListener.onItemClick(v, adapterPosition)
        }
    }

    fun updateData(filteredRecipes: ArrayList<Recipe>) {
        recipes = filteredRecipes
        notifyDataSetChanged()
    }
}