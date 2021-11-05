package com.example.appfootprint.ui.footprint.calfootprint

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appfootprint.repository.ResultRepository

class ViewModelProviderFactory(
    val resultRepository: ResultRepository
) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ResultViewModel(resultRepository) as T
    }
}