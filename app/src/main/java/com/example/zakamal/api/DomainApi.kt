package com.example.zakamal.api


import com.example.zakamal.api.login.Login
import com.example.zakamal.api.monitoring.Monitoring
import com.example.zakamal.api.register.Register
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DomainApi {

    private const val BASE_URL = "https://b349-125-160-225-142.ngrok-free.app/"
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val monitoringService: Monitoring = retrofit.create(Monitoring::class.java)
    val registerService: Register = retrofit.create(Register::class.java)
    val loginService: Login = retrofit.create(Login::class.java)
}