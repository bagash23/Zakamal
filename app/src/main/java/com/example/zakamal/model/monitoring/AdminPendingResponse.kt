package com.example.zakamal.model.monitoring

import com.google.gson.annotations.SerializedName

data class PostFeedAdminPendingResponse(
    @SerializedName("totalData")
    val totalData: Int,
    @SerializedName("data")
    val data: List<AdminPendingResponse>
)

class AdminPendingResponse {

    @SerializedName("id_post_feed")
    val id_post_feed: Int ? = null
    @SerializedName("id_user")
    val id_user: Int  ? = null
    @SerializedName("id_provinsi")
    val id_provinsi: Int  ? = null
    @SerializedName("nama")
    val nama: String  ? = null
    @SerializedName("judul_post")
    val judul_post: String  ? = null
    @SerializedName("biaya")
    val biaya: String  ? = null
    @SerializedName("alamat")
    val alamat: String  ? = null
    @SerializedName("keterangan")
    val keterangan: String  ? = null
    @SerializedName("status")
    val status: String  ? = null
    @SerializedName("tanggal_post")
    val tanggal_post: String  ? = null
    @SerializedName("nama_lengkap")
    val nama_lengkap: String  ? = null
    @SerializedName("nama_provinsi")
    val nama_provinsi: String  ? = null
    @SerializedName("nama_role")
    val nama_role: String  ? = null

}