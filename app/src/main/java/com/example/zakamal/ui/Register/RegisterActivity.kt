package com.example.zakamal.ui.Register

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.zakamal.Provinsi.ProvinsiActivity
import com.example.zakamal.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSelectProvinsi.setOnClickListener {
            val intent = Intent(this, ProvinsiActivity::class.java)
            startActivity(intent)
        }

        val sharedPreferences = getSharedPreferences("selectedProvinsi", Context.MODE_PRIVATE)
        var selectedProvinsiName = sharedPreferences.getString("selectedProvinsi", null)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val sharedPreferences = getSharedPreferences("selectedProvinsi", Context.MODE_PRIVATE)
        var selectedProvinsiName = sharedPreferences.getString("selectedProvinsi", null)

        binding.btnSelectProvinsi.text = selectedProvinsiName
    }
}