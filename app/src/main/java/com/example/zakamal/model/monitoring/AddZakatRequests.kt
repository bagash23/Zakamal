package com.example.zakamal.model.monitoring

import android.net.Uri
import com.google.gson.annotations.SerializedName
import java.sql.Blob

class AddZakatRequests {

    @SerializedName("id_provinsi")
    var id_provinsi : Int ? = null

    @SerializedName("judul_post")
    var judul_post : String ? = null

    @SerializedName("biaya")
    var biaya : String ? = null

    @SerializedName("alamat")
    var alamat : String ? = null

    @SerializedName("keterangan")
    var keterangan : String ? = null

    @SerializedName("dokumen")
    var dokumen: Uri ? = null

}