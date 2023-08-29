package com.example.zakamal.api

import com.example.zakamal.api.monitoring.Monitoring
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DomainApi {

    private const val BASE_URL = "https://f2bd-180-252-119-212.ngrok-free.app/"
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val monitoringService: Monitoring = retrofit.create(Monitoring::class.java)
}