package com.example.zakamal.model.monitoring

import com.google.gson.annotations.SerializedName

class AddZakatResponse {

    @SerializedName("message")
    var message : String ? = null

    @SerializedName("postId")
    var postId: Int ? = null
}