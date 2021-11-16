package com.example.appfootprint.ui.recollect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appfootprint.repository.RecollectRepository
import com.example.appfootprint.ui.news.BreakingNewsViewModel

class RecollectViewModelProviderFactory(
    val recollectRepository: RecollectRepository
    ): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddRecollectViewModel(recollectRepository) as T
    }
}