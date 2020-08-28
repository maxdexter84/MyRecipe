package com.maxdexter.myrecipe.View.bottomsheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.maxdexter.myrecipe.R
import com.maxdexter.myrecipe.databinding.FragmentBottomsheetBinding

class BottomsheetFragment : BottomSheetDialogFragment() {
    lateinit var binding: FragmentBottomsheetBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_bottomsheet,container,false)
        return binding.root
    }


}