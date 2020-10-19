package com.maxdexter.myrecipe.ui
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import com.maxdexter.myrecipe.R
import com.maxdexter.myrecipe.databinding.ActivityMainBinding
import com.maxdexter.myrecipe.model.Rating
import com.maxdexter.myrecipe.model.Recipe
import com.maxdexter.myrecipe.repository.NoteRepository
import kotlinx.coroutines.*
import org.koin.android.ext.android.get
import java.io.FileNotFoundException


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel


    val job = Job()
    val coroutineScope = CoroutineScope(job + Dispatchers.IO)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.findNavController()
        binding.bottomNavigationView.setupWithNavController(navController)





        coroutineScope.launch {
            ioLoad()
        }



    }

    private suspend fun ioLoad() {
        try {
            val res = assets
            val input = res.open("convertjson.json")
            val json = JsonParser.parseReader(input.reader()).asJsonArray

            delay(500)
            input.close()
            coroutineScope.launch { insert(json) }
        }catch (e: FileNotFoundException) {
            Log.d("TAG", "${e.message}")
        }finally {

        }
    }

    private suspend fun insert(json: JsonArray){
            val noteRepository: NoteRepository = get()
            for (item in json ) {
                coroutineScope.launch {
                    val jsonObject = item.asJsonObject
                    val recipeTitle = jsonObject.get("Recept").asString
                    val recipeContent = jsonObject.get("Content").asString
                    val descript = jsonObject.get("ReceptDescription").asString
                    val delishType = jsonObject.get("DishType").asString
                    val cuisine = jsonObject.get("Cuisine").asString
                    val urlPic = jsonObject.get("Image").asString
                    val recipe = Recipe(recipe = recipeTitle,
                        content = recipeContent,
                        recipeDescription = descript,
                        dishType = delishType,
                        cuisine = cuisine,
                        picURL = urlPic
                    )
                    noteRepository.saveNoteInFireStore(recipe)
                    Log.d("THREAD", " ${recipe.id}")
                }

        }
    }





}