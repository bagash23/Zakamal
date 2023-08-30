package com.example.zakamal.ui.Request

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import com.example.zakamal.Camera.CameraActivity
import com.example.zakamal.R
import com.example.zakamal.api.DomainApi
import com.example.zakamal.databinding.ActivityRequestBinding
import com.example.zakamal.model.monitoring.AddZakatRequests
import com.example.zakamal.model.monitoring.AddZakatResponse
import com.example.zakamal.utils.Constant.Companion.CAMERA_REQUEST_CODE
import com.example.zakamal.utils.Preference
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

class RequestActivity : AppCompatActivity() {

    lateinit var binding: ActivityRequestBinding
    private val CAMERA_PERMISSION = arrayOf(Manifest.permission.CAMERA)
    private val TAG = "cameraX"
    private val PDF_REQUEST_CODE = 101
    private var pdfUri: Uri? = null

    private lateinit var preference: Preference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preference = Preference(this)
        val idUsers = preference.getValues("ID_USER")!!.toInt()
        println("ID $idUsers")

        val sharedPreferences = getSharedPreferences("numberProvinsi", Context.MODE_PRIVATE)

        binding.llTakeImage.setOnClickListener {
            selectPdf()
        }

        binding.button4.setOnClickListener {
            addData(pdfUri!!, getSharedPreferences("numberProvinsi", Context.MODE_PRIVATE).getInt("numberProvinsi", 0),idUsers)
        }

    }

    private fun selectPdf() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "application/pdf"
        startActivityForResult(intent, PDF_REQUEST_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PDF_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            pdfUri = data?.data
            if (pdfUri != null) {
                binding.linearLayoutFile.visibility = View.VISIBLE
                binding.llTakeImage.visibility = View.GONE
                binding.txtForamt.setText("Data-Dokumen-Pengajuan.pdf")
                println("PDF URI: $pdfUri")
            } else {
                binding.linearLayoutFile.visibility = View.GONE
                binding.llTakeImage.visibility = View.VISIBLE
                println("PDF URI is null")
            }
        }

    }


    private fun addData(pdf: Uri, idProv: Int, idUser: Int) {

        println("provinsi $idProv")
        println("user $idUser")

        val apiServices = DomainApi.monitoringService
        val alamatRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), binding.editTextText2.text.toString())
        val judulPostRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), binding.editTextText.text.toString())
        val keteranganRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), binding.editTextText5.text.toString())
        val biayaRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), binding.editTextText4.text.toString())

        if (binding.editTextText2.text.toString().isEmpty()) {
            binding.editTextText2.error = "Alamat tidak boleh kosong"
            binding.editTextText2.requestFocus()
            return
        }

        if (binding.editTextText.text.toString().isEmpty()) {
            binding.editTextText.error = "Judul tidak boleh kosong"
            binding.editTextText.requestFocus()
            return
        }

        if (binding.editTextText4.text.toString().isEmpty()) {
            binding.editTextText4.error = "Biaya tidak boleh kosong"
            binding.editTextText4.requestFocus()
            return
        }

        if (binding.editTextText5.text.toString().isEmpty()) {
            binding.editTextText5.error = "Keterangan tidak boleh kosong"
            binding.editTextText5.requestFocus()
            return
        }


        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Mohon Tunggu...")
        progressDialog.show()

        val filePart = pdf?.let { uri ->
            val contentResolver = contentResolver
            val inputStream = contentResolver.openInputStream(uri)

            val mediaType = "application/pdf".toMediaTypeOrNull()
            val file = File(getCacheDir(), "Data-Dokumen-Pengajuan.pdf")
            file.deleteOnExit()

            val outputStream = FileOutputStream(file)
            inputStream?.copyTo(outputStream)

            val requestFile = RequestBody.create(mediaType, file)
            MultipartBody.Part.createFormData("dokumen", file.name, requestFile)
        }

        println(filePart)

        if (filePart == null) {
            progressDialog.dismiss()
            println("Error: filePart is null")
            return
        }

        val addZakat: Call<AddZakatResponse> = apiServices.addZakat(
            idUser,
            idProv,
            judulPostRequestBody,
            biayaRequestBody,
            alamatRequestBody,
            keteranganRequestBody,
            filePart
        )
        addZakat.enqueue(object : Callback<AddZakatResponse> {
            override fun onResponse(
                call: Call<AddZakatResponse>,
                response: Response<AddZakatResponse>
            ) {
                progressDialog.dismiss()

                if (response.isSuccessful) {
                    val message = response.body()
                    println("Message $message")

                    binding.editTextText2.text.clear()
                    binding.editTextText.text.clear()
                    binding.editTextText4.text.clear()
                    binding.editTextText5.text.clear()
                    binding.linearLayoutFile.visibility = View.GONE
                    binding.llTakeImage.visibility = View.VISIBLE

                    setResult(Activity.RESULT_OK)
                    finish()
                } else {
                    println("ERROR CODE ${response.code()}")
                }
            }

            override fun onFailure(call: Call<AddZakatResponse>, t: Throwable) {
                progressDialog.dismiss()
                println("ERROR ${t.message}")
            }
        })
    }

}