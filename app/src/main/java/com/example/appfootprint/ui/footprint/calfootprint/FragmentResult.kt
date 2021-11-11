package com.example.appfootprint.ui.footprint.calfootprint

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.appfootprint.MainActivity
import com.example.appfootprint.R
import com.example.appfootprint.databinding.FragmentFootprintBinding
import com.example.appfootprint.databinding.FragmentResultBinding

class FragmentResult : Fragment() {

    private lateinit var mBinding: FragmentResultBinding
    lateinit var viewModel: ResultViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        mBinding = FragmentResultBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel3

        viewModel.resultLiveData.observe(viewLifecycleOwner){response->
            mBinding.cantKilograms.text = response.carbonEquivalent.toString() + " Kg"
        }


        mBinding.returnMenu.setOnClickListener {
            it.findNavController().navigate(
                R.id.action_fragmentResult_to_nav_footprint)
        }
        mBinding.returnOptions.setOnClickListener {
            this.findNavController().popBackStack()
        }

    }

}