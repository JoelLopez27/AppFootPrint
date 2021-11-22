package com.example.appfootprint.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.appfootprint.MainActivity
import com.example.appfootprint.R
import com.example.appfootprint.databinding.FragmentBreakingNewsBinding
import com.example.appfootprint.databinding.FragmentHomeBinding
import com.example.appfootprint.databinding.ItemRecollectHomeBinding
import com.example.appfootprint.ui.recollect.AddRecollectViewModel
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.database.SnapshotParser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class HomeFragment : Fragment() {

    private lateinit var mBinding: FragmentHomeBinding
    lateinit var viewModel: AddRecollectViewModel
    private lateinit var mFirebaseAdapter: FirebaseRecyclerAdapter<Recollect, RecollectHolder>
    private lateinit var mLayoutManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val query = FirebaseDatabase.getInstance().reference.child("recollects")

        val options =
        FirebaseRecyclerOptions.Builder<Recollect>().setQuery(query, SnapshotParser {
            val recollect = it.getValue(Recollect::class.java)
            recollect!!.id = it.key!!
            recollect
        }).build()


        mFirebaseAdapter = object : FirebaseRecyclerAdapter<Recollect, RecollectHolder>(options){

            private lateinit var mContext: Context
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecollectHolder {
                mContext = parent.context

                val view = LayoutInflater.from(mContext)
                        .inflate(R.layout.item_recollect_home, parent, false)
                return RecollectHolder(view)
            }

            override fun onBindViewHolder(holder: RecollectHolder, position: Int, model: Recollect) {
                val recollect = getItem(position)

                with(holder) {
                    setListener(recollect)

                    binding.tvNombre.text = recollect.name
                    binding.tvTitle.text = recollect.title
                    binding.tvFecha.text = recollect.date
                    binding.tvcantMaterial.text = String.format(
                        mContext.getString(R.string.cant_material_home),
                        recollect.cantMaterial
                    )
                    binding.tvTipoMaterial.text = recollect.typeMaterial
                    binding.CantCO2.text =
                        String.format(mContext.getString(R.string.cant_co2_home), recollect.cantC02)
                    binding.cdLike.text = recollect.likeList.keys.size.toString()
                    FirebaseAuth.getInstance().currentUser?.let {
                    binding.cdLike.isChecked =
                        recollect.likeList.containsKey(it.uid)
                    }
                    Glide.with(mContext)
                        .load(recollect.photoUrl)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .into(binding.imagPhoto)

                }
            }

            override fun onDataChanged() {
                super.onDataChanged()
                mBinding.progressBar.visibility = View.GONE
            }

            override fun onError(error: DatabaseError) {
                super.onError(error)
                Toast.makeText(mContext, "Ha Ocurrido Un Error", Toast.LENGTH_SHORT).show()
            }
        }

        mLayoutManager = LinearLayoutManager(context)

        mBinding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = mLayoutManager
            adapter = mFirebaseAdapter
        }

        mBinding.fab.setOnClickListener {
            it.findNavController()
                .navigate(R.id.action_nav_home_to_nav_recollect)
        }
    }

    override fun onStart() {
        super.onStart()
        mFirebaseAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        mFirebaseAdapter.stopListening()
    }

    private fun setLike(recollect: Recollect, checked: Boolean){
        val databaseReference = FirebaseDatabase.getInstance().reference.child("recollects")
        if (checked){
            databaseReference.child(recollect.id).child("likeList")
                .child(FirebaseAuth.getInstance().currentUser!!.uid).setValue(checked)
        }else{
            databaseReference.child(recollect.id).child("likeList")
                .child(FirebaseAuth.getInstance().currentUser!!.uid).setValue(null)
        }
    }

    inner class RecollectHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = ItemRecollectHomeBinding.bind(view)

        fun setListener(recollect: Recollect){

            binding.cdLike.setOnCheckedChangeListener { compoundButton, checked ->
                setLike(recollect, checked)
            }
        }
    }

}