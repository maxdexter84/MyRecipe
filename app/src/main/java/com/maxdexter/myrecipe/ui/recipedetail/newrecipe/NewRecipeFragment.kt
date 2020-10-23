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

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.maxdexter.myrecipe.R
import com.maxdexter.myrecipe.adapter.IngredientAdapter
import com.maxdexter.myrecipe.databinding.ListItemIngredientBinding
import com.maxdexter.myrecipe.databinding.NewRecipeFragmentBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

const val REQUEST_IMAGE_CAPTURE = 1
class NewRecipeFragment : Fragment() {

    companion object {
        fun newInstance() = NewRecipeFragment()
    }
    private lateinit var currentPhotoPath: String
    private lateinit var binding: NewRecipeFragmentBinding
    private lateinit var bindingItem: ListItemIngredientBinding
    private lateinit var fileImage: File
    private lateinit var viewModel: NewRecipeViewModel
    var count = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NewRecipeFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        bindingItem = DataBindingUtil.inflate(inflater,R.layout.list_item_ingredient,container, false)
        viewModel = ViewModelProvider(this).get(NewRecipeViewModel::class.java)
        binding.viewModel = viewModel
        fileImage = createImageFile()

        initRecycler()

        binding.addRecipePhoto.setOnClickListener {
            dispatchTakePictureIntent()
        }
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
            Log.i("COUNT", "$count")
        }
    }

    private fun saveAndAddIngredient() {
//        val size = binding.rvIngredients.adapter?.itemCount
//        if (size != null)
//        for (i in 0..size){
//
//        }
//        val ingredientName = binding.rvIngredients.get(count).et_ingredient_name.text.toString()
//        val ingredientMeasure = binding.rvIngredients.get(count).elv_measure.selectedItem.toString()
//        val ingredientQuantity = binding.rvIngredients.get(count).et_quantity.text.toString()


    }
    private fun dispatchTakePictureIntent() { //Отправляем интент для получения фото
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val uriFile = fileImage.let { FileProvider.getUriForFile(requireContext(),"com.maxdexter.myrecipe.fileprovider",it) }
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriFile)
        val cameraActivity = requireActivity().packageManager.queryIntentActivities(takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY)
        for (activity in cameraActivity) {
            requireActivity().grantUriPermission(activity.activityInfo.packageName, uriFile, Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        }
        startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) { //Получаем результат фото интента
        if(requestCode == REQUEST_IMAGE_CAPTURE) {
            val uri = fileImage.let { FileProvider.getUriForFile(requireActivity(),"com.maxdexter.myrecipe.fileprovider", it) }
            Glide.with(this)
                .load(uri)
                .apply(RequestOptions().placeholder(R.drawable.loading_animation).error(R.drawable.ic_brocken_img))
                .into(binding.ivNewRecipePic)
        }
    }

    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_",".jpg",storageDir)
            .apply { currentPhotoPath = absolutePath }
    }

}
//File(Environment.DIRECTORY_PICTURES)