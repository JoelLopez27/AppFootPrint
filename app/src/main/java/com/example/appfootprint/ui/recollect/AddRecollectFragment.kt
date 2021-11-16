package com.example.appfootprint.ui.recollect

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.appfootprint.MainActivity
import com.example.appfootprint.R
import com.example.appfootprint.databinding.FragmentAddRecollectBinding
import com.example.appfootprint.db.UserRecollect
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_add_recollect.*


class AddRecollectFragment : Fragment() {

    private lateinit var mBinding: FragmentAddRecollectBinding
    lateinit var viewModel: AddRecollectViewModel
    private var actionUpdate: Boolean = false
    private var material: String = "Papel"

    companion object{
        private var id : Int = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentAddRecollectBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel2

        bindAllViews()

      /* ***************************************************************************************** */

        botonDate.setOnClickListener {
            selectDate()
        }

        boton.setOnClickListener {
            if (mBinding.etCantMaterial.text.isNullOrEmpty() || spinner.selectedItem.equals("-Seleccionar Tipo de Material-")
                || mBinding.tvFecha.text.isEmpty()) {
                addDataMissign()
               } else {
                      Snackbar.make(view, "Recolección Guardada", Snackbar.LENGTH_SHORT).show()
                         addRecollectData()
                            it.findNavController().navigate(
                              R.id.action_addRecollectFragment_to_nav_recollect)
            }
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(arg0: AdapterView<*>, arg1: View?, arg2: Int, arg3: Long) {
                val selectedItem = spinner.selectedItem.toString()
                material = selectedItem
            }
            override fun onNothingSelected(arg0: AdapterView<*>) {
            }
        }
    }

    private fun addDataMissign() {
        AlertDialog.Builder(requireContext())
            .setMessage("Ingresar Datos Faltantes")
            .setPositiveButton("OK", (DialogInterface.OnClickListener { _, _ -> }))
            .show()
    }

    private fun addRecollectData() {
        val cantMaterial = etCantMaterial.text.toString()
        val cantMaterial2 = mBinding.etCantMaterial.text.toString()
        val materialCant = cantMaterial2.toDouble()
        val userRecollect = UserRecollect(
            null,
            material,
            cantMaterial,
            tvFecha.text.toString(),
            viewModel.calculateRecollect(cantMaterial, material)
        )
       viewModel.insertRecollectData(userRecollect)
    }

    private fun bindAllViews() {
        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(requireContext(),
            R.array.type_Material,
            android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        etCantMaterial
        tvFecha.text
    }

    private fun selectDate() {
        mBinding.apply {
                // Crea Nueva Instancia de DatePickerFragment
                val datePickerFragment = DatePickerFragment()
                val supportFragmentManager = requireActivity().supportFragmentManager

                // Implementamos setFragmentResultListener
                supportFragmentManager.setFragmentResultListener(
                    "REQUEST_KEY",
                    viewLifecycleOwner
                ) { resultKey, bundle ->
                    if (resultKey == "REQUEST_KEY") {
                        val date = bundle.getString("SELECTED_DATE")
                        tvFecha.text = date
                        mBinding.tvselectFecha.visibility = View.VISIBLE
                    }
                }
                // show
                datePickerFragment.show(supportFragmentManager, "DatePickerFragment")
        }
    }

    private fun dataSubmitted() {
        Toast.makeText(requireContext(), "Guardado Exitosamente", Toast.LENGTH_LONG).show()
    }

    private fun dataSubmitFailed() {
        Toast.makeText(requireContext(), "No se Logro Guardar", Toast.LENGTH_LONG).show()
    }

}
