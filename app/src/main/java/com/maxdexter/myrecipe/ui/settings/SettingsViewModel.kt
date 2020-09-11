package com.maxdexter.myrecipe.ui.settings


import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import com.maxdexter.myrecipe.model.Note
import com.maxdexter.myrecipe.model.NoteResult
import com.maxdexter.myrecipe.repository.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.security.acl.Owner

class SettingsViewModel(private val repository: NoteRepository?,private val owner: LifecycleOwner) : ViewModel() {
    private var viewModelJob = Job() //когда viewModel будет уничтожена то в переопределенном методе onCleared() будут так же завершены все задания
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private var notes = repository?.notes

    init {

    }

    fun onLoadToFireStore() {
        notes?.observe(owner, { list -> repository?.loadToFireStore(list) })

    }

    fun downloadFromFireStore(){
        var listOfNote = mutableListOf<Note>()
        repository?.synchronization()?.observe(owner, {list -> listOfNote = list})

        uiScope.launch {listOfNote.forEach{note ->  repository?.insert(note) }  }


    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}