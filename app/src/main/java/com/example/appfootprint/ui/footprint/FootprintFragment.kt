package com.example.appfootprint.ui.footprint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.appfootprint.R
import com.example.appfootprint.databinding.FragmentBreakingNewsBinding
import com.example.appfootprint.databinding.FragmentFootprintBinding
import com.example.appfootprint.ui.footprint.calfootprint.FootprintCarTravelFragment

class FootprintFragment : Fragment() {

    private lateinit var mBinding: FragmentFootprintBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        mBinding = FragmentFootprintBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       mBinding.button.setOnClickListener {
            it.findNavController().navigate(
                R.id.action_nav_footprint_to_footprintCarTravelFragment)
        }

        mBinding.button2.setOnClickListener {
            it.findNavController().navigate(
                R.id.action_nav_footprint_to_footprintFlightTravelFragment)
        }
        mBinding.button3.setOnClickListener {
            it.findNavController().navigate(
                R.id.action_nav_footprint_to_footprintBikeTravelFragment)
        }
        mBinding.button4.setOnClickListener {
            it.findNavController().navigate(
                R.id.action_nav_footprint_to_footprintTransitTravelFragment)
        }

        mBinding.button5.setOnClickListener {
            it.findNavController().navigate(
                R.id.action_nav_footprint_to_footprintHydroElectricFragment)
        }

        mBinding.button6.setOnClickListener {
            it.findNavController().navigate(
                R.id.action_nav_footprint_to_treesEquivalentFragment)
        }



    }

}