package com.example.zakamal.BottomNav

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.db.williamchart.view.BarChartView
import com.example.zakamal.Provinsi.ProvinsiActivity
import com.example.zakamal.R
import com.example.zakamal.databinding.FragmentMonitoringBinding
import com.example.zakamal.dummy.DummyProvinsi
import com.example.zakamal.dummy.DummyTahun
import com.example.zakamal.utils.CustomeArrayAdapterTahun
import com.example.zakamal.utils.ProvinsiAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
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
        val barChartView: BarChartView = binding.barChart
        val animationDuration = 1000L
        val barSet = listOf(
            "JAN" to 4F,
            "FEB" to 7F,
            "MAR" to 2F,
            "APR" to 2.3F,
            "MAY" to 5F,
            "JUN" to 4F,
            "JUL" to 5F,
            "AUG" to 2F,
            "SEP" to 5F,
            "OCT" to 4F,
            "NOV" to 3F,
            "DEC" to 5F

        )

        barChartView.animation.duration = animationDuration
        barChartView.animate(barSet)

        val btnShowBottomSheet: Button = binding.btnClickProvinsi
        val btnShowBottomSheetTahun: Button = binding.btnClickTahun

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

            // Update the text of the button
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PROVINSI_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val selectedProvinsi = data?.getStringExtra("selectedProvinsi")
            selectedProvinsi?.let {
                binding.btnClickProvinsi.text = it
                binding.txtMonitorProvinsi.text = it
                selectedProvinsiName = it
            }
        }
    }


    companion object {
        const val PROVINSI_REQUEST_CODE = 1
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}