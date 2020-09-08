package com.maxdexter.myrecipe.ui.notelist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.maxdexter.myrecipe.R
import com.maxdexter.myrecipe.adapter.NoteAdapter
import com.maxdexter.myrecipe.adapter.NoteListener
import com.maxdexter.myrecipe.database.room.AppDatabase
import com.maxdexter.myrecipe.databinding.FragmentNoteListBinding
import com.maxdexter.myrecipe.repository.NoteRepository


class NoteListFragment : Fragment() {
    private lateinit var viewModel: NoteListViewModel
    private lateinit var viewModelFactory: NoteListViewModelFactory
    private lateinit var binding: FragmentNoteListBinding

    companion object {
        fun newInstance() = NoteListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_note_list, container, false)

        val database = context?.let { AppDatabase(it) }
        val repository = database?.noteDao()?.let { NoteRepository(it) }


        viewModelFactory = repository?.let { NoteListViewModelFactory(it,viewLifecycleOwner) }!!
        viewModel = ViewModelProvider(this,viewModelFactory).get(NoteListViewModel::class.java)
        binding.noteListViewModel = viewModel
        binding.lifecycleOwner = this


        initRecycler()

        viewModel.navigate.observe(viewLifecycleOwner, {add ->
            if (add) {
                findNavController().navigate(NoteListFragmentDirections.actionNoteListFragmentToDetailFragment(-1))
            }
        })

        return binding.root
    }

    private fun initRecycler() {
        val adapter = NoteAdapter(NoteListener { uuid -> this.findNavController().navigate(NoteListFragmentDirections.actionNoteListFragmentToDetailFragment(uuid)) })
        viewModel.notes.observe(viewLifecycleOwner, { adapter.submitList(it)   })
        val recyclerView = binding.recycler
        val layoutManager = GridLayoutManager(context, 2)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }




}