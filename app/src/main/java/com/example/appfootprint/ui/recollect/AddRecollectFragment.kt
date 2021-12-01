package com.example.appfootprint.ui.recollect

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.appfootprint.MainActivity
import com.example.appfootprint.R
import com.example.appfootprint.databinding.FragmentAddRecollectBinding
import com.example.appfootprint.db.UserRecollect
import com.example.appfootprint.ui.home.Recollect
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_add_recollect.*


class AddRecollectFragment : Fragment() {

    private lateinit var mBinding: FragmentAddRecollectBinding
    private lateinit var mStorageReference: StorageReference
    private lateinit var mDatabaseReference: DatabaseReference
    lateinit var viewModel: AddRecollectViewModel
    private var mPhotoSelectedUri: Uri? = null
    private var PATH_RECOLLECT = "recollects"
    private var material: String = "Papel"

    companion object{
        private var id : Int = 0
    }

    private var galleryResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){

                mPhotoSelectedUri = it.data?.data
                mBinding.imgPhoto.setImageURI(mPhotoSelectedUri)
        }
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

        mBinding.btnSelect.setOnClickListener {
            openGallery()
        }

        botonDate.setOnClickListener {
            selectDate()
        }

             botonCalcular.setOnClickListener {
                    hideKeyboard()
                    if (mBinding.etCantMaterial.text.isNullOrEmpty() || spinner.selectedItem.equals(
                            "-Seleccionar Tipo de Material-") || mBinding.tvFecha.text.isEmpty()) {
                        addDataMissign()
                    } else {
                        if (mBinding.publicarFeed.isChecked) {

                            if (mBinding.etNombre.text.isNullOrEmpty() || mBinding.etTitulo.text.isNullOrEmpty()) {
                                addDataMissign()
                            } else {
                                Snackbar.make(
                                    view,
                                    "Recolección Guardada",
                                    Snackbar.LENGTH_SHORT
                                )
                                    .show()
                                addRecollectData()
                                postRecollect()
                            }
                        } else {
                            Snackbar.make(view, "Recolección Guardada", Snackbar.LENGTH_SHORT)
                                .show()
                            addRecollectData()
                            it.findNavController().popBackStack()
                        }
                    }
                }


                 mBinding.publicarFeed.setOnCheckedChangeListener { button, b ->

            if ( mBinding.publicarFeed.isChecked) {
                mBinding.feedContainer.visibility = View.VISIBLE
            }else{
                mBinding.feedContainer.visibility = View.GONE
            }

        }

        val connectedRef =  FirebaseDatabase.getInstance().getReference(".info/connected")

            connectedRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val connected = snapshot.getValue(Boolean::class.java) ?: false

                             if (!connected) {
                                 Snackbar.make(
                                     view,
                                     "No hay Conexión a Internet, Revise su Conexión",
                                     Snackbar.LENGTH_SHORT
                                 )
                                     .show()
                             }
                         }

                override fun onCancelled(error: DatabaseError) {
                    Log.w(TAG, "Listener Fue Cancelado")
                }
            })

            mStorageReference = FirebaseStorage.getInstance().reference
            mDatabaseReference = FirebaseDatabase.getInstance().reference.child(PATH_RECOLLECT)

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(arg0: AdapterView<*>, arg1: View?, arg2: Int, arg3: Long) {
                val selectedItem = spinner.selectedItem.toString()
                material = selectedItem
            }
            override fun onNothingSelected(arg0: AdapterView<*>) {
               }
            }
         }

        private fun openGallery() {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            galleryResult.launch(intent)
        }

        private fun postRecollect() {
            mBinding.progressBar.visibility = View.VISIBLE
            val key = mDatabaseReference.push().key!!
            val cantMaterial = etCantMaterial.text.toString()
            val storageReference = mStorageReference.child(PATH_RECOLLECT)
                            .child(FirebaseAuth.getInstance().currentUser!!.uid).child(key)
            if (mPhotoSelectedUri != null) {
                storageReference.putFile(mPhotoSelectedUri!!)
                    .addOnProgressListener {
                        val process = (100 * it.bytesTransferred/it.totalByteCount).toDouble()
                        mBinding.progressBar.progress = process.toInt()
                        mBinding.progressPorcentage.visibility = View.VISIBLE
                        mBinding.progressPorcentage.text = "$process%"
                    }
                    .addOnCompleteListener {
                        mBinding.progressBar.visibility = View.INVISIBLE
                    }
                    .addOnSuccessListener {
                        Snackbar.make(mBinding.root, "Recoleción Publicada",
                            Snackbar.LENGTH_SHORT).show()
                        it.storage.downloadUrl.addOnSuccessListener {
                            saveRecollect(key, it.toString(),
                                mBinding.etTitulo.text.toString().trim(),
                                mBinding.etNombre.text.toString().trim(),
                                mBinding.tvFecha.text.toString().trim(),
                                cantMaterial.toDouble(),
                                material,
                                viewModel.calculateRecollect(cantMaterial.toDouble(), material))
                            mBinding.feedContainer.visibility = View.GONE
                            this.findNavController().popBackStack()
                        }
                    }
                    .addOnFailureListener {
                        Snackbar.make(mBinding.root, "No se ha logrado Publicarlo, Intentelo más tarde",
                            Snackbar.LENGTH_SHORT).show()
                     }
                 }
             }

            private fun saveRecollect(key: String, url: String, title: String, name: String,
                                        date: String, cantMaterial: Double, typeMaterial:String,
                                        cantCo2:Double) {
                val recollect = Recollect(title = title, photoUrl = url, name = name, date = date,
                                            cantMaterial = cantMaterial, typeMaterial = typeMaterial,
                                            cantC02 = cantCo2)

                mDatabaseReference.child(key).setValue(recollect)
            }

            private fun addDataMissign() {
                AlertDialog.Builder(requireContext())
                    .setMessage("Ingresar Datos Faltantes")
                    .setPositiveButton("OK", (DialogInterface.OnClickListener { _, _ -> }))
                    .show()
            }

            private fun addRecollectData() {
                val cantMaterial = etCantMaterial.text.toString()
                val userRecollect = UserRecollect(
                    null,
                    material,
                    cantMaterial.toDouble(),
                    tvFecha.text.toString(),
                    viewModel.calculateRecollect(cantMaterial.toDouble(), material)
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

            fun Fragment.hideKeyboard() {
                view?.let { activity?.hideKeyboard(it) }
            }

            fun Context.hideKeyboard(view: View) {
                val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }