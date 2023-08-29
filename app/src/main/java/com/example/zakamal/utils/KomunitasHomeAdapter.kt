package com.example.zakamal.utils

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.zakamal.R
import com.example.zakamal.model.monitoring.AllProvinsiData
import com.example.zakamal.ui.Community.CommunityComment.CommunityCommentActivity
import java.text.DecimalFormat

class KomunitasHomeAdapter(context: Context, private val dataList: List<AllProvinsiData>) : ArrayAdapter<AllProvinsiData>(context, 0, dataList) {

    private val decimalFormat = DecimalFormat("#,##0.00")


    override fun getCount(): Int {
        return 1
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.rc_list_komunitas, parent, false)

        val textTItle: TextView = view.findViewById(R.id.txt_title_kom)
        val textHarga: TextView = view.findViewById(R.id.txt_harga_kom)
        val btnKomentar : LinearLayout = view.findViewById(R.id.btn_komentar)
        val feed_kom = getItem(position)


        val id_post = feed_kom!!.id_post_feed
        println("ID ${id_post}")

        val pemasukan = feed_kom?.biaya?.toDouble() ?: 0.0
        textTItle.text = feed_kom!!.judul_post
        textHarga.text = formatNominal(pemasukan)

        btnKomentar.setOnClickListener {
            val intent = Intent(context, CommunityCommentActivity::class.java)
            intent.putExtra("EXTRA_DATA_KOMUNITAS", feed_kom.id_post_feed.toString())
            context.startActivity(intent)
        }

        return view

    }

    private fun formatNominal(value: Double): String {
        return decimalFormat.format(value)
    }

}