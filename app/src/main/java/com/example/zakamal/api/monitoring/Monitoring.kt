package com.example.zakamal.api.monitoring

import com.example.zakamal.model.monitoring.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

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

    @GET("v1/user/{id_user}/post_feed/status/all")
    fun getAllPosttFeedById(
        @Path("id_user")idUser: Int
    ): Call<List<AllProvinsiData>>

    @GET("v1/user/post_feed/{id_post_feed}")
    fun getByIDPost(
        @Path("id_post_feed") idPostFeed: Int
    ): Call<AllProvinsiData>

//    get komentar
    @GET("v1/user/{id_post_feed}/komentar")
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

    @POST("v1/user/{id_user}/post_feed/add")
    @Multipart
    fun addZakat(
        @Path("id_user") idUser: Int,
        @Part("id_provinsi") idProv: Int,
        @Part("judul_post") judulPost: RequestBody,
        @Part("biaya") biaya: RequestBody,
        @Part("alamat") alamat: RequestBody,
        @Part("keterangan") keterangan: RequestBody,
        @Part dokumen: MultipartBody.Part?
    ) : Call<AddZakatResponse>

//    data pending
    @GET("v1/admin/post_feed/status/{nomor}")
    fun getStatusPending(
        @Path("nomor") nomor: Int
    ) : Call<PostFeedAdminPendingResponse>


    @GET("v1/user/{id_user}/post_feed/status/{nomor}")
    fun getStatusUser(
        @Path("id_user") idUser: Int,
        @Path("nomor") nomor: Int
    ) : Call<PostFeedAdminPendingResponse>

    @PUT("v1/admin/post_feed/{id_post_feed}/status")
    fun updateStatus(
        @Path("id_post_feed")idPostFeed: Int,
        @Body statusRequest: StatusRequest
    ) : Call<StatusResponse>

}