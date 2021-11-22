package com.example.appfootprint.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appfootprint.repository.RecollectRepository

class HomeViewModel(val recollectRepository: RecollectRepository) : ViewModel() {

    fun getSavedRecollect() = recollectRepository.getSavedRecollect()

}