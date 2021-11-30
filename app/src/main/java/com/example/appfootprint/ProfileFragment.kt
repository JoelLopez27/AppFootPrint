package com.example.appfootprint

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.appfootprint.databinding.FragmentProfileBinding
import com.example.appfootprint.ui.recollect.AddRecollectViewModel
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    private lateinit var mBinding: FragmentProfileBinding
    private lateinit var viewModel: AddRecollectViewModel
    var totalMaterial : Double? = 0.00
    var totalKgCo2 : Double? = 0.0
    var fecha : String? = "Not Found"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentProfileBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel2

        mBinding.instagramButton.setOnClickListener{
            val webIntent: Intent = Uri.parse("https://www.instagram.com/eco_print_app/?hl=es").let { webpage ->
                Intent(Intent.ACTION_VIEW, webpage)
            }
            startActivity(webIntent)
        }

        mBinding.facebookButton.setOnClickListener {
            val webIntent: Intent = Uri.parse("https://www.facebook.com/groups/770302206438949").let { webpage ->
                Intent(Intent.ACTION_VIEW, webpage)
            }
            startActivity(webIntent)
        }

        mBinding.fab.setOnClickListener {
            it.findNavController()
                .navigate(R.id.action_nav_profile_to_nav_recollect)
        }

            mBinding.tvName.text = FirebaseAuth.getInstance().currentUser?.displayName
            mBinding.tvEmail.text = FirebaseAuth.getInstance().currentUser?.email
                   Glide.with(this)
                    .load(FirebaseAuth.getInstance().currentUser?.photoUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(mBinding.circleImageView)

            mBinding.btnLogOut.setOnClickListener { signOut() }


        viewModel.getCountMaterial().observe(viewLifecycleOwner, Observer {
            totalMaterial = it

            mBinding.tvcantMaterial.text = String.format(requireActivity()
                .getString(R.string.cant_material_profile), "%.2f".format(totalMaterial))
            })

        viewModel.getCountKgCo2().observe(viewLifecycleOwner, Observer {
            totalKgCo2 = it

            mBinding.tvKgdeCO2.text = String.format(requireActivity()
                .getString(R.string.cant_kgco2_profile), "%.2f".format(totalKgCo2))
            })

        viewModel.getDate().observe(viewLifecycleOwner, Observer {
            fecha = it
            mBinding.fecha.text =  String.format(requireActivity()
                .getString(R.string.fecha_profile), fecha)
            })


        mBinding.fabShow.setOnClickListener {

            if(estadisticasText.visibility == View.GONE){
                mBinding.estadisticasText.visibility = View.VISIBLE
                mBinding.tvcantMaterial.visibility = View.VISIBLE
                mBinding.tvKgdeCO2.visibility = View.VISIBLE
                mBinding.fecha.visibility = View.VISIBLE

            }else{
                estadisticasText.visibility = View.GONE
                mBinding.tvcantMaterial.visibility = View.GONE
                mBinding.tvKgdeCO2.visibility = View.GONE
                mBinding.fecha.visibility = View.GONE
            }
        }
    }

    private fun signOut() {
        context?.let {
            AuthUI.getInstance().signOut(it)
                .addOnCompleteListener {
                    Toast.makeText(context, "Hasta Pronto....", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }