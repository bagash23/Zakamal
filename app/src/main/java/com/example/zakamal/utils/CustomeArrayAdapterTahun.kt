package com.example.zakamal.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.zakamal.R
import com.example.zakamal.dummy.DummyTahun

class CustomeArrayAdapterTahun(context: Context, private val resource: Int, private val tahunList: List<DummyTahun>, private val selectedTahun: String?):
    ArrayAdapter<DummyTahun>(context, resource, tahunList) {

    private val blueColor = ContextCompat.getColor(context, R.color.Primary_Blue_80)
    private val blackColor = ContextCompat.getColor(context, R.color.black)



    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)

        val tahun = tahunList[position]
        val tahunTextView: TextView = view.findViewById(R.id.tahunTextView)
        tahunTextView.text = tahun.name

        tahunTextView.setTextColor(blackColor)


        if (tahun.name == selectedTahun) {
            tahunTextView.setTextColor(blueColor)
        }

        return view
    }
}