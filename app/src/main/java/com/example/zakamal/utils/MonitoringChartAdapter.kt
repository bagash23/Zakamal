package com.example.zakamal.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.zakamal.R
import com.example.zakamal.model.monitoring.MonitoringResponse
import java.text.DecimalFormat

class MonitoringChartAdapter(context: Context, dataList: List<MonitoringResponse>?) : ArrayAdapter<MonitoringResponse>(context, 0,
    dataList!!
) {

    private val decimalFormat = DecimalFormat("#,##0.00")


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.rc_list_monitoring, parent, false)
        }

        val monitoringData = getItem(position)
        val bulanTextView: TextView = itemView!!.findViewById(R.id.mon_bulan)
        val tahunTextView: TextView = itemView.findViewById(R.id.mon_tahun)
        val pemasukanTextView: TextView = itemView.findViewById(R.id.mon_pemasukan)
        val pengluaranTextView: TextView = itemView.findViewById(R.id.mon_pengluaran)

        bulanTextView.text = monitoringData?.bulan
        tahunTextView.text = monitoringData?.tahun.toString()
        pemasukanTextView.text = monitoringData?.totalTerkumpul.toString()
        pengluaranTextView.text = monitoringData?.totalPengeluaran.toString()

        val pemasukan = monitoringData?.totalTerkumpul?.toDouble() ?: 0.0
        val pengeluaran = monitoringData?.totalPengeluaran?.toDouble() ?: 0.0

        pemasukanTextView.text = formatNominal(pemasukan)
        pengluaranTextView.text = formatNominal(pengeluaran)

        return itemView!!
    }

    private fun formatNominal(value: Double): String {
        return decimalFormat.format(value)
    }

}