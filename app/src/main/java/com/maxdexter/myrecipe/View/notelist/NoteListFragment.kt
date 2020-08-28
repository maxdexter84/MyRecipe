package com.maxdexter.myrecipe.View.notelist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.maxdexter.myrecipe.R
import com.maxdexter.myrecipe.databinding.FragmentNoteListBinding


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

        return binding.root
    }


}