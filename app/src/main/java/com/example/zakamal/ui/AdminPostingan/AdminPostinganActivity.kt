package com.example.zakamal.ui.AdminPostingan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zakamal.api.AdminGetCommunity.AdminGetCommunity
import com.example.zakamal.api.AdminGetCommunity.AdminGetCommunityDetail
import com.example.zakamal.api.AdminGetCommunity.AdminGetCommunityResponse
import com.example.zakamal.databinding.ActivityAdminPostinganBinding
import kotlin.math.log

class AdminPostinganActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminPostinganBinding
    private lateinit var recyclerView: AdminPostinganAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminPostinganBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var GET_EXTRA_STATUS_POSTINGAN = intent.getStringExtra("EXTRA_STATUS_POSTINGAN").toString()

        binding.rvAdminPostingan.layoutManager = LinearLayoutManager(this)
        recyclerView = AdminPostinganAdapter(listOf(
            AdminGetCommunityDetail(
                1,
                1,
                1,
                "Nama",
                "Judul Post",
                100000,
                "Alamat",
                "Keterangan",
                "DIPROSES",
                "Tanggal Post",
                "Nama Lengkap",
                "Nama Provinsi",
                "Nama Role"
            ),
            AdminGetCommunityDetail(
                2,
                2,
                2,
                "Nama",
                "Judul Post",
                100000,
                "Alamat",
                "Keterangan",
                "DIPROSES",
                "Tanggal Post",
                "Nama Lengkap",
                "Nama Provinsi",
                "Nama Role"
            ),
            AdminGetCommunityDetail(
                3,
                3,
                3,
                "Nama",
                "Judul Post",
                100000,
                "Alamat",
                "Keterangan",
                "DIPROSES",
                "Tanggal Post",
                "Nama Lengkap",
                "Nama Provinsi",
                "Nama Role"
            ),
        ))

        binding.rvAdminPostingan.adapter = recyclerView

//        if (GET_EXTRA_STATUS_POSTINGAN == "KETERIMA") {
//            binding.tvStatusPostinganAdmin.text = "KETERIMA"
//            binding.tvStatusPostinganAdmin.setTextColor(resources.getColor(R.color.Light_Green))
//        } else if (GET_EXTRA_STATUS_POSTINGAN == "DIPROSES") {
//            binding.tvStatusPostinganAdmin.text = "DIPROSES"
//            binding.tvStatusPostinganAdmin.setTextColor(resources.getColor(R.color.Primary_Orange))
//            binding.llPostinganAdminCard.setOnClickListener {
//                val intent = Intent(this, AdminPostinganChangeStatusActivity::class.java)
//                startActivity(intent)
//            }
//        } else if (GET_EXTRA_STATUS_POSTINGAN == "DITOLAK") {
//            binding.tvStatusPostinganAdmin.text = "DITOLAK"
//            binding.tvStatusPostinganAdmin.setTextColor(resources.getColor(R.color.Primary_Red))
//        }

    }

}