package com.example.zakamal.ui.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.zakamal.MainActivity
import com.example.zakamal.api.DomainApi
import com.example.zakamal.api.login.Login
import com.example.zakamal.api.login.LoginRequestBody
import com.example.zakamal.databinding.ActivityLoginBinding
import com.example.zakamal.model.login.LoginResponse
import com.example.zakamal.ui.Register.RegisterActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.btnMasuk.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("EXTRA_ID", 2)
            startActivity(intent)
        }

        binding.llRegisterHere.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        val loginRequest = LoginRequestBody(
            email = "hayhhw@gmail.com",
            password = "passwordnic"
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