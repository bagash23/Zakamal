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
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.zakamal.R
import com.example.zakamal.databinding.FragmentHomeBinding
import com.example.zakamal.databinding.FragmentHomeUserBinding
import com.example.zakamal.ui.Community.CommunityAll.CommunityAllActivity
import com.example.zakamal.ui.Community.CommunityComment.CommunityCommentActivity
import com.example.zakamal.ui.Request.RequestActivity

import com.google.android.gms.location.FusedLocationProviderClient
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? =null
    private var _bindingUser: FragmentHomeUserBinding? =null

    private val binding get() = _binding!!
    private val bindingUser get() = _bindingUser!!

    var isUser = true

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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

    private fun checkUser() {
        if (isUser) {
            bindingUser.tvHomeSeeAll.setOnClickListener {
                val intent = Intent(requireContext(), CommunityAllActivity::class.java)
                startActivity(intent)
            }

            bindingUser.llCommunityCard.setOnClickListener {
                val intent = Intent(requireContext(), CommunityCommentActivity::class.java)
                startActivity(intent)
            }

            bindingUser.llRequest.setOnClickListener {
                val intent = Intent(requireContext(), RequestActivity::class.java)
                startActivity(intent)
            }

        } else {
            binding.tvHomeSeeAll.setOnClickListener {
                val intent = Intent(requireContext(), CommunityAllActivity::class.java)
                startActivity(intent)
            }

            binding.llCommunityCard.setOnClickListener {
                val intent = Intent(requireContext(), CommunityCommentActivity::class.java)
                startActivity(intent)
            }
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