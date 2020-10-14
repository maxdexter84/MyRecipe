package com.maxdexter.myrecipe.adapter

import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.maxdexter.myrecipe.R
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
        val imgUri = Uri.parse(imgUrl)
        Glide.with(imageView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_brocken_img))
            .into(imageView)
    }
}

