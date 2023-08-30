package com.example.zakamal.ui.Login

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.zakamal.BottomNav.HomeFragment
import com.example.zakamal.MainActivity
import com.example.zakamal.api.DomainApi
import com.example.zakamal.api.login.Login
import com.example.zakamal.api.login.LoginRequestBody
import com.example.zakamal.databinding.ActivityLoginBinding
import com.example.zakamal.model.login.LoginResponse
import com.example.zakamal.ui.Register.RegisterActivity
import com.example.zakamal.utils.Preference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var preferences: Preference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        preferences = Preference(this)

        binding.llRegisterHere.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }


        binding.btnMasuk.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (email.isEmpty()) {
                binding.etEmail.error = "Email harus diisi"
                binding.etEmail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.etPassword.error = "Password harus diisi"
                binding.etPassword.requestFocus()
                return@setOnClickListener
            }

            userLogin(email, password)
        }
    }

    private fun userLogin(email: String, password: String) {


        val loginRequest = LoginRequestBody(
            email = email,
            password = password
        )

        val sendLogin = DomainApi.loginService.postLogin(loginRequest)

        sendLogin.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    val loginResponse: LoginResponse? = response.body()
                    Log.d("LoginActivity", "onResponse: $loginResponse")
                    loginResponse?.let {
                        // Handle the successful login response

                        Toast.makeText(this@LoginActivity, "Berhasil", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        preferences.setLogin("ID_USER", loginResponse.userId.toString())
                        preferences.setLogin("ID_PEZAKAT", loginResponse.pezakatId.toString())
                        preferences.setLogin("NAMA_LENGKAP", loginResponse.namaLengkap)
                        preferences.setLogin("TELEPON", loginResponse.telepon.toString())
                        preferences.setLogin("EMAIL", loginResponse.email)
                        preferences.setLogin("PASSWORD", loginResponse.password)
                        preferences.setLogin("ID_PROVINSI", loginResponse.idProvinsi.toString())
                        preferences.setLoginInt("ID_ROLE", loginResponse.idRole)
//                        intent.putExtra("EXTRA_ID", 1)
                        startActivity(intent)

                    }
                } else {
                    // Handle the unsuccessful login response
                    Toast.makeText(this@LoginActivity, "Gagal", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                // Handle the network failure
                Toast.makeText(this@LoginActivity, "Gagal", Toast.LENGTH_SHORT).show()
            }
        })
    }


}