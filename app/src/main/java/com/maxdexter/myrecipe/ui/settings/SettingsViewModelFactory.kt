package com.maxdexter.myrecipe.ui.settings

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.maxdexter.myrecipe.repository.NoteRepository
import com.maxdexter.myrecipe.ui.notelist.NoteListViewModel

class SettingsViewModelFactory (private val repository: NoteRepository?, private val owner: LifecycleOwner) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(repository, owner) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}