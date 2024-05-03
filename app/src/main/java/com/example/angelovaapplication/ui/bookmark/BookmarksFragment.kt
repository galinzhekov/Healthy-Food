package com.example.angelovaapplication.ui.bookmark

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.angelovaapplication.adapters.BookmarkedRecipesAdapter
import com.example.angelovaapplication.databinding.FragmentBookmarksBinding
import com.example.angelovaapplication.listeners.OnItemListener
import com.example.angelovaapplication.model.Recipe
import com.example.angelovaapplication.persistence.AngelovaRepository
import com.example.angelovaapplication.ui.RecipeActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookmarksFragment : Fragment(), OnItemListener {

    private var _binding: FragmentBookmarksBinding? = null
    private val binding get() = _binding!!
    private lateinit var angelovaRepository: AngelovaRepository
    private var recipes = arrayListOf<Recipe>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBookmarksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        angelovaRepository = AngelovaRepository(requireContext())
        val layoutManager = LinearLayoutManager(requireContext())
        binding.bookmarkRecipesRecyclerView.layoutManager = layoutManager
        val adapter = BookmarkedRecipesAdapter(recipes, this)
        binding.bookmarkRecipesRecyclerView.adapter = adapter

        CoroutineScope(Dispatchers.IO).launch {
            angelovaRepository.getBookmarkedRecipes()?.let {
                CoroutineScope(Dispatchers.Main).launch {
                    recipes = it as ArrayList
                    adapter.updateData(recipes)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(v: View?, position: Int) {
        val intent = Intent(requireContext(), RecipeActivity::class.java).apply {
            putExtra("recipe", recipes[position])
        }
        startActivity(intent)
    }
}