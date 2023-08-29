package com.example.zakamal.model.monitoring

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MonitoringResponse {
    @SerializedName("id_mon_zakat")
    val idMonZakat: Int? = null

    @SerializedName("id_provinsi")
    val idProvinsi: Int? = null

    @SerializedName("bulan")
    val bulan: String? = null

    @SerializedName("tahun")
    val tahun: Int? = null

    @SerializedName("total_terkumpul")
    val totalTerkumpul: Int? = null

    @SerializedName("total_pengeluaran")
    val totalPengeluaran: Int? = null

    @SerializedName("total_keseluruhan")
    val totalKeseluruhan: Int? = null

    @SerializedName("nama_provinsi")
    val namaProvinsi: String? = null
}