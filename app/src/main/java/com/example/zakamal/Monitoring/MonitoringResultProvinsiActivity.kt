package com.example.zakamal.Monitoring

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import com.example.zakamal.R
import com.example.zakamal.api.DomainApi
import com.example.zakamal.model.monitoring.AllProvinsiData
import com.example.zakamal.utils.MonitoringResultAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MonitoringResultProvinsiActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monitoring_result_provinsi)
        sharedPreferences = getSharedPreferences("selectedProvinsi", MODE_PRIVATE)

        val getProvinsi = sharedPreferences.getString("selectedProvinsi", null)
//        call by id
        findViewById<TextView>(R.id.text_prov_zakat).setText(getProvinsi)
        findViewById<TextView>(R.id.txt_provinsi).setText(getProvinsi)

        val listView: ListView = findViewById(R.id.provinsiListView)

//        call api
        val apiService = DomainApi.monitoringService
        val getPostFeedByProvinsi: Call<List<AllProvinsiData>> = apiService.getPostFedd(getProvinsi.toString())

        println("API Request URL: ${getPostFeedByProvinsi.request().url}")

        getPostFeedByProvinsi.enqueue(object : Callback<List<AllProvinsiData>> {
            override fun onResponse(
                call: Call<List<AllProvinsiData>>,
                response: Response<List<AllProvinsiData>>
            ) {
                if (response.isSuccessful) {
                    val postFeed = response.body()
                    println("Response successful. Data count: ${postFeed}")
                    postFeed?.let {
                        val adapter = MonitoringResultAdapter(this@MonitoringResultProvinsiActivity, it
                        )
                        println("adapter $adapter")
                        listView.adapter = adapter
                    }
                } else {
                    println("Response not successful. Code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<AllProvinsiData>>, t: Throwable) {
                println("Error ${t.message}")
            }
        })





    }
}