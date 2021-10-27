package com.example.appfootprint.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appfootprint.repository.NewsRepository

class BreakingNewsViewModelProviderFactory(
    val newsRepository: NewsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BreakingNewsViewModel(newsRepository) as T
    }
}