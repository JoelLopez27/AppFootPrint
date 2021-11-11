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

    fun getResultFlight(distanceKm:String,typePlane:String) = viewModelScope.launch {
        val response = resultRepository.getResultFlight(distanceKm, typePlane)
        resultLiveData.postValue(response)
    }

    fun getResultBike(distanceKm:String,typeBike:String) = viewModelScope.launch {
        val response = resultRepository.getResultBike(distanceKm, typeBike)
        resultLiveData.postValue(response)
    }

    fun getResultTransit(distanceKm:String,typeTransit:String) = viewModelScope.launch {
        val response = resultRepository.getResultTransit(distanceKm, typeTransit)
        resultLiveData.postValue(response)
    }

    fun getResultHydroElectric(consumoKwh:String,location:String) = viewModelScope.launch {
        val response = resultRepository.getResultHydroElectric(consumoKwh, location)
        resultLiveData.postValue(response)
    }

    fun getResultTrees(peso:String,unidad:String) = viewModelScope.launch {
        val response = resultRepository.getResultTrees(peso, unidad)
        resultLiveData.postValue(response)
    }

}