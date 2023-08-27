package com.example.zakamal.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zakamal.R
import com.example.zakamal.dummy.DummyProvinsi

class ProvinsiAdapter(private val provinsi: List<DummyProvinsi>) : RecyclerView.Adapter<ProvinsiAdapter.ProvinsiViewHandler>() {

    class ProvinsiViewHandler(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.txt_list_provinsi)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProvinsiAdapter.ProvinsiViewHandler {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rc_list_provinsi, parent, false)
        return ProvinsiViewHandler(itemView)
    }

    override fun onBindViewHolder(holder: ProvinsiAdapter.ProvinsiViewHandler, position: Int) {
        val province = provinsi[position]
        holder.textView.text = province.name
    }

    override fun getItemCount(): Int {
        return provinsi.size
    }
}