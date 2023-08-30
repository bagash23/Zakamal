package com.example.zakamal.ui.AdminPostingan

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zakamal.api.DomainApi
import com.example.zakamal.databinding.ActivityAdminPostinganBinding
import com.example.zakamal.model.monitoring.PostFeedAdminPendingResponse
import com.example.zakamal.ui.AdminPostingan.AdminPostinganAdapter
import com.google.gson.Gson

class AdminPostinganActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminPostinganBinding
    private lateinit var recyclerViewAdapter: AdminPostinganAdapter
    private lateinit var sharedPreferencesId: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminPostinganBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val GET_EXTRA_STATUS_POSTINGAN = intent.getStringExtra("EXTRA_STATUS_POSTINGAN")!!.toInt()

        recyclerViewAdapter = AdminPostinganAdapter(mutableListOf()) // Initialize with an empty list


        sharedPreferencesId = this.getSharedPreferences("pendingPostList", Context.MODE_PRIVATE)

        binding.rvAdminPostingan.layoutManager = LinearLayoutManager(this)
        binding.rvAdminPostingan.adapter = recyclerViewAdapter

        binding.ivBackPostinganAdmin.setOnClickListener {
            onBackPressed()
        }
        fetchDataAndUpdateAdapter(GET_EXTRA_STATUS_POSTINGAN)
    }

    private fun fetchDataAndUpdateAdapter(nomor: Int) {
        val apiService = DomainApi.monitoringService // Assuming you have set up your Retrofit API interface

        // Make the API call
        val call = apiService.getStatusPending(nomor)
        call.enqueue(object : retrofit2.Callback<PostFeedAdminPendingResponse> {
            override fun onResponse(
                call: retrofit2.Call<PostFeedAdminPendingResponse>,
                response: retrofit2.Response<PostFeedAdminPendingResponse>
            ) {
                if (response.isSuccessful) {
                    val pendingPostList = response.body()?.data ?: emptyList()
                    recyclerViewAdapter.updateData(pendingPostList)
                    if (nomor == 1) {
                        recyclerViewAdapter.setOnItemClickListener { pendingPost ->
                            val intent = Intent(this@AdminPostinganActivity, AdminPostinganChangeStatusActivity::class.java)
                            val pendingPostListJson = Gson().toJson(response.body()?.data)
                            sharedPreferencesId.edit().putString("pendingPostList", pendingPostListJson).apply()
                            startActivity(intent)
                        }
                    }

                } else {
                    println("ERROR ${response.code()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<PostFeedAdminPendingResponse>, t: Throwable) {
                println("ERROR ${t.message}")
            }
        })
    }
}
