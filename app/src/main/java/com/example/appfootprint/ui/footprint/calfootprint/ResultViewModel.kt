package com.example.appfootprint.ui.footprint.calfootprint

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfootprint.models.carbono.ResultResponse
import com.example.appfootprint.repository.ResultRepository
import kotlinx.coroutines.launch

class ResultViewModel(
    val resultRepository: ResultRepository
) : ViewModel(){

    val resultLiveData = MutableLiveData<ResultResponse>()

    fun getResultCar(distanceKm:String,typeVehicle:String) = viewModelScope.launch {
        val response = resultRepository.getResultCar(distanceKm, typeVehicle)
        resultLiveData.postValue(response)
    }
}