package com.example.appfootprint.ui.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfootprint.models.NewsResponse
import com.example.appfootprint.repository.NewsRepository
import com.example.appfootprint.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class BreakingNewsViewModel(
    val newsRepository : NewsRepository
) : ViewModel() {

    val breakingNews : MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1

    init {
            getBreakingNews("calentamiento+global", "es", "publishedAt")
    }

    fun getBreakingNews(selectedTopic: String, selectedLanguage: String, comeFisrt: String) =
        viewModelScope.launch {
            breakingNews.postValue(Resource.Loading())
            val response = newsRepository.getBreakingNews(selectedTopic, selectedLanguage, comeFisrt,
            breakingNewsPage)
            breakingNews.postValue(handleBreakingNewsResponse(response))
        }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if(response.isSuccessful){
            response.body()?.let {resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())

    }

}