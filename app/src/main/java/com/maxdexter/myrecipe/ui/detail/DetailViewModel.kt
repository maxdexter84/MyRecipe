package com.maxdexter.myrecipe.ui.detail

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maxdexter.myrecipe.R
import com.maxdexter.myrecipe.model.Note
import com.maxdexter.myrecipe.repository.NoteRepository
import com.maxdexter.myrecipe.utils.Color
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

    private var _indexSpinner = MutableLiveData<Int>()
            val indexSpinner: LiveData<Int>
            get() = _indexSpinner


init {
    if (id == -1) {
        newNote = Note( )
        _note.value = newNote
    } else {
        getNote(id)
    }

}





    fun addTitle(title: String) {
        newNote.mTitle = title

    }
    fun addDescription( desc: String) {
        newNote.mDescription = desc
    }

    private fun getNote(id: Int){
        repository.getNote(id).observe(owner,{n ->
            newNote = n
            _note.value = newNote
            spinnerItemDefault(newNote)
        })

    }


    private fun addNote(note: Note) {
        uiScope.launch{repository.insert(note)}
    }

    fun updateNote(note: Note) {
        uiScope.launch{repository.updateNote(note)}
    }

    fun saveNote() {
        if (newNote.mTitle != "" || newNote.mDescription != "") {
            addNote(newNote)
        }
        _updateNote.value = true

    }
    fun itemColor(color: Color){
        newNote.noteColor  = when(color) {
            Color.WHITE -> R.color.color_white
            Color.VIOLET -> R.color.color_violet
            Color.YELLOW -> R.color.color_yello
            Color.RED -> R.color.color_red
            Color.PINK -> R.color.color_pink
            Color.GREEN -> R.color.color_green
            Color.BLUE -> R.color.color_blue
        }
    }

    fun spinnerItemDefault(note: Note){
        val color = when(note.noteColor) {
            R.color.color_white -> Color.WHITE
            R.color.color_violet -> Color.VIOLET
            R.color.color_yello -> Color.YELLOW
            R.color.color_red -> Color.RED
            R.color.color_pink -> Color.PINK
            R.color.color_green -> Color.GREEN
            else -> Color.BLUE
        }
        val arr = Color.values()
        _indexSpinner.value = arr.indexOf(color)
    }


    override fun onCleared() {
        super.onCleared()
        _note.value = null
        _updateNote.value = false

    }


}