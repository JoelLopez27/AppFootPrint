package com.example.appfootprint.repository

import com.example.appfootprint.api.RetrofitInstance

class ResultRepository {
    suspend fun getResultCar(distanceKm:String,typeVehicle:String) = RetrofitInstance.apiCarbono.getResultCar(distanceKm, typeVehicle)
}