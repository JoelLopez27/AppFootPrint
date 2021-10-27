package com.example.appfootprint.ui.savednews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appfootprint.MainActivity
import com.example.appfootprint.databinding.FragmentSavedNewsBinding
import com.example.appfootprint.ui.news.BreakingNewsViewModel

class savedNewsFragment : Fragment() {

    private lateinit var mBinding: FragmentSavedNewsBinding
    lateinit var viewModel: BreakingNewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        mBinding = FragmentSavedNewsBinding.inflate(inflater, container, false)

        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

}