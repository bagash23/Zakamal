package com.example.zakamal.ui.AdminPostingan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.zakamal.R
import com.example.zakamal.databinding.ActivityAdminPostinganChangeStatusBinding

class AdminPostinganChangeStatusActivity : AppCompatActivity() {
    lateinit var binding: ActivityAdminPostinganChangeStatusBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminPostinganChangeStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBackPostinganChangeStatusAdmin.setOnClickListener {
            onBackPressed()
        }
    }
}