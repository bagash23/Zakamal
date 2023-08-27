package com.example.zakamal.ui.Home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.zakamal.R
import com.example.zakamal.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportActionBar?.hide()
    }
}