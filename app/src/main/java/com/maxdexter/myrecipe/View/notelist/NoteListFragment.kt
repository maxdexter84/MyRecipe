package com.maxdexter.myrecipe.View.notelist

import android.os.Bundle
import android.os.SystemClock
import android.text.format.DateUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.maxdexter.myrecipe.R
import com.maxdexter.myrecipe.adapter.NoteAdapter
import com.maxdexter.myrecipe.databinding.FragmentNoteListBinding
import com.maxdexter.myrecipe.model.Note


class NoteListFragment : Fragment() {
private lateinit var viewModel: NoteListViewModel
private lateinit var binding: FragmentNoteListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_note_list, container, false)
        viewModel = ViewModelProvider(this).get(NoteListViewModel::class.java)

        val list = listOf<Note>(Note(title = "My simple note",description = "Много разного текста", date = "",typeNote = "simple",imageURL = "https://image.freepik.com/free-vector/pieces-torn-white-lined-notebook-paper-dark-background_47586-35.jpg"),
                Note(title = "My simple note",description = "Много разного текста", date = "",typeNote = "simple",imageURL = "https://image.freepik.com/free-vector/pieces-torn-white-lined-notebook-paper-dark-background_47586-35.jpg"))
        val adapter = NoteAdapter()
        adapter.data = list
        val recyclerView = binding.recycler
        val layoutManager = GridLayoutManager(context,2)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter


        return binding.root
    }


}