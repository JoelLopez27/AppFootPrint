package com.example.appfootprint.ui.toprecollect

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appfootprint.R
import com.example.appfootprint.databinding.FragmentTopRecollectsBinding
import com.example.appfootprint.databinding.ListRecollectsCo2Binding
import com.example.appfootprint.databinding.ListRecollectsTopBinding
import com.example.appfootprint.ui.home.Recollect
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.database.SnapshotParser
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class TopRecollectsFragment : Fragment() {

    private lateinit var mBinding: FragmentTopRecollectsBinding
    private lateinit var mFirebaseAdapter: FirebaseRecyclerAdapter<Recollect, RecollectTopHolder>
    private lateinit var mFirebaseAdapter2: FirebaseRecyclerAdapter<Recollect, RecollectTopCO2Holder>
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private lateinit var mLayoutManager2: RecyclerView.LayoutManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentTopRecollectsBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.fab.setOnClickListener {
            it.findNavController()
                .navigate(R.id.action_nav_topRecollects_to_nav_recollect)
        }

        val query = FirebaseDatabase.getInstance().reference.child("recollects").orderByChild("cantMaterial").limitToLast(5)
        val options = FirebaseRecyclerOptions.Builder<Recollect>().setQuery(query, SnapshotParser {
                val recollect = it.getValue(Recollect::class.java)
                recollect!!.id = it.key!!
                recollect
            }).build()

        mFirebaseAdapter = object : FirebaseRecyclerAdapter<Recollect, RecollectTopHolder>(options){

            private lateinit var mContext: Context
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecollectTopHolder {
                mContext = parent.context

                val view = LayoutInflater.from(mContext)
                    .inflate(R.layout.list_recollects_top, parent, false)
                return RecollectTopHolder(view)
            }

            override fun onBindViewHolder(holder: RecollectTopHolder, position: Int, model: Recollect) {

                val recollect = getItem(position)

                with(holder) {

                    binding.tvNombre.text = recollect.name
                    binding.tvTipoMaterial.text =  String.format(
                        mContext.getString(R.string.type_material_top),
                        recollect.typeMaterial)
                    binding.tvcantMaterial.text = recollect.cantMaterial.toString()
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

        val queryTop = FirebaseDatabase.getInstance().reference.child("recollects").orderByChild("cantC02").limitToLast(5)
        val optionsTop = FirebaseRecyclerOptions.Builder<Recollect>().setQuery(queryTop, SnapshotParser {
            val recollect = it.getValue(Recollect::class.java)
            recollect!!.id = it.key!!
            recollect
        }).build()


        mFirebaseAdapter2 = object : FirebaseRecyclerAdapter<Recollect, RecollectTopCO2Holder>(optionsTop) {

            private lateinit var mContext: Context
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecollectTopCO2Holder {
                mContext = parent.context

                val view = LayoutInflater.from(mContext)
                    .inflate(R.layout.list_recollects_co2, parent, false)
                return RecollectTopCO2Holder(view)
            }

            override fun onBindViewHolder(holder: RecollectTopCO2Holder, position: Int, model: Recollect) {

                val recollect = getItem(position)

                with(holder) {

                    binding.tvNombre.text = recollect.name
                    binding.tvTipoMaterial.text =  String.format(
                        mContext.getString(R.string.type_material_co2),
                        recollect.typeMaterial)
                    binding.tvKgCO2.text = recollect.cantC02.toString()
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
        mLayoutManager2 = LinearLayoutManager(context)


        mBinding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = mLayoutManager
            adapter = mFirebaseAdapter
        }

        mBinding.recyclerView2.apply {
            setHasFixedSize(true)
            //reverseOrder<>()
            layoutManager = mLayoutManager2
            adapter = mFirebaseAdapter2
        }


    }

    override fun onStart() {
        super.onStart()
        mFirebaseAdapter.startListening()
        mFirebaseAdapter2.startListening()
    }

    override fun onStop() {
        super.onStop()
        mFirebaseAdapter.stopListening()
        mFirebaseAdapter2.stopListening()
    }




    inner class RecollectTopHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = ListRecollectsTopBinding.bind(view)
    }

    inner class RecollectTopCO2Holder(view: View) : RecyclerView.ViewHolder(view){
        val binding = ListRecollectsCo2Binding.bind(view)
    }

}