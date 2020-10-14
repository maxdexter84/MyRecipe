package com.maxdexter.myrecipe.ui



import android.app.Activity
import android.content.Context
import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.util.Log
import android.view.View

import androidx.appcompat.app.AppCompatActivity

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.common.internal.Asserts
import com.google.gson.JsonArray
import com.google.gson.JsonParser

import com.maxdexter.myrecipe.R
import com.maxdexter.myrecipe.database.room.AppDatabase

import com.maxdexter.myrecipe.databinding.ActivityMainBinding
import com.maxdexter.myrecipe.model.Recipe
import com.maxdexter.myrecipe.repository.NoteRepository
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import org.json.JSONObject
import org.koin.android.ext.android.get
import java.io.File
import java.io.FileNotFoundException


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel


    val job = Job()
    val coroutineScope = CoroutineScope(job + Dispatchers.Default)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.findNavController()
        binding.bottomNavigationView.setupWithNavController(navController)





//        coroutineScope.launch {
//            ioLoad()
//        }



    }

    private suspend fun ioLoad() {
        try {
            val res = assets
            val input = res.open("convertjson.json")
            var json = JsonParser.parseReader(input.reader()).asJsonArray

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
                    var jsonObject = item.asJsonObject
                    var recipeTitle = jsonObject.get("Recept").asString
                    var recipeContent = jsonObject.get("Content").asString
                    var descript = jsonObject.get("ReceptDescription").asString
                    var delishType = jsonObject.get("DishType").asString
                    var cuisine = jsonObject.get("Cuisine").asString
                    var recipe = Recipe(recipe = recipeTitle,
                        content = recipeContent,
                        recipeDescription = descript,
                        dishType = delishType,
                        cuisine = cuisine)
                    noteRepository.insert(recipe)
                    Log.d("THREAD", " ${recipe.id}")
                }

        }
    }





}