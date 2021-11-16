package com.example.appfootprint.ui.recollect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appfootprint.MainActivity
import com.example.appfootprint.R
import com.example.appfootprint.adapters.RecollectAdapter
import com.example.appfootprint.databinding.FragmentRecollectBinding
import com.example.appfootprint.db.UserRecollect
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_recollect.*

class RecollectFragment : Fragment() {

    lateinit var mUserRecollectList: List<UserRecollect>
    lateinit var recollectAdapter: RecollectAdapter
    lateinit var tempUserRecollect: UserRecollect
    private lateinit var viewModel: AddRecollectViewModel
    private lateinit var mBinding: FragmentRecollectBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        mBinding = FragmentRecollectBinding.inflate(inflater, container, false)
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel2

        setupRecyclerView()

       // bindAllView()

        mBinding.boton.setOnClickListener {
            it.findNavController().navigate(
                R.id.action_nav_recollect_to_addRecollectFragment)
        }

        viewModel.getSavedRecollect().observe(viewLifecycleOwner, Observer { recollects ->
            recollectAdapter.differ.submitList(recollects)

        })

       val snackbarCallback = object : Snackbar.Callback() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                viewModel.deleteRecollectionData(tempUserRecollect)
                recollectAdapter.notifyDataSetChanged()
                checkDataList()
            }

            override fun onShown(sb: Snackbar?) {}
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.absoluteAdapterPosition
                val userRecollect = recollectAdapter.differ.currentList[position]
                viewModel.deleteRecollectionData(userRecollect)
                Snackbar.make(view, "Recolección Eliminada", Snackbar.LENGTH_SHORT).apply {
                    setAction("Deshacer"){
                        viewModel.insertRecollectData(userRecollect)
                        checkDataList()
                    }
                    show()
                }
            }

        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(recyclerView)
        }
     }

    private fun setupRecyclerView(){
        recollectAdapter = RecollectAdapter()
        recyclerView.apply {
            adapter = recollectAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }


    fun checkDataList() {
        if (mUserRecollectList.size == 0)
            emptyViewText.visibility = View.VISIBLE
        else
            emptyViewText.visibility = View.GONE
    }


}