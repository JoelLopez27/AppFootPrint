package com.example.appfootprint.ui.footprint.calfootprint

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appfootprint.R
import com.example.appfootprint.databinding.FragmentFootprintBinding
import com.example.appfootprint.databinding.FragmentFootprintCarTravelBinding


class FootprintCarTravelFragment : Fragment() {

    private lateinit var mBinding: FragmentFootprintCarTravelBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        mBinding = FragmentFootprintCarTravelBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}