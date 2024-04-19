package com.example.angelovaapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.example.angelovaapplication.R
import com.example.angelovaapplication.adapters.InformationLabelsAdapter
import com.example.angelovaapplication.databinding.ActivityRecipeBinding
import com.example.angelovaapplication.model.Recipe
import com.example.angelovaapplication.persistence.AngelovaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeActivity : AppCompatActivity() {

    private lateinit var angelovaRepository: AngelovaRepository
    private lateinit var binding: ActivityRecipeBinding
    private var recipe: Recipe? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        angelovaRepository = AngelovaRepository(this)
        recipe = intent.getParcelableExtra("recipe")
        println(recipe?.name)

        binding.informationLabelRecyclerView.layoutManager = GridLayoutManager(this, 3)

        recipe?.let {
            binding.imageImageView.setImageResource(it.imageResourceId)
            binding.descriptionTextView.text = it.description
            binding.informationLabelRecyclerView.adapter = InformationLabelsAdapter(it.informationLabelsList)
            binding.titleTextView.text = it.name
            if (it.isBookmarked)
                binding.bookmarkImageView.setImageResource(R.drawable.ic_saved_bookmark)
            else
                binding.bookmarkImageView.setImageResource(R.drawable.ic_bookmark_border)
        }

        binding.returnBackArrow.setOnClickListener {
            finish()
        }

        binding.bookmarkImageView.setOnClickListener {
            showConfirmationDialog()
        }
    }

    fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Потвърждение")
            .setMessage("Сигурни ли сте, че искате да промените отметката?")
            .setPositiveButton("Да") { dialog, which ->
                CoroutineScope(Dispatchers.IO).launch {
                    recipe?.let { recipe ->
                        angelovaRepository.updateRecipeBookmarkStatus(recipe.id).let {
                            if (it) {
                                angelovaRepository.getRecipeById(recipe.id)?.let { updatedRecipe ->
                                    CoroutineScope(Dispatchers.Main).launch {
                                        if (updatedRecipe.isBookmarked)
                                            binding.bookmarkImageView.setImageResource(R.drawable.ic_saved_bookmark)
                                        else
                                            binding.bookmarkImageView.setImageResource(R.drawable.ic_bookmark_border)
                                    }
                                }
                            }
                        }

                    }
                }
            }
            .setNegativeButton("Не") { _, _ ->

            }
            .show()
    }
}