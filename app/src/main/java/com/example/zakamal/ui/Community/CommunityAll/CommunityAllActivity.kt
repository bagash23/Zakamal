package com.example.zakamal.ui.Community.CommunityAll

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zakamal.R
import com.example.zakamal.api.DomainApi
import com.example.zakamal.databinding.ActivityCommunityAllBinding
import com.example.zakamal.model.CommunityItem
import com.example.zakamal.model.monitoring.AllProvinsiData
import com.example.zakamal.utils.AllKomunitasAdapter
import com.example.zakamal.utils.KomunitasHomeAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommunityAllActivity : AppCompatActivity() {
    lateinit var binding: ActivityCommunityAllBinding
    lateinit var communityAdapter: CommunityAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommunityAllBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listItem = findViewById<ListView>(R.id.komunitasHome)
        val apiServices = DomainApi.monitoringService
        val getAllKomunitas: Call<List<AllProvinsiData>> = apiServices.getAllPosttFeed()

        getAllKomunitas.enqueue(object : Callback<List<AllProvinsiData>> {
            override fun onResponse(
                call: Call<List<AllProvinsiData>>,
                response: Response<List<AllProvinsiData>>
            ) {
                if (response.isSuccessful) {
                    val komunitasAll = response.body()
                    listItem.adapter = AllKomunitasAdapter(this@CommunityAllActivity, komunitasAll!!)
                }
            }

            override fun onFailure(call: Call<List<AllProvinsiData>>, t: Throwable) {
                println("Error ${t.message}")
            }

        })


        binding.ivBackCommunityAll.setOnClickListener {
            onBackPressed()
        }

    }
}