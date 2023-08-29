package com.example.zakamal.ui.AdminPostingan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.zakamal.R
import com.example.zakamal.databinding.ActivityAdminPostinganBinding

class AdminPostinganActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminPostinganBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminPostinganBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var GET_EXTRA_STATUS_POSTINGAN = intent.getStringExtra("EXTRA_STATUS_POSTINGAN").toString()


        if (GET_EXTRA_STATUS_POSTINGAN == "KETERIMA") {
            binding.tvStatusPostinganAdmin.text = "KETERIMA"
            binding.tvStatusPostinganAdmin.setTextColor(resources.getColor(R.color.Light_Green))
        } else if (GET_EXTRA_STATUS_POSTINGAN == "DIPROSES") {
            binding.tvStatusPostinganAdmin.text = "DIPROSES"
            binding.tvStatusPostinganAdmin.setTextColor(resources.getColor(R.color.Primary_Orange))
            binding.llPostinganAdminCard.setOnClickListener {
                val intent = Intent(this, AdminPostinganChangeStatusActivity::class.java)
                startActivity(intent)
            }
        } else if (GET_EXTRA_STATUS_POSTINGAN == "DITOLAK") {
            binding.tvStatusPostinganAdmin.text = "DITOLAK"
            binding.tvStatusPostinganAdmin.setTextColor(resources.getColor(R.color.Primary_Red))

        }

    }
}