package com.example.zakamal.ui.AdminPostingan

import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.zakamal.R
import com.example.zakamal.api.DomainApi
import com.example.zakamal.databinding.ActivityAdminPostinganChangeStatusBinding
import com.example.zakamal.model.monitoring.*
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class AdminPostinganChangeStatusActivity : AppCompatActivity() {
    lateinit var binding: ActivityAdminPostinganChangeStatusBinding
    private lateinit var sharedPreferencesId: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminPostinganChangeStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)


        sharedPreferencesId = this.getSharedPreferences("pendingPostList", Context.MODE_PRIVATE)
        val pendingPostListJson = sharedPreferencesId.getString("pendingPostList", null)

        if (!pendingPostListJson.isNullOrEmpty()) {
            val gson = Gson()
            val pendingPostList: List<AdminPendingResponse> = gson.fromJson(pendingPostListJson, Array<AdminPendingResponse>::class.java).toList()
            val idPostFeed = pendingPostList.firstOrNull()?.id_post_feed ?: -1

            fetchDetail(idPostFeed)
            updateStatusTerima(idPostFeed)
            updateStatusTolak(idPostFeed)
            binding.tvStatusPostinganAdmin.text = idPostFeed.toString()
            binding.tvItemCommunityTitle.text = idPostFeed.toString()
        }

        println("data :  ${sharedPreferencesId}")


        binding.ivBackPostinganChangeStatusAdmin.setOnClickListener {
            onBackPressed()
        }
    }

    private fun updateStatusTerima(idPostFeed: Int) {
        val apiServices = DomainApi.monitoringService
        binding.button.setOnClickListener {
            val requestData = StatusRequest()
            requestData.status = 2

            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Mohon Tunggu...")
            progressDialog.show()

            val changeStatus: Call<StatusResponse> = apiServices.updateStatus(idPostFeed, requestData)
            changeStatus.enqueue(object : Callback<StatusResponse> {
                override fun onResponse(
                    call: Call<StatusResponse>,
                    response: Response<StatusResponse>
                ) {
                    if (response.isSuccessful) {
                        val message = response.body()
                        Toast.makeText(this@AdminPostinganChangeStatusActivity, "${message!!.message}", Toast.LENGTH_LONG).show()
                        progressDialog.dismiss()
                    } else {
                        println("ERROR CODE ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<StatusResponse>, t: Throwable) {
                    println("ERROR ${t.message}")
                }

            })

        }
    }

    private fun updateStatusTolak(idPostFeed: Int) {
        val apiServices = DomainApi.monitoringService
        binding.button3.setOnClickListener {
            val requestData = StatusRequest()
            requestData.status = 3

            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Mohon Tunggu...")
            progressDialog.show()

            val changeStatus: Call<StatusResponse> = apiServices.updateStatus(idPostFeed, requestData)
            changeStatus.enqueue(object : Callback<StatusResponse> {
                override fun onResponse(
                    call: Call<StatusResponse>,
                    response: Response<StatusResponse>
                ) {
                    if (response.isSuccessful) {
                        val message = response.body()
                        Toast.makeText(this@AdminPostinganChangeStatusActivity, "${message!!.message}", Toast.LENGTH_LONG).show()
                        progressDialog.dismiss()
                    } else {
                        println("ERROR CODE ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<StatusResponse>, t: Throwable) {
                    println("ERROR ${t.message}")
                }

            })

        }
    }

    private fun fetchDetail(id: Int) {
        val apiServices = DomainApi.monitoringService
        val getDetailById: Call<AllProvinsiData> = apiServices.getByIDPost(id)

        getDetailById.enqueue(object : Callback<AllProvinsiData> {
            override fun onResponse(
                call: Call<AllProvinsiData>,
                response: Response<AllProvinsiData>
            ) {
                if (response.isSuccessful) {
                    val dataObject = response.body()

                    println("data object $dataObject")

                    if (dataObject != null) {
                        binding.tvStatusPostinganAdmin.text = dataObject.status
                        binding.tvItemCommunityTitle.text = dataObject.judul_post
                        binding.tvItemCommunityNumber.text = dataObject.biaya.toString()

                        binding.editTextText6.text = Editable.Factory.getInstance().newEditable(dataObject.alamat?.toString() ?: "")
                        binding.editTextText7.text = Editable.Factory.getInstance().newEditable(dataObject.biaya?.toString() ?: "")
                        binding.editTextTextPassword.text = Editable.Factory.getInstance().newEditable(dataObject.telepon?.toString() ?: "")
                        binding.editTextText8.text = Editable.Factory.getInstance().newEditable(dataObject.keterangan?.toString() ?: "")

                        if (dataObject.status == "Diterima") {
                            binding.tvStatusPostinganAdmin.setTextColor(resources.getColor(R.color.Light_Green))
                        } else if (dataObject.status == "Ditolak") {
                            binding.tvStatusPostinganAdmin.setTextColor(resources.getColor(R.color.Primary_Red))
                        } else {
                            binding.tvStatusPostinganAdmin.setTextColor(resources.getColor(R.color.Primary_Orange))
                        }

                    } else {
                        println("No data received")
                    }
                } else {
                    println("RESPONSE CODE ${response.code()}")
                }
            }

            override fun onFailure(call: Call<AllProvinsiData>, t: Throwable) {
                println("ERROR ${t.message}")
            }

        })
    }


}