package com.example.appfootprint.ui.recollect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.appfootprint.databinding.FragmentRecollectBinding

class RecollectFragment : Fragment() {

    private lateinit var recollectViewModel: RecollectViewModel
    private lateinit var mBinding: FragmentRecollectBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        recollectViewModel = ViewModelProvider(this).get(RecollectViewModel::class.java)

        mBinding = FragmentRecollectBinding.inflate(inflater, container, false)
        return mBinding.root

    }

}