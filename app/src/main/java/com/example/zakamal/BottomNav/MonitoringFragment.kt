package com.example.zakamal.BottomNav

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
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.db.williamchart.view.BarChartView
import com.example.zakamal.Provinsi.ProvinsiActivity
import com.example.zakamal.R
import com.example.zakamal.databinding.FragmentMonitoringBinding
import com.example.zakamal.dummy.DummyProvinsi
import com.example.zakamal.utils.ProvinsiAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog

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

        val txtProvMonitor: TextView = binding.txtMonitorProvinsi
        val btnShowBottomSheet: Button = binding.btnClickProvinsi

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

    }

    private fun showBottomSheetDialog() {

        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val contentView = layoutInflater.inflate(R.layout.layout_bottom_sheet, null)
        val provinsiList = DummyProvinsi.createDummyList()

        // Find the RecyclerView inside the bottom sheet layout
        val recyclerView: RecyclerView = contentView.findViewById(R.id.recycler_view_provinsi)

        // Set up the RecyclerView adapter and layout manager
        val adapter = ProvinsiAdapter(provinsiList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Set the bottom sheet content view and show the dialog
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