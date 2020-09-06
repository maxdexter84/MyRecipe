package com.maxdexter.myrecipe.ui.detail

import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
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

class DetailViewModel (id: Int,private val repository: NoteRepository,val owner: LifecycleOwner): ViewModel() {
    private var viewModelJob = Job() //когда viewModel будет уничтожена то в переопределенном методе onCleared() будут так же завершены все задания
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private lateinit var newNote: Note

    private var _updateNote = MutableLiveData<Boolean>()
        val updateNote: LiveData<Boolean>
            get() = _updateNote


    private var _note = MutableLiveData<Note>()
    val note: LiveData<Note>
        get() = _note

    private var _idNote = MutableLiveData<Int>()
        val idNote: LiveData<Int>
            get() = _idNote




init {
    if (id == -1) {
        newNote = Note( )
        _note.value = newNote
    } else {
        getNote(id)
    }
}





    fun addTitle(title: String) {
        newNote.title = title

    }
    fun addDescription( desc: String) {
        newNote.description = desc
    }

    private fun getNote(id: Int){
        repository.getNote(id).observe(owner,{n ->
            newNote = n
            _note.value = newNote
        })

    }


    private fun addNote(note: Note) {
        uiScope.launch{repository.insert(note)}
    }

    fun updateNote(note: Note) {
        uiScope.launch{repository.updateNote(note)}
    }


    override fun onCleared() {
        super.onCleared()
        _note.value = null
        if (newNote.title != "" || newNote.description != "") {
            addNote(newNote)
        }


    }


}