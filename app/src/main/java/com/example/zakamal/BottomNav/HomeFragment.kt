package com.example.zakamal.BottomNav


import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.DnsResolver
import android.net.DnsResolver.Callback
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.zakamal.api.DomainApi
import com.example.zakamal.databinding.FragmentHomeBinding
import com.example.zakamal.databinding.FragmentHomeUserBinding
import com.example.zakamal.model.monitoring.AllProvinsiData
import com.example.zakamal.model.monitoring.PostFeedAdminPendingResponse
import com.example.zakamal.ui.AdminPostingan.AdminPostinganActivity
import com.example.zakamal.ui.Community.CommunityAll.CommunityAllActivity
import com.example.zakamal.ui.Request.RequestActivity
import com.example.zakamal.utils.KomunitasHomeAdapter
import com.example.zakamal.utils.Preference

import com.google.android.gms.location.FusedLocationProviderClient
import retrofit2.Call
import retrofit2.Response
import java.util.Locale
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? =null
    private var _bindingUser: FragmentHomeUserBinding? =null

    private val binding get() = _binding!!
    private val bindingUser get() = _bindingUser!!

    private var isUser = false
    private var extraId = 1

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var preference: Preference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        preference = Preference(requireActivity().applicationContext)
        extraId = preference.getValuesInt("ID_ROLE") ?: 1
        if (extraId == 1) {
            isUser = false
        } else {
            isUser = true
        }



        if (!isUser) {
            _binding = FragmentHomeBinding.inflate(inflater, container, false)
            return binding.root
        } else {
            _bindingUser = FragmentHomeUserBinding.inflate(inflater, container, false)
            return bindingUser.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationClient = FusedLocationProviderClient(requireActivity())
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        } else {
            getCurrentLocation()
        }
        checkUser()
    }

    private fun getPostinganKomunitas(nomor: Int) {
        val apiService = DomainApi.monitoringService
        val call = apiService.getStatusPending(nomor)
        call.enqueue(object : retrofit2.Callback<PostFeedAdminPendingResponse> {
            override fun onResponse(
                call: Call<PostFeedAdminPendingResponse>,
                response: Response<PostFeedAdminPendingResponse>
            ) {
                if (response.isSuccessful) {
                    val totalData = response.body()!!.totalData
                    if (nomor == 1 ){
                        binding.tvNumber3.text = totalData.toString()
                    } else if (nomor == 2) {
                        binding.tvNumber1.text = totalData.toString()
                    } else if (nomor == 3) {
                        binding.tvNumber2.text = totalData.toString()
                    }


                } else {
                    println("ERROR ${response.code()}")
                }
            }

            override fun onFailure(call: Call<PostFeedAdminPendingResponse>, t: Throwable) {
                println("ERROR ${t.message}")
            }

        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        preference = Preference(requireActivity().applicationContext)
        if (!isUser) {
            binding.tvGreetingUser.setText(preference.getValues("NAMA_LENGKAP")) ?: "User"
        } else {
            bindingUser.tvGreetingUser.setText(preference.getValues("NAMA_LENGKAP")) ?: "User"
        }

    }

    private fun getKomunitasAll(listView: ListView) {
        val allKomunitas = DomainApi.monitoringService.getAllPosttFeed()
        allKomunitas.enqueue(object : retrofit2.Callback<List<AllProvinsiData>> {
            override fun onResponse(
                call: Call<List<AllProvinsiData>>,
                response: Response<List<AllProvinsiData>>
            ) {
                if (response.isSuccessful) {
                    val komunitasAlls = response.body()
                    println("komunitasAlls $komunitasAlls")

                    listView.adapter = KomunitasHomeAdapter(requireContext(), komunitasAlls!!)
                } else {
                    println("Response code error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<AllProvinsiData>>, t: Throwable) {
                println("Error ${t.message}")
            }

        })
    }

    private fun checkUser() {
        if (isUser) {
            bindingUser.tvHomeSeeAll.setOnClickListener {
                val intent = Intent(requireContext(), CommunityAllActivity::class.java)
                startActivity(intent)
            }

            bindingUser.llRequest.setOnClickListener {
                val intent = Intent(requireContext(), RequestActivity::class.java)
                startActivity(intent)
            }

//            Log.d("TAG", "checkUser: ${preference.getValues("NAMA_LENGKAP")}")
//            binding.tvGreetingUser.setText(preference.getValues("NAMA_LENGKAP")) ?: "User"

            val listView = bindingUser.komunitasHome
            getKomunitasAll(listView)


        } else {
            binding.tvHomeSeeAll.setOnClickListener {
                val intent = Intent(requireContext(), CommunityAllActivity::class.java)
                startActivity(intent)
            }

            binding.llKeterimaPostingan.setOnClickListener {
                val intent = Intent(requireContext(), AdminPostinganActivity::class.java)
                intent.putExtra("EXTRA_STATUS_POSTINGAN", "2")
                startActivity(intent)
            }

            binding.llPostinganPending.setOnClickListener {
                val intent = Intent(requireContext(), AdminPostinganActivity::class.java)
                intent.putExtra("EXTRA_STATUS_POSTINGAN", "1")
                startActivity(intent)
            }

            binding.llPostinganDitolak.setOnClickListener {
                val intent = Intent(requireContext(), AdminPostinganActivity::class.java)
                intent.putExtra("EXTRA_STATUS_POSTINGAN", "3")
                startActivity(intent)
            }

            getPostinganKomunitas(1)
            getPostinganKomunitas(2)
            getPostinganKomunitas(3)

            val listView = binding.komunitasHome
            getKomunitasAll(listView)
        }
    }


    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {


        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                // Got last known location. In some rare situations, this can be null.


                if (location != null) {
                    val geocoder = Geocoder(requireContext(), Locale.getDefault())
                    val sharedPreferences = requireContext().getSharedPreferences("location", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    val addresses: List<Address> =
                        geocoder.getFromLocation(location.latitude, location.longitude, 1) as List<Address>
                    val cityName: String = addresses[0].locality
                    val country: String = addresses[0].countryName
                    println("data lokasi: ${addresses[0].countryCode}")
                    Toast.makeText(requireContext(), cityName, Toast.LENGTH_LONG).show()
                    editor.putString("location", cityName + ", "+ country)
                    editor.apply()
                    if (!isUser) {
                        binding.tvLocation.text = cityName + ", "+ country
                    } else {
                        bindingUser.tvLocation.text = cityName + ", "+ country
                    }
                }
            }
    }
}