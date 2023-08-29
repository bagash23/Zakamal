package com.example.zakamal.api.monitoring

import com.example.zakamal.model.monitoring.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
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
    @GET("v1/user/post_feed/all")
    fun getAllPosttFeed(): Call<List<AllProvinsiData>>

    @GET("v1/user/post_feed/{id_post_feed}")
    fun getByIDPost(
        @Path("id_post_feed") idPostFeed: Int
    ): Call<AllProvinsiData>

//    get komentar
    @GET("v1//user/{id_post_feed}/komentar")
    fun getKomentarByID(
    @Path("id_post_feed") idPostFeed: Int,
    ) : Call<List<KomentarResponse>>

//    add komentar
    @POST("v1/user/{id_user}/{id_post_feed}/komentar/add")
    fun addKomentar(
        @Path("id_user") idUser: Int,
        @Path("id_post_feed") idPostFeed: Int,
        @Body komenntarReq: KomentarRequest
    ) : Call<AddKomentarResponse>

}