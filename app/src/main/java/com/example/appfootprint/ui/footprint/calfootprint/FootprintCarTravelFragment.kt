package com.example.appfootprint.ui.footprint.calfootprint

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appfootprint.R
import android.widget.Toast
import android.widget.ArrayAdapter
import androidx.navigation.findNavController
import com.example.appfootprint.MainActivity
import kotlinx.android.synthetic.main.fragment_footprint_car_travel.*
import com.example.appfootprint.databinding.FragmentFootprintBinding
import com.example.appfootprint.databinding.FragmentFootprintCarTravelBinding
import com.google.android.material.navigation.NavigationBarView


class FootprintCarTravelFragment : Fragment() {

    private lateinit var mBinding: FragmentFootprintCarTravelBinding
    private lateinit var viewModel: ResultViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentFootprintCarTravelBinding.inflate(inflater, container, false)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel3

        val spinner = mBinding.spinner
        ArrayAdapter.createFromResource(
            requireContext(), R.array.type_cars, android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        mBinding.btnCalcular.setOnClickListener {
            if (!mBinding.etKm.text.toString().isEmpty()&&!spinner.selectedItem.equals("-Seleccionar Tipo de Carro-")) {
                    viewModel.getResultCar(
                        mBinding.etKm.text.toString(),
                        spinner.selectedItem.toString()
                    )
                    it.findNavController()
                        .navigate(R.id.action_footprintCarTravelFragment_to_fragmentResult)

            } else {
                Toast.makeText(activity, "Ingresar Dato Faltante", Toast.LENGTH_SHORT).show()
            }
        }

    }
}