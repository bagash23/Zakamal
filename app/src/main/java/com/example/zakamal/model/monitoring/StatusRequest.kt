package com.example.zakamal.model.monitoring

import com.google.gson.annotations.SerializedName

class StatusRequest {
    @SerializedName("status")
    var status: Int ? = null
}