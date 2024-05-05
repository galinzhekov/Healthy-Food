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
            openBrowser()
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
                angelovaRepository.insertCategory(Category(category = "Всички"))
                angelovaRepository.insertCategory(Category(category = "Най-нови"))
                angelovaRepository.insertCategory(Category(category = "Най-популярни"))
                angelovaRepository.insertCategory(Category(category = "Веган"))
            }
            if (angelovaRepository.getAllRecipes()?.isEmpty() == true) {
                angelovaRepository.insertRecipe(Recipe(name = "Спаначено руло", description = "Съставки:\n" +
                        "400 гр. свеж спанак\n" +
                        "4 яйца\n" +
                        "200 гр. крема сирене\n" +
                        "100 гр. сирене (фета или козе)\n" +
                        "1 с.л. брашно\n" +
                        "Сол, пипер и мускатов орех по вкус\n" +
                        "Приготвяне:\n" +
                        "Подготовка на спанака:\n" +
                        "Измийте спанака и го бланширайте за 1-2 минути във вряща вода. След това го изстискайте добре и нарежете на ситно.\n" +
                        "Приготвяне на основата:\n" +
                        "Разделете жълтъците от белтъците. Разбийте жълтъците с малко сол. Прибавете към тях брашното и нарязания на ситно спанак.\n" +
                        "В отделен съд разбийте белтъците до твърда пяна и внимателно ги добавете към спаначената смес.\n" +
                        "Печене:\n" +
                        "Изсипете сместа върху хартия за печене в тава и разстелете равномерно. Печете в предварително загрята фурна на 180°C за около 15-20 минути.\n" +
                        "Приготвяне на плънката:\n" +
                        "Разбъркайте крема сиренето с натрошеното сирене. Овкусете със сол, пипер и малко мускатов орех.\n" +
                        "Сглобяване на рулото:\n" +
                        "След като основата изстине, намажете я равномерно с плънката. Внимателно завийте на руло, като помагате с хартията за печене.", imageResourceId = R.drawable.spinach, categoriesList = arrayListOf(1, 2, 4), informationLabelsList = arrayListOf(Pair("10", "Въглехидрати"), Pair("20", "Белтъци"), Pair("30", "Мазнини"))))
                angelovaRepository.insertRecipe(Recipe(name = "Протеинов сладолед", description = "Съставки:\n" +
                        "\n" +
                        "400 гр. нискомаслена извара\n" +
                        "2 дози протеин на прах (по избор на вкус)\n" +
                        "250 гр. микс от ядки (като орехи, бадеми, кашу)\n" +
                        "200мл. бадемово мляко\n" +
                        "Инструкции за приготвяне:\n" +
                        "\n" +
                        "Подготовка на съставките:\n" +
                        "Ако ядките са големи, нарежете ги на по-малки парчета за по-лесно ядене в сладоледа.\n" +
                        "Смесване на съставките:\n" +
                        "В голяма купа смесете изварата с протеиновия прах. Добавете бадемово мляко по малко, докато сместа стане гладка и кремообразна.\n" +
                        "Прибавете нарязаните ядки към сместа и разбъркайте добре.\n" +
                        "Замразяване:\n" +
                        "Прехвърлете сместа в подходяща кутия за замразяване, която може да бъде затворена плътно.\n" +
                        "Поставете кутията във фризера за поне 2 часа, докато сместа стегне добре.", imageResourceId = R.drawable.icecream, categoriesList = arrayListOf(1, 2)))
                angelovaRepository.insertRecipe(Recipe(name = "Овесени йогурт хапки", description = "Необходими съставки:\n" +
                        "\n" +
                        "40 гр. гранола\n" +
                        "200 гр. гръцки стил йогурт\n" +
                        "150 гр. пресни плодове (като малини, боровинки и манго), нарязани ако са по-големи\n" +
                        "Инструкции за приготвяне:\n" +
                        "\n" +
                        "Добавяне на слоя с гранола:\n" +
                        "Разделете гранолата между отделенията на форма за лед, като запълните около 20-24 отделения. Може да се наложи да използвате 1-2 форми в зависимост от размера им.\n" +
                        "Добавяне на йогурт и плодове:\n" +
                        "С лъжица добавете гръцки йогурт върху гранолата, така че да покриете целия слой. Отгоре поставете няколко парченца пресни плодове.\n" +
                        "Замразяване:\n" +
                        "Замразете формите до твърдо състояние (1-2 часа) или докато се нуждаете от тях. Замразените хапки могат да се съхраняват до един месец във формите или да бъдат прехвърлени в херметически затворен съд.\n" +
                        "Сервиране:\n" +
                        "Оставете хапките няколко минути на стайна температура, за да омекнат леко, след което ги извадете от формите и сервирайте.", imageResourceId = R.drawable.yogurt, categoriesList = arrayListOf(1, 4)))
                angelovaRepository.insertRecipe(Recipe(name = "Протеинов кекс", description = "Съставки:\n" +
                        "20 гр. какао на прах\n" +
                        "250 гр. извара\n" +
                        "1 доза шоколадов протеин на прах\n" +
                        "1 банан\n" +
                        "1 яйце\n" +
                        "50 гр. микс от ядки (например орехи, бадеми, лешници)\n" +
                        "Приготвяне:\n" +
                        "Подготовка:\n" +
                        "Намажете форма за печене с малко масло или използвайте хартия за печене, за да предотвратите залепване.\n" +
                        "Приготвяне на сместа:\n" +
                        "В голяма купа намачкайте банана до гладкост.\n" +
                        "Добавете яйцето и разбъркайте добре.\n" +
                        "Прибавете изварата, протеиновият прах и какаото. Разбъркайте всичко до получаване на хомогенна смес.\n" +
                        "Накрая добавете нарязаните на ситно ядки и разбъркайте внимателно.\n" +
                        "Печене:\n" +
                        "Изсипете сместа в подготвената форма и загладете повърхността.\n" +
                        "Печете в предварително загрята фурна на 180°C за около 25-30 минути или докато кексът стане златист и тестерът излезе чист, когато го забодете в средата на кекса.", imageResourceId = R.drawable.proteincake, categoriesList = arrayListOf(1, 4)))
                angelovaRepository.insertRecipe(Recipe(name = "Пита със семена", description = "Съставки:\n" +
                        "\n" +
                        "3 яйца\n" +
                        "2 с.л. извара\n" +
                        "2 с.л. зехтин\n" +
                        "1 с.л. бакпулвер\n" +
                        "2 дози хуск\n" +
                        "1/2 ч.л. сол\n" +
                        "4 с.л. бадемово брашно\n" +
                        "Микс от семена по избор\n" +
                        "Инструкции:\n" +
                        "\n" +
                        "В голяма купа смесете яйцата, изварата, брашното, бакпулвера, солта, хуска и бадемовото брашно.\n" +
                        "Разбъркайте до получаването на еднородна смес.\n" +
                        "Добавете микса от семена и разбъркайте отново.\n" +
                        "Изсипете сместа в намаслена форма.\n" +
                        "Печете в предварително загрята фурна на 200 градуса за 20 минути или до златисто кафяво.\n" +
                        "Приятен апетит!", imageResourceId = R.drawable.bread, categoriesList = arrayListOf(1, 2)))
                angelovaRepository.insertRecipe(Recipe(name = "Смути Енергия", description = "Съставки:\n" +
                        "\n" +
                        "1 зряла праскова, обелена и нарязана на парчета\n" +
                        "1 чаша пресни спаначени листа\n" +
                        "1/2 зряло авокадо\n" +
                        "1/2 чаша пресни малини\n" +
                        "1/2 чаша вода или бадемово мляко (за по-кремообразна текстура)\n" +
                        "1 супена лъжица мед или агаве сироп за подслаждане (по желание)\n" +
                        "Ледени кубчета по желание\n" +
                        "Инструкции за приготвяне:\n" +
                        "\n" +
                        "Подготовка на съставките:\n" +
                        "Измийте добре спанака и малините. Обелете прасковата и я нарежете на парченца. Нарежете авокадото на половина, извадете костилката и извадете месестата част с лъжица.\n" +
                        "Смесване на съставките:\n" +
                        "В блендер добавете нарязаната праскова, спанака, месестата част на авокадото и малините.\n" +
                        "Добавете водата или бадемовото мляко за по-лека или кремообразна консистенция според вашите предпочитания.\n" +
                        "Ако желаете по-сладко смути, добавете меда или агаве сиропа.\n" +
                        "Блендиране:\n" +
                        "Блендирайте на висока скорост до получаване на гладка и хомогенна смес. Ако сместа е твърде гъста, може да добавите още малко течност.", imageResourceId = R.drawable.smoothie, categoriesList = arrayListOf(1, 3, 4), informationLabelsList = arrayListOf(Pair("10", "Въглехидрати"), Pair("20", "Белтъци"), Pair("30", "Мазнини"), Pair("10", "Минути"))))
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun openBrowser() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://fitness-supplements.onrender.com/todo"))
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "No app found to open the website", Toast.LENGTH_SHORT).show()
        }
    }
}