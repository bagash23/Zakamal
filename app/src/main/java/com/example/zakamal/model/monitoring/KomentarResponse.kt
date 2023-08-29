package com.example.zakamal.model.monitoring

import com.google.gson.annotations.SerializedName

class KomentarResponse {

    @SerializedName("id_komentar")
    val id_komentar : String ? = null

    @SerializedName("id_post_feed")
    val id_post_feed : String ? = null

    @SerializedName("id_user")
    val id_user : String ? = null

    @SerializedName("nama")
    val nama : String ? = null

    @SerializedName("komentar")
    val komentar : String ? = null

    @SerializedName("tanggal_komentar")
    val tanggal_komentar : String ? = null
}