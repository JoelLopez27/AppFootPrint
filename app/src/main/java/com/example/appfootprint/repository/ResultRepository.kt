package com.example.appfootprint.repository

import com.example.appfootprint.api.RetrofitInstance
import retrofit2.http.Query

class ResultRepository {
    suspend fun getResultCar(distanceKm:String,typeVehicle:String) = RetrofitInstance.apiCarbono.getResultCar(distanceKm, typeVehicle)

    suspend fun getResultFlight(distanceKm:String,typePlane:String) = RetrofitInstance.apiCarbono.getResultFlight(distanceKm, typePlane)

    suspend fun getResultBike(distanceKm:String,typeBike:String) = RetrofitInstance.apiCarbono.getResultBike(distanceKm, typeBike)

    suspend fun getResultTransit(distanceKm:String,typeTransit:String) = RetrofitInstance.apiCarbono.getResultTransit(distanceKm, typeTransit)

    suspend fun getResultHydroElectric(consumoKwh: String,country:String) = RetrofitInstance.apiCarbono.getResultHydroElectric(consumoKwh, country)

    suspend fun getResultTrees(peso: String, unidad:String) = RetrofitInstance.apiCarbono.getResultTrees(peso, unidad)

}