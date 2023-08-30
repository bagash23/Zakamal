package com.example.zakamal.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.zakamal.R
import com.example.zakamal.model.monitoring.AllProvinsiData
import com.example.zakamal.model.monitoring.MonitoringGambarResponse
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class MonitoringResultAdapter(context: Context, private val dataList: List<AllProvinsiData>) : ArrayAdapter<AllProvinsiData>(context, 0, dataList) {


    private val decimalFormat = DecimalFormat("#,##0.00")

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.rc_list_card, parent, false)

//        call id
        val textStatus : TextView = view.findViewById(R.id.txt_status)
        val textJudul : TextView = view.findViewById(R.id.txt_judul)
        val textHarga : TextView = view.findViewById(R.id.txt_harga)


        val textGroup: TextView = view.findViewById(R.id.txt_drop_selengkap)

//        drop data
        val textAlamat: TextView = view.findViewById(R.id.txt_alamat)
        val textTelpone: TextView = view.findViewById(R.id.txt_telpone)
        val textKeterangan: TextView = view.findViewById(R.id.txt_keterangan)
        val textTanggal: TextView = view.findViewById(R.id.txt_tgl_post)
        val Gambar: TextView = view.findViewById(R.id.poster_provinsi)

        // Set initial visibility based on expanded state
        textAlamat.visibility = View.GONE
        textTelpone.visibility = View.GONE
        textKeterangan.visibility = View.GONE
        textTanggal.visibility = View.GONE

        // Toggle expanded state and visibility on textGroup click
        textGroup.setOnClickListener {
            if (textAlamat.visibility == View.GONE) {
                // Show additional information
                textAlamat.visibility = View.VISIBLE
                textTelpone.visibility = View.VISIBLE
                textKeterangan.visibility = View.VISIBLE
                textTanggal.visibility = View.VISIBLE
            } else {
                // Hide additional information
                textAlamat.visibility = View.GONE
                textTelpone.visibility = View.GONE
                textKeterangan.visibility = View.GONE
                textTanggal.visibility = View.GONE
            }
        }


//        render data
        val feed_post = getItem(position)


        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val parsedDate: Date? = dateFormat.parse(feed_post?.tanggal_post)

        val targetDateFormat = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
        val formattedDate: String = targetDateFormat.format(parsedDate)

        val pemasukan = feed_post?.biaya?.toDouble() ?: 0.0

        // Debug logs
        println("Position: $position")
        println("Feed Post: $feed_post")

        if (feed_post != null) {
            textStatus.text = feed_post.status
            textJudul.text = feed_post.judul_post
            textHarga.text = formatNominal(pemasukan)
            textAlamat.text = feed_post.alamat
            textTelpone.text = feed_post.telepon.toString()
            textKeterangan.text = feed_post.keterangan
            textTanggal.text = formattedDate
        } else {
            textStatus.text = "N/A"
            textJudul.text = "N/A"
            textHarga.text = "N/A"
        }

        return view
    }


    private fun formatNominal(value: Double): String {
        return decimalFormat.format(value)
    }

}