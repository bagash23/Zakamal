package com.example.zakamal.api.register

import com.example.zakamal.model.monitoring.MonitoringResponse
import com.example.zakamal.model.register.RegisterResponse
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
data class RegisterRequestBody(
    @SerializedName("nama_lengkap") val namaLengkap: String,
    @SerializedName("telepon") val telepon: Long,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("id_provinsi") val idProvinsi: Int
)

interface Register {
    @POST("/v1/pezakat/register")
    fun postRegister(
        @Body body: RegisterRequestBody
    ): Call<RegisterResponse>
}

