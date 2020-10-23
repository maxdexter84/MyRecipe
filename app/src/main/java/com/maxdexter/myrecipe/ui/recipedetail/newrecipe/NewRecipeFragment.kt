package com.maxdexter.myrecipe.ui.recipedetail.newrecipe

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.core.net.toUri

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.maxdexter.myrecipe.R
import com.maxdexter.myrecipe.adapter.IngredientAdapter
import com.maxdexter.myrecipe.databinding.ListItemIngredientBinding
import com.maxdexter.myrecipe.databinding.NewRecipeFragmentBinding
import com.maxdexter.myrecipe.model.Recipe
import com.maxdexter.myrecipe.ui.event.EventType
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

const val REQUEST_IMAGE_CAPTURE = 1
class NewRecipeFragment : Fragment() {

    private lateinit var binding: NewRecipeFragmentBinding
    private lateinit var bindingItem: ListItemIngredientBinding
    private lateinit var viewModel: NewRecipeViewModel
    private lateinit var viewModelFactory: NewRecipeViewModelFactory
    var count = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NewRecipeFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        bindingItem = DataBindingUtil.inflate(inflater,R.layout.list_item_ingredient,container, false)
        viewModelFactory = NewRecipeViewModelFactory(requireContext())
        viewModel = ViewModelProvider(this, viewModelFactory).get(NewRecipeViewModel::class.java)
        binding.viewModel = viewModel



        initRecycler()

        viewModel.eventType.observe(viewLifecycleOwner, {
            when(it) {
                EventType.SAVE -> viewModel.saveData(binding)
                EventType.NEW_PHOTO -> startActivityForResult(viewModel.dispatchTakePictureIntent(),
                    REQUEST_IMAGE_CAPTURE)
                else -> Log.i("EVENT" ,"Unknown event")
            }
        })
        return binding.root
    }

    private fun initRecycler() {
        val adapter = IngredientAdapter()
        binding.rvIngredients.layoutManager = LinearLayoutManager(context)
        binding.rvIngredients.adapter = adapter
        viewModel.ingredientList.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
        addIngredientBtn(adapter)
    }

    private fun addIngredientBtn(adapter: IngredientAdapter) {
        binding.btnAddIngredient.setOnClickListener {
            count++
            viewModel.addIngredient()
            adapter.notifyItemInserted(count)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) { //Получаем результат фото интента
        viewModel.setImage(requestCode, resultCode, binding)
    }
}
