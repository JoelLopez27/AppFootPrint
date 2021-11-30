package com.example.appfootprint.ui.footprint.calfootprint

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.appfootprint.MainActivity
import com.example.appfootprint.R
import com.example.appfootprint.databinding.FragmentResultTreesBinding
import com.example.appfootprint.databinding.FragmentTreesEquivalentBinding

class FragmentResultTrees : Fragment() {

    private lateinit var mBinding: FragmentResultTreesBinding
    private lateinit var viewModel: ResultViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentResultTreesBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel3

        viewModel.resultLiveData.observe(viewLifecycleOwner){response->
            mBinding.cantArboles.text = response.numberOfTrees.toString() + " arboles"
        }

        mBinding.returnMenu.setOnClickListener {
            it.findNavController().navigate(
                R.id.action_fragmentResultTrees_to_nav_footprint)
        }
        mBinding.returnOptions.setOnClickListener {
            this.findNavController().popBackStack()
        }
    }
}