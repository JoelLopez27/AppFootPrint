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

            val cantCO2 = response.carbonEquivalent.toDouble()
            if(cantCO2 == null){
                mBinding.progressBar.visibility = View.VISIBLE
            }else{
                mBinding.progressBar.visibility = View.GONE
                mBinding.cantKilograms.text = "%.2f".format(cantCO2) + " Kg"
            }
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