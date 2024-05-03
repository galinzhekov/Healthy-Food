package com.example.angelovaapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.angelovaapplication.databinding.ActivityMainBinding
import com.example.angelovaapplication.model.Category
import com.example.angelovaapplication.model.Recipe
import com.example.angelovaapplication.persistence.AngelovaRepository
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            openEmailApp()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.nav_home, R.id.nav_bookmarks), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val angelovaRepository = AngelovaRepository(this)
        CoroutineScope(Dispatchers.IO).launch {
            if (angelovaRepository.getAllCategories()?.isEmpty() == true) {
                angelovaRepository.insertCategory(Category(category = "All"))
                angelovaRepository.insertCategory(Category(category = "Newest Foods"))
                angelovaRepository.insertCategory(Category(category = "Best Recipes"))
                angelovaRepository.insertCategory(Category(category = "Popular ingredients"))
            }
            if (angelovaRepository.getAllRecipes()?.isEmpty() == true) {
                angelovaRepository.insertRecipe(Recipe(name = "Pasta", description = "Delicious pasta dish", imageResourceId = R.drawable.cupcake, categoriesList = arrayListOf(1, 3, 4), informationLabelsList = arrayListOf(Pair("10", "Въглехидрати"), Pair("20", "Белтъци"), Pair("30", "Мазнини"), Pair("10", "Минути"))))
                angelovaRepository.insertRecipe(Recipe(name = "Salad", description = "Healthy salad recipe", imageResourceId = R.drawable.cupcake, categoriesList = arrayListOf(1, 2, 4), informationLabelsList = arrayListOf(Pair("10", "Въглехидрати"), Pair("20", "Белтъци"), Pair("30", "Мазнини"))))
                angelovaRepository.insertRecipe(Recipe(name = "Pizza", description = "Homemade pizza recipe", imageResourceId = R.drawable.cupcake, categoriesList = arrayListOf(1, 2)))
                angelovaRepository.insertRecipe(Recipe(name = "Soup", description = "Warm soup recipe", imageResourceId = R.drawable.cupcake, categoriesList = arrayListOf(1, 4)))
                angelovaRepository.insertRecipe(Recipe(name = "Cake", description = "Decadent cake recipe", imageResourceId = R.drawable.cupcake, categoriesList = arrayListOf(1, 4)))
                angelovaRepository.insertRecipe(Recipe(name = "Smoothie", description = "Refreshing smoothie recipe", imageResourceId = R.drawable.cupcake, categoriesList = arrayListOf(1, 2)))
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun openEmailApp() {
        val emailAddress = "eli.angelova@gmail.com"

        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$emailAddress")
        }

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "No email app found", Toast.LENGTH_SHORT).show()
        }
    }
}