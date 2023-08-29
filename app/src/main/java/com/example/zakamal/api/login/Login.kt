package com.example.zakamal.api.login

import com.example.zakamal.model.login.LoginResponse
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Login {
    @POST("/v1/user/login")
    fun postLogin(
        @Body body: LoginRequestBody
    ): Call<LoginResponse>
}

data class LoginRequestBody(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)
