package com.example.zakamal.api.monitoring

import com.example.zakamal.model.monitoring.AllProvinsiData
import com.example.zakamal.model.monitoring.MonitoringGambarResponse
import com.example.zakamal.model.monitoring.MonitoringResponse
import com.example.zakamal.model.monitoring.ProvinsiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Monitoring {
    @GET("v1/admin/monitoring-zakat/provinsi/{nama_provinsi}/tahun/{tahun}")
    fun getMonitoring(
        @Path("nama_provinsi") namaProvinsi: String,
        @Path("tahun") tahun: String
    ): Call<List<MonitoringResponse>>
    @GET("v1/admin/provinsi")
    fun getProvinsi() : Call<List<ProvinsiResponse>>

    @GET("v1/user/post_feed/provinsi/{nama_provinsi}")
    fun getPostFedd(
        @Path("nama_provinsi") namaProvinsi: String
    ) : Call<List<AllProvinsiData>>

    @GET("v1/user/post_feed/{id_post_feed}/dokumen")
    fun getGambarByIdPost(
        @Path("id_post_feed") idPostFeed: Int
    )

}