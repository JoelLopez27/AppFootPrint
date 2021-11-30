package com.example.appfootprint.ui.news

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import androidx.lifecycle.*
import com.example.appfootprint.NewsApplication
import com.example.appfootprint.models.Article
import com.example.appfootprint.models.NewsResponse
import com.example.appfootprint.repository.NewsRepository
import com.example.appfootprint.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class BreakingNewsViewModel(
    app: Application,
    val newsRepository : NewsRepository
) : AndroidViewModel(app) {

        val breakingNews : MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
        var breakingNewsPage = 1
        var breakingNewNumber = 60
        var breakingNewsResponse: NewsResponse? = null

            init {
                    getBreakingNews("calentamiento+global", "es",
                        "publishedAt")
            }

        fun getBreakingNews(selectedTopic: String, selectedLanguage: String, comeFisrt: String) =
                viewModelScope.launch {
           safeBreakingNewsCall(selectedTopic, selectedLanguage, comeFisrt)
        }

         private fun handleBreakingNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
            if(response.isSuccessful){
                response.body()?.let {resultResponse ->
                    breakingNewsPage++
                    if(breakingNewsResponse == null){
                        breakingNewsResponse = resultResponse
                    }else{
                          val oldArticles = breakingNewsResponse?.articles
                          val newArticles = resultResponse.articles
                          oldArticles?.addAll(newArticles)
                        }
                    return Resource.Success(breakingNewsResponse ?: resultResponse)
                    }
                }
                return Resource.Error(response.message())
            }

        fun saveArticle(article: Article) = viewModelScope.launch {
            newsRepository.upsert(article)
        }

        fun getSavedNews() = newsRepository.getSavedNews()

        fun deleteArticle(article: Article) = viewModelScope.launch {
            newsRepository.deleteArticle(article)
        }

        fun getSizeRows(): LiveData<Int> = newsRepository.getSizeRows()


        private suspend fun safeBreakingNewsCall(selectedTopic: String,
                                             selectedLanguage: String, comeFisrt: String){
        breakingNews.postValue(Resource.Loading())
        try {
            if(siConexionInternet()){
                val response = newsRepository.getBreakingNews(selectedTopic, selectedLanguage, comeFisrt,
                    breakingNewsPage, breakingNewNumber)
                breakingNews.postValue(handleBreakingNewsResponse(response))
            }else{
                breakingNews.postValue(Resource.Error("No hay Conexión a Internet"))
            }
        }catch (t: Throwable) {
            when(t) {
                is IOException -> breakingNews.postValue(Resource.Error("Internet Fallando"))
                else -> breakingNews.postValue(Resource.Error("Error al Convertir"))
                }
            }
        }

        private fun siConexionInternet(): Boolean{
                val connectivityManager = getApplication<NewsApplication>().getSystemService(
                    Context.CONNECTIVITY_SERVICE
                ) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                val activeNetwork = connectivityManager.activeNetwork ?: return false
                val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
                return when{
                        capabilities.hasTransport(TRANSPORT_WIFI) -> true
                        capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                        capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                    else -> false
                }
                }else{
                    connectivityManager.activeNetworkInfo?.run {
                        return when(type){
                            TYPE_WIFI -> true
                            TYPE_MOBILE -> true
                            TYPE_ETHERNET -> true
                            else -> false
                                }
                            }
                        }
                        return false
                    }
                }