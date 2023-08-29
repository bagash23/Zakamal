package com.example.zakamal.ui.Register

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.zakamal.Provinsi.ProvinsiActivity
import com.example.zakamal.api.DomainApi
import com.example.zakamal.api.register.RegisterRequestBody
import com.example.zakamal.databinding.ActivityRegisterBinding
import com.example.zakamal.model.monitoring.MonitoringResponse
import com.example.zakamal.model.register.RegisterResponse
import com.example.zakamal.utils.MonitoringChartAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val registerRequestBody = RegisterRequestBody(
//            namaLengkap = "dapa",
//            telepon = 981233372902,
//            email = "tes1t28@gmail.com",
//            password = "123466756789",
//            idProvinsi = 1
//        )
//        postRegister(registerRequestBody)



        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // The Intent that was returned by the Activity
                val data: Intent? = result.data

                // Get the selected province name from SharedPreferences
                val sharedPreferences = getSharedPreferences("selectedProvinsi", Context.MODE_PRIVATE)
                val selectedProvinsiName = sharedPreferences.getString("selectedProvinsi", null)

                // Set the button text with the selected province name
                binding.btnSelectProvinsi.text = selectedProvinsiName
            }
        }

        binding.btnSelectProvinsi.setOnClickListener {
            val intent = Intent(this, ProvinsiActivity::class.java)
            startActivity(intent)
        }

        binding.btnRegister.setOnClickListener {
            val sharedPreferences = getSharedPreferences("numberProvinsi", Context.MODE_PRIVATE)
            val name = binding.edName.text.toString()
            val phoneText = binding.edTelp.text.toString()
            val email = binding.edEmail.text.toString()
            val password = binding.edPassword.text.toString()
            val provinsi = sharedPreferences.getInt("numberProvinsi", 0)

            var phone: Long
            try {
                phone = phoneText.toLong()
            } catch (e: NumberFormatException) {
                // Handle the case where the entered phone number is not a valid Long value
                Toast.makeText(this@RegisterActivity, "Invalid phone number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (name.isEmpty()) {
                binding.edName.error = "Name is required"
                binding.edName.requestFocus()
                return@setOnClickListener
            }

            if (phoneText.isEmpty()) {
                binding.edTelp.error = "Phone number is required"
                binding.edTelp.requestFocus()
                return@setOnClickListener
            }


            if (email.isEmpty()) {
                binding.edEmail.error = "Email is required"
                binding.edEmail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.edPassword.error = "Password is required"
                binding.edPassword.requestFocus()
                return@setOnClickListener
            }

            if (provinsi.toString().isEmpty()) {
                binding.btnSelectProvinsi.error = "Provinsi is required"
                binding.btnSelectProvinsi.requestFocus()
                return@setOnClickListener
            }

            val registerRequest = RegisterRequestBody(name, phone.toLong(), email, password, provinsi)

            Log.d("Register", registerRequest.toString())

            postRegister(registerRequest)

        }

    }

    private fun postRegister(registerRequest: RegisterRequestBody) {
        val sendRegister = DomainApi.registerService.postRegister(registerRequest)

        sendRegister.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    val registerResponse: RegisterResponse? = response.body()
                    registerResponse?.let {
                        val message: String? = registerResponse.message
                        Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val message: String = response.message()
                    Toast.makeText(this@RegisterActivity, "Fail" + message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, t.message.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

}