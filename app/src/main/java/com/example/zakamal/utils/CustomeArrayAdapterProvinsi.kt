package com.example.zakamal.utils

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.zakamal.R
import com.example.zakamal.dummy.DummyProvinsi

class CustomeArrayAdapterProvinsi(
    context: Context,
    private val dataSource: List<DummyProvinsi>,
    private val selectedProvinsiName: String?) :
    ArrayAdapter<DummyProvinsi>(context, R.layout.rc_list_provinsi, dataSource){

    private val blueColor = ContextCompat.getColor(context, R.color.Primary_Blue_80)
    private val blackColor = ContextCompat.getColor(context, R.color.black)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.rc_list_provinsi, parent, false)

        val textView: TextView = view.findViewById(R.id.txt_list_provinsi)
        val provinsi = getItem(position)

        textView.text = provinsi?.name

        // Reset warna untuk semua item
        textView.setTextColor(blackColor)

        if (provinsi?.name == selectedProvinsiName) {
            textView.setTextColor(blueColor)
        }

        return view
    }
}