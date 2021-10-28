package com.example.appfootprint.ui.footprint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.appfootprint.databinding.FragmentBreakingNewsBinding
import com.example.appfootprint.databinding.FragmentFootprintBinding

class FootprintFragment : Fragment() {

    private lateinit var footprintViewModel: FootprintViewModel
    private lateinit var mBinding: FragmentFootprintBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        mBinding = FragmentFootprintBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}