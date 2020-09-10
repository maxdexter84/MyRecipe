package com.maxdexter.myrecipe.ui.settings


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.maxdexter.myrecipe.R
import com.maxdexter.myrecipe.database.room.AppDatabase
import com.maxdexter.myrecipe.databinding.DetailFragmentBinding
import com.maxdexter.myrecipe.databinding.SettingsFragmentBinding
import com.maxdexter.myrecipe.repository.NoteRepository

class SettingsFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private lateinit var viewModel: SettingsViewModel
    private lateinit var binding: SettingsFragmentBinding
    private lateinit var viewModelFactory: SettingsViewModelFactory
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.settings_fragment, container, false)
        val database = context?.let { AppDatabase(it) }
        val repository = database?.noteDao()?.let { NoteRepository(it) }
        viewModelFactory = SettingsViewModelFactory(repository, viewLifecycleOwner)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SettingsViewModel::class.java)

        binding.settingsViewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }



}