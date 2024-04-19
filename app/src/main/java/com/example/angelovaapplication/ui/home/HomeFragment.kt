package com.example.angelovaapplication.ui.home

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.angelovaapplication.adapters.HomeRecipesPagerAdapter
import com.example.angelovaapplication.databinding.FragmentHomeBinding
import com.example.angelovaapplication.listeners.OnItemListener
import com.example.angelovaapplication.model.Recipe
import com.example.angelovaapplication.persistence.AngelovaRepository
import com.example.angelovaapplication.ui.RecipeActivity
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment(), OnItemListener {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapter: HomeRecipesPagerAdapter
    private lateinit var angelovaRepository: AngelovaRepository
    private var recipes = arrayListOf<Recipe>()
    private val binding get() = _binding!!
    private var isScrolled = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        angelovaRepository = AngelovaRepository(requireContext())

        CoroutineScope(Dispatchers.IO).launch {
            angelovaRepository.getAllCategories().let { tabs ->
                CoroutineScope(Dispatchers.IO).launch {
                    tabs.forEach { category ->
                        val tab = binding.categoriesTabLayout.newTab()
                        tab.text = category.category
                        tab.id = category.id.toInt()
                        binding.categoriesTabLayout.addTab(tab)
                    }
                    binding.categoriesTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                        override fun onTabSelected(tab: TabLayout.Tab?) {
                            tab?.id?.let {
                                onCategorySelected(it)
                            }
                        }

                        override fun onTabUnselected(tab: TabLayout.Tab?) {}

                        override fun onTabReselected(tab: TabLayout.Tab?) {}

                    })
                }
            }



        }

        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recipesRecyclerView.layoutManager = layoutManager
        adapter = HomeRecipesPagerAdapter(recipes, this)
        binding.recipesRecyclerView.adapter = adapter

        binding.recipesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (isScrolled < 2) {
                    Toast.makeText(requireContext(), "Можете да скролвате", Toast.LENGTH_SHORT).show()
                    isScrolled += 1
                }
            }
        })

        binding.searchBar.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                Toast.makeText(requireContext(), "Списъкът се филтрира само по име на рецепта.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.searchBar.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val filteredList = recipes.filter { it.name.contains(s.toString(), ignoreCase = true) } as ArrayList
                    adapter.updateData(filteredList)
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

    }

    override fun onResume() {
        super.onResume()

        CoroutineScope(Dispatchers.IO).launch {
            angelovaRepository.getAllRecipes().let {
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

    private fun onCategorySelected(id: Int) {
        val filteredRecipes = recipes.filter { it.categoriesList.find { categoryId -> categoryId == id } != null } as ArrayList
        adapter.updateData(filteredRecipes)
    }
}