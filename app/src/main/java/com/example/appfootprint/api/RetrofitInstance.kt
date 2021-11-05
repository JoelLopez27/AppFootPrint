package com.example.appfootprint.api

import com.example.appfootprint.util.Constants.Companion.BASE_URL
import com.example.appfootprint.util.Constants.Companion.BASE_URL_CARBONO
import okhttp3.OkHttpClient
import okhttp3.internal.addHeaderLenient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RetrofitInstance {

    companion object{

        //API de Noticias
        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val api by lazy {
            retrofit.create(NewsAPI::class.java)
        }

        //API sobre Carbono
            private val retrofitInstance by lazy {
                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                val client = OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build()
            Retrofit.Builder()
                .baseUrl(BASE_URL_CARBONO)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            }

        val apiCarbono by lazy {
            retrofitInstance.create(CarbonoAPI::class.java)
        }
    }
}