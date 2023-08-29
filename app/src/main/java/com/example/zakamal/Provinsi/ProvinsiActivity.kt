package com.example.zakamal.Provinsi

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import com.example.zakamal.R
import com.example.zakamal.api.DomainApi
import com.example.zakamal.dummy.DummyProvinsi
import com.example.zakamal.model.monitoring.ProvinsiResponse
import com.example.zakamal.utils.CustomeArrayAdapterProvinsi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProvinsiActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private var selectedProvinsiName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_provinsi)

        sharedPreferences = getSharedPreferences("selectedProvinsi", Context.MODE_PRIVATE)
        selectedProvinsiName = sharedPreferences.getString("selectedProvinsi", null)

        val listView: ListView = findViewById(R.id.provinsiListView)

        val apiService = DomainApi.monitoringService
        val getProvinis: Call<List<ProvinsiResponse>> = apiService.getProvinsi()

        getProvinis.enqueue(object : Callback<List<ProvinsiResponse>> {
            override fun onResponse(
                call: Call<List<ProvinsiResponse>>,
                response: Response<List<ProvinsiResponse>>
            ) {
                if (response.isSuccessful) {
                    val provinsiList: List<ProvinsiResponse>? = response.body()
                    provinsiList?.let {
                        val adapter = CustomeArrayAdapterProvinsi(this@ProvinsiActivity, it, selectedProvinsiName)
                        listView.adapter = adapter

                        listView.setOnItemClickListener { parent, view, position, id ->
                            val clickedProvinsiName = provinsiList[position].nama_provinsi

                            if (clickedProvinsiName != null) {
                                Log.d("PROVINSI", clickedProvinsiName)
                            }

                            if (selectedProvinsiName != clickedProvinsiName) {
                                sharedPreferences.edit().putString("selectedProvinsi", clickedProvinsiName).apply()
                                selectedProvinsiName = clickedProvinsiName
                                adapter.notifyDataSetChanged()

                                val intent = Intent()
                                intent.putExtra("selectedProvinsi", clickedProvinsiName)
                                setResult(Activity.RESULT_OK, intent)
                                finish()
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<ProvinsiResponse>>, t: Throwable) {
                // Handle API call failure
                Log.e("API Call", "Error: ${t.message}")
            }
        })

    }
}