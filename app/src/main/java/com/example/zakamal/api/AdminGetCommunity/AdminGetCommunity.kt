package com.example.zakamal.api.AdminGetCommunity

import com.example.zakamal.model.login.LoginResponse
import com.example.zakamal.model.monitoring.ProvinsiResponse
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AdminGetCommunity {
    @GET("v1/admin/post_feed/status/1")
    fun getProvinsi() : Call<List<AdminGetCommunityResponse>>
}

data class AdminGetCommunityResponse(
    @SerializedName("total") val total: String,
    @SerializedName("data") val data: List<AdminGetCommunityDetail>
)

data class AdminGetCommunityDetail(
    @SerializedName("id_post_feed") val idPostFeed: Int,
    @SerializedName("id_user") val idUser: Int,
    @SerializedName("idprovinsi") val idProvinsi: Int,
    @SerializedName("nama") val nama: String,
    @SerializedName("judul_post") val judulPost: String,
    @SerializedName("biaya") val biaya: Long,
    @SerializedName("alamat") val alamat: String,
    @SerializedName("keterangan") val keterangan: String,
    @SerializedName("status") val status: String,
    @SerializedName("tanggal_post") val tanggalPost: String,
    @SerializedName("nama_lengkap") val namaLengkap: String,
    @SerializedName("nama_provinsi") val namaProvinsi: String,
    @SerializedName("nama_role") val namaRole: String
)
