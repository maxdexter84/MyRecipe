package com.maxdexter.myrecipe.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DetailViewModelFactory(val noteId: Int) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(noteId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}