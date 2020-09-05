package com.maxdexter.myrecipe.ui.detail

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maxdexter.myrecipe.database.NoteDao
import com.maxdexter.myrecipe.model.Note
import com.maxdexter.myrecipe.repository.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailViewModel (noteId: Int, val repository: NoteRepository): ViewModel() {
    private var viewModelJob = Job() //когда viewModel будет уничтожена то в переопределенном методе onCleared() будут так же завершены все задания
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private lateinit var newNote: Note

    private var _note = MutableLiveData<Note>()
    val note: LiveData<Note>
        get() = _note
init {

    checkNoteId(noteId, repository)

}

   private fun checkNoteId(id: Int, repository: NoteRepository){
        if(id == -1) {
            newNote()
        } else {

            val note = repository.getNote(id).value
           _note.value = note
        }
    }

    private fun newNote() {
        newNote = Note(title = "new note")
       _note.value = newNote
    }

    fun addNote(note: Note) {
        uiScope.launch{repository.insert(note)}

    }

    override fun onCleared() {
        super.onCleared()
        addNote(newNote)
    }




}