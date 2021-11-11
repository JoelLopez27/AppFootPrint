package com.example.appfootprint.ui.footprint.calfootprint

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.appfootprint.MainActivity
import com.example.appfootprint.R
import com.example.appfootprint.databinding.FragmentFootprintCarTravelBinding
import com.example.appfootprint.databinding.FragmentFootprintFlightTravelBinding


class FootprintFlightTravelFragment : Fragment() {

    private lateinit var mBinding: FragmentFootprintFlightTravelBinding
    private lateinit var viewModel: ResultViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?):
            View? {
        mBinding = FragmentFootprintFlightTravelBinding.inflate(inflater, container, false)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel3

        val spinner = mBinding.spinner
        ArrayAdapter.createFromResource(
            requireContext(), R.array.type_planes, android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        mBinding.btnCalcular.setOnClickListener {
            if (!mBinding.etKm.text.toString().isEmpty()&&!spinner.selectedItem.equals("-Seleccionar Tipo de Vuelo-")) {
                viewModel.getResultFlight(
                    mBinding.etKm.text.toString(),
                    spinner.selectedItem.toString()
                )
                it.findNavController()
                    .navigate(R.id.action_footprintFlightTravelFragment_to_fragmentResult)

            } else {
                Toast.makeText(activity, "Ingresar Dato Faltante", Toast.LENGTH_SHORT).show()
            }
        }

    }
}