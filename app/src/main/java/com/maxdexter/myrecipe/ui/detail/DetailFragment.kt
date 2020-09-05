package com.maxdexter.myrecipe.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.maxdexter.myrecipe.R
import com.maxdexter.myrecipe.database.AppDatabase
import com.maxdexter.myrecipe.database.NoteDao
import com.maxdexter.myrecipe.databinding.DetailFragmentBinding
import com.maxdexter.myrecipe.repository.NoteRepository

class DetailFragment : Fragment() {

    companion object {
        fun newInstance() = DetailFragment()
    }

    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: DetailFragmentBinding
    private lateinit var viewModelFactory: DetailViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.detail_fragment,container, false)
        val database = context?.let { AppDatabase(it) }
        val repository = database?.noteDao()?.let { NoteRepository(it) }

        val args = arguments?.let { DetailFragmentArgs.fromBundle(it) }




        if (repository != null) {
            initViewModel(args, repository)
        }


        binding.detailViewModel = viewModel
        binding.setLifecycleOwner(this)


        return binding.root
    }

    private fun initViewModel(args: DetailFragmentArgs?, repository: NoteRepository) {
        if (args != null) {
            viewModelFactory = DetailViewModelFactory(args.noteId, repository)
        }
        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)
    }


}