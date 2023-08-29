package com.example.zakamal.BottomNav

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.db.williamchart.view.BarChartView
import com.example.zakamal.Provinsi.ProvinsiActivity
import com.example.zakamal.R
import com.example.zakamal.api.DomainApi
import com.example.zakamal.databinding.FragmentMonitoringBinding
import com.example.zakamal.dummy.DummyTahun
import com.example.zakamal.model.monitoring.MonitoringResponse
import com.example.zakamal.utils.CustomeArrayAdapterTahun
import com.example.zakamal.utils.MonitoringChartAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.Year

class MonitoringFragment : Fragment() {

    private var _binding: FragmentMonitoringBinding? =null
    private val binding get() = _binding!!
    private var selectedProvinsiName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMonitoringBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnShowBottomSheet: Button = binding.btnClickProvinsi
        val btnShowBottomSheetTahun: Button = binding.btnClickTahun
        val listDetailMonitoring: ListView = view.findViewById(R.id.monitoringListView)
        val swipeRefreshLayout: SwipeRefreshLayout = binding.swipeRefreshPageMonitoring

        val currentYear = Year.now().value.toString()
        binding.btnClickTahun.text = currentYear

        btnShowBottomSheetTahun.setOnClickListener {
            showBottomSheetDialog(currentYear)
        }

        btnShowBottomSheet.setOnClickListener {
            val intent = Intent(context, ProvinsiActivity::class.java)
            startActivityForResult(intent, PROVINSI_REQUEST_CODE)
        }

        // Load selected provinsi from SharedPreferences
        val sharedPreferences = requireContext().getSharedPreferences("selectedProvinsi", Context.MODE_PRIVATE)
        selectedProvinsiName = sharedPreferences.getString("selectedProvinsi", null)
        selectedProvinsiName?.let {
            binding.btnClickProvinsi.text = it
            binding.txtMonitorProvinsi.text = it
        }

        val sharedYearPreferences = requireContext().getSharedPreferences("selectedYear", Context.MODE_PRIVATE)
        val selectedYear = sharedYearPreferences.getString("selectedYear", null)
        selectedYear?.let {
            binding.btnClickTahun.text = it
        }

//        render chart
        val dataProvinsi = selectedProvinsiName.toString()
        val dataTahunan = selectedYear.toString()
        getMonitoringZakat(dataProvinsi, dataTahunan, listDetailMonitoring)

        swipeRefreshLayout.setOnRefreshListener {
            val dataProvinsi = selectedProvinsiName.toString()
            val dataTahunan = selectedYear.toString()
            getMonitoringZakat(dataProvinsi, dataTahunan, listDetailMonitoring)
            Toast.makeText(requireContext(), "Refresh Page", Toast.LENGTH_SHORT).show()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun showBottomSheetDialog(initialSelectedYear: String) {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val contentView = layoutInflater.inflate(R.layout.layout_bottom_sheet_tahun, null)
        val tahunList = DummyTahun.createDummyListTahun()
        val listView: ListView = contentView.findViewById(R.id.listViewTahun)
        val selectedTahun = binding.btnClickTahun.text.toString() // Get the initially selected year

        val adapter = CustomeArrayAdapterTahun(requireContext(), R.layout.item_tahun, tahunList, selectedTahun)
        listView.adapter = adapter

        println("tahun sekarang: ${ initialSelectedYear }")

        val initialYearPosition = tahunList.indexOfFirst { it.name == initialSelectedYear }
        if (initialYearPosition != -1) {
            listView.setSelection(initialYearPosition)
        }

        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedTahun = tahunList[position]
            println("Selected Year: ${selectedTahun.name}")
            binding.btnClickTahun.text = selectedTahun.name
            val sharedPreferences = requireContext().getSharedPreferences("selectedYear", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("selectedYear", selectedTahun.name)
            editor.apply()
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.setContentView(contentView)
        bottomSheetDialog.show()
    }

    private fun getMonitoringZakat(provinsi: String, tahun: String, listView: ListView) {
        val monitoringCall = DomainApi.monitoringService.getMonitoring(provinsi, tahun)
        monitoringCall.enqueue(object : Callback<List<MonitoringResponse>> {
            override fun onResponse(call: Call<List<MonitoringResponse>>, response: Response<List<MonitoringResponse>>) {
                if (response.isSuccessful) {
                    val monitoringDataList = response.body()
                    val chartData = createChartData(monitoringDataList)
                    updateBarChart(chartData)
                    listView.adapter = MonitoringChartAdapter(requireContext(), monitoringDataList)
                } else {
                    println("Response not successful: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<MonitoringResponse>>, t: Throwable) {
                println("error ${t.message}")
            }
        })
    }

    private fun createChartData(monitoringDataList: List<MonitoringResponse>?): List<Pair<String, Float>> {
        return monitoringDataList?.map {
            it.bulan?.take(3).orEmpty() to (it.totalKeseluruhan?.toFloat() ?: 0F)
        } ?: emptyList()
    }

    private fun updateBarChart(chartData: List<Pair<String, Float>>) {
        val barChartView: BarChartView = binding.barChart
        val animationDuration = 1000L

        barChartView.animation.duration = animationDuration

        val chartEntries = chartData.map { (label, value) ->
            Pair(label, value)
        }
        barChartView.show(chartEntries)
    }


    companion object {
        const val PROVINSI_REQUEST_CODE = 1
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}