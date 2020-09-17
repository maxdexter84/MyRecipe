package com.maxdexter.myrecipe.ui.notelist

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.maxdexter.myrecipe.R
import com.maxdexter.myrecipe.databinding.FragmentNoteListBinding
import com.maxdexter.myrecipe.model.Note
import com.maxdexter.myrecipe.repository.NoteRepository
import kotlinx.coroutines.*

class NoteListViewModel(
    val repository: NoteRepository?,
    val binding: FragmentNoteListBinding, val lifecycleOwner: LifecycleOwner) : ViewModel() {
    private var viewModelJob = Job() //когда viewModel будет уничтожена то в переопределенном методе onCleared() будут так же завершены все задания
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    val notes = repository?.notes
    private var currentUser: Boolean = false

    private val _navigate = MutableLiveData<Boolean>()
            val navigate: LiveData<Boolean>
            get() = _navigate
    

    init {
        _navigate.value = false
        repository?.getCurrentUser()?.observe(lifecycleOwner, Observer { if (it != null) currentUser = true })
    }


    fun actionWithANote(note: Note) {
        deleteItem(note)
    }
    fun deleteNote(note: Note) = uiScope.launch {
        repository?.deleteNote(note)

    }

    fun deleteNoteFromFireStore(note: Note) = uiScope.launch {
        repository?.deleteNoteFromFireStore(note)
    }

    private fun navigateToDetailFragment(addNote: Boolean) {
        _navigate.value = addNote
    }

    private fun deleteItem(note: Note) {
        if (currentUser){
            Snackbar.make(
                binding.root,
                R.string.snackbar_delete_note_title,
                Snackbar.LENGTH_LONG
            ).setAction("Yes") {
                note.let { it1 ->
                    deleteNoteFromFireStore(it1)
                    deleteNote(it1)
                }
            }.show()
        }else {Snackbar.make(binding.root, R.string.toast_login_about_delete, Snackbar.LENGTH_SHORT).show()}
    }

    override fun onCleared() {
        super.onCleared()
        navigateToDetailFragment(false)
        viewModelJob.cancel()

    }

}