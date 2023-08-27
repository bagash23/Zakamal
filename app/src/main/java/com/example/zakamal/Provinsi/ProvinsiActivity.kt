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
import com.example.zakamal.dummy.DummyProvinsi
import com.example.zakamal.utils.CustomeArrayAdapterProvinsi

class ProvinsiActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private var selectedProvinsiName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_provinsi)

        sharedPreferences = getSharedPreferences("selectedProvinsi", Context.MODE_PRIVATE)
        selectedProvinsiName = sharedPreferences.getString("selectedProvinsi", null)

        val provinsiList = DummyProvinsi.createDummyList()

        val listView: ListView = findViewById(R.id.provinsiListView)
        val adapter =  CustomeArrayAdapterProvinsi(this, provinsiList, selectedProvinsiName)
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            val clickedProvinsiName = provinsiList[position].name

            Log.d("PROVINSI", clickedProvinsiName)

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