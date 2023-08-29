package com.example.zakamal.ui.Request

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import com.example.zakamal.Camera.CameraActivity
import com.example.zakamal.R
import com.example.zakamal.databinding.ActivityRequestBinding
import com.example.zakamal.utils.Constant
import com.example.zakamal.utils.Constant.Companion.CAMERA_REQUEST_CODE
import androidx.appcompat.widget.AppCompatImageView
import com.example.zakamal.api.DomainApi
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class RequestActivity : AppCompatActivity() {

    lateinit var binding: ActivityRequestBinding
    private val CAMERA_PERMISSION = arrayOf(Manifest.permission.CAMERA)
    private val TAG = "cameraX"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.llTakeImage.setOnClickListener {
            if (hasCameraPermission()) {
                enableCamera()
            } else {
                requestPermission()
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val imageUri = data?.getParcelableExtra<Uri>("imageResult")
            // Do something with the captured image URI

        }

//        binding.btnSubmit.setOnClickListener {
//            val userId = intent.getStringExtra("userId")
//            val idProvinsi = binding.etProvinsi.text.toString().toInt()
//            val judulPost = binding.etJudul.text.toString()
//            val biaya = binding.etBiaya.text.toString().toInt()
//            val alamat = binding.etAlamat.text.toString()
//            val keterangan = binding.etKeterangan.text.toString()
//            val dokumenFile = File(binding.ivPreview.toString())
//
//            createUserPost(userId!!, idProvinsi, judulPost, biaya, alamat, keterangan, dokumenFile)
//        }
    }


    private fun enableCamera() {
        val intent = Intent(this, CameraActivity::class.java)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    private fun hasCameraPermission(): Boolean {
        return checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            CAMERA_PERMISSION,
            CAMERA_REQUEST_CODE
        )
    }



}