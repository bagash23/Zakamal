package com.example.zakamal.model.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("id_user") val userId: Int,
    @SerializedName("id_pezakat") val pezakatId: Int,
    @SerializedName("nama_lengkap") val namaLengkap: String,
    @SerializedName("telepon") val telepon: Long,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("id_provinsi") val idProvinsi: Int,
    @SerializedName("id_role") val idRole: Int
)

