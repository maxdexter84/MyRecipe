package com.maxdexter.myrecipe.ui.recipedetail.newrecipe

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Environment
import android.provider.MediaStore
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.core.view.size
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.common.base.Enums
import com.maxdexter.myrecipe.R
import com.maxdexter.myrecipe.databinding.NewRecipeFragmentBinding
import com.maxdexter.myrecipe.model.Ingredient
import com.maxdexter.myrecipe.ui.event.EventType
import kotlinx.android.synthetic.main.list_item_ingredient.view.*
import java.io.File
import java.text.SimpleDateFormat

import java.util.*

class NewRecipeViewModel(val context: Context) : ViewModel() {
    private var fileImage = createImageFile()
    private var ingredient: Ingredient = Ingredient()
    private val list = mutableListOf(ingredient)
    private val _eventType = MutableLiveData<EventType>()
            val eventType: LiveData<EventType>
            get() = _eventType

    private val _ingredientsList = MutableLiveData<List<Ingredient>>()
            val ingredientList: LiveData<List<Ingredient>>
            get() = _ingredientsList

    init {
        _eventType.value = EventType.NO_EVENT
        _ingredientsList.value = list
    }
     fun addIngredient() {
         ingredient = Ingredient()
         list.add(ingredient)
         _ingredientsList.value = list
     }

    fun deleteIngredient() {
        list.remove(ingredient)
        _ingredientsList.value = list
    }

    fun eventType(eventType: EventType) {
        _eventType.value = eventType
    }

    override fun onCleared() {
        super.onCleared()
        _eventType.value = EventType.NO_EVENTS
    }


    fun saveData(binding:NewRecipeFragmentBinding){
      //  val recipeImg = currentPhotoPath
        val recipeTitle = binding.etRecipeName.text.toString()
        val recipeDescription = binding.etRecipeDescription.text.toString()
        val list = mutableListOf<String>()

        for (indx in 0 until binding.rvIngredients.childCount) {
                binding.rvIngredients[indx].apply {
             val recipeContent = "${et_ingredient_name.text.toString()} ${et_quantity.text.toString()} ${elv_measure.selectedItem}"
                list.add(recipeContent)
            }

        }
    }

    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_",".jpg",storageDir)

    }

   fun dispatchTakePictureIntent(): Intent { //Отправляем интент для получения фото
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val uriFile = fileImage.let { FileProvider.getUriForFile(context,"com.maxdexter.myrecipe.fileprovider",it) }
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriFile)
        val cameraActivity = context.packageManager.queryIntentActivities(takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY)
        for (activity in cameraActivity) {
            context.grantUriPermission(activity.activityInfo.packageName, uriFile, Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        }
        return takePictureIntent
    }


    fun setImage(requestCode: Int, resultCode: Int, binding: NewRecipeFragmentBinding) {
        if(requestCode == REQUEST_IMAGE_CAPTURE) {
            val uri = fileImage.let { FileProvider.getUriForFile(context,"com.maxdexter.myrecipe.fileprovider", it) }
            Glide.with(context)
                .load(uri)
                .apply(RequestOptions().placeholder(R.drawable.loading_animation).error(R.drawable.ic_brocken_img))
                .into(binding.ivNewRecipePic)
        }
    }

}