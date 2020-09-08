package com.maxdexter.myrecipe.ui.detail

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.maxdexter.myrecipe.repository.NoteRepository

class DetailViewModelFactory(val id: Int, val repository: NoteRepository, val owner: LifecycleOwner) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(id, repository, owner) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}