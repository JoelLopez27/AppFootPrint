package com.example.appfootprint.api

import com.example.appfootprint.models.carbono.ResultResponse
import com.example.appfootprint.util.Constants.Companion.API_KEY_CARBONO
import com.example.appfootprint.util.Constants.Companion.HOST
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CarbonoAPI {

    @Headers("x-rapidapi-key: $API_KEY_CARBONO ", "x-rapidapi-host: $HOST ")
    @GET("CarbonFootprintFromCarTravel")
    suspend fun getResultCar(
        @Query("distance") distanceKm:String,
        @Query("vehicle") typeVehicle:String
    ):ResultResponse
}