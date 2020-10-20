package com.maxdexter.myrecipe.adapter

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.maxdexter.myrecipe.R
import com.maxdexter.myrecipe.model.Ingredient
import com.maxdexter.myrecipe.model.Recipe

@BindingAdapter("textTitle")
fun TextView.setTitle(recipe: Recipe) {
    text = recipe.recipe
}

@BindingAdapter("textDescription")
fun TextView.setDescription(recipe: Recipe) {
    text = recipe.recipeDescription
}



@BindingAdapter("dateTime")
fun TextView.setTime(recipe: Recipe){
    text = recipe.date
}


@BindingAdapter("imgUrl")
fun bindImage(imageView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        Glide.with(imageView.context)
            .load(imgUrl)
            .apply(
                RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_brocken_img))
            .into(imageView)
    }
}


@SuppressLint("SetTextI18n")
@BindingAdapter("textSplit")
fun splitText(textView: TextView, recipe: Recipe?) {
        textView.text = "${recipe?.dishType}, ${recipe?.cuisine}"
}

@BindingAdapter("listAdapter")
fun listViewAdapter(listView: ListView, recipe: Recipe?) {
    val list = recipe?.content?.split(",") as MutableList
    val adapter = ArrayAdapter(listView.context, android.R.layout.simple_spinner_item,list)
    listView.adapter = adapter
}

@BindingAdapter("setIngredient")
fun EditText.setIngredient(ingredient: Ingredient){
    this.setText(ingredient.ingredient)
}

