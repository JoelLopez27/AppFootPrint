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

    @Headers("x-rapidapi-key: $API_KEY_CARBONO ", "x-rapidapi-host: $HOST ")
    @GET("CarbonFootprintFromFlight")
    suspend fun getResultFlight(
        @Query("distance") distanceKm: String,
        @Query("type") typePlane:String
    ):ResultResponse

    @Headers("x-rapidapi-key: $API_KEY_CARBONO ", "x-rapidapi-host: $HOST ")
    @GET("CarbonFootprintFromMotorBike")
    suspend fun getResultBike(
        @Query("distance") distanceKm: String,
        @Query("type") typeBike:String
    ):ResultResponse

    @Headers("x-rapidapi-key: $API_KEY_CARBONO ", "x-rapidapi-host: $HOST ")
    @GET("CarbonFootprintFromPublicTransit")
    suspend fun getResultTransit(
        @Query("distance") distanceKm: String,
        @Query("type") typeTransit:String
    ):ResultResponse

    @Headers("x-rapidapi-key: $API_KEY_CARBONO ", "x-rapidapi-host: $HOST ")
    @GET("TraditionalHydroToCarbonFootprint")
    suspend fun getResultHydroElectric(
        @Query("consumption") consumoKwh: String,
        @Query("location") country:String
    ):ResultResponse

    @Headers("x-rapidapi-key: $API_KEY_CARBONO ", "x-rapidapi-host: $HOST ")
    @GET("TreeEquivalent")
    suspend fun getResultTrees(
        @Query("weight") peso: String,
        @Query("unit") unidad:String
    ):ResultResponse

}