package com.maxdexter.myrecipe.ui.notelist

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.maxdexter.myrecipe.model.Note
import com.maxdexter.myrecipe.repository.NoteRepository
import kotlinx.coroutines.*

class NoteListViewModel(val repository: NoteRepository?) : ViewModel() {
    private var viewModelJob = Job() //когда viewModel будет уничтожена то в переопределенном методе onCleared() будут так же завершены все задания
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    val notes = repository?.notes


    private val _navigate = MutableLiveData<Boolean>()
            val navigate: LiveData<Boolean>
            get() = _navigate

    private val _action = MutableLiveData<Note?>()
            val action: LiveData<Note?>
            get() = _action

    init {
        _navigate.value = false
        _action.value = null
    }


    fun actionWithANote(note: Note) {
        _action.value = note
    }
    fun deleteNote(note: Note) = uiScope.launch { repository?.deleteNote(note) }

    private fun navigateToDetailFragment(addNote: Boolean) {
        _navigate.value = addNote
    }

    override fun onCleared() {
        super.onCleared()
        navigateToDetailFragment(false)
        viewModelJob.cancel()

    }

}