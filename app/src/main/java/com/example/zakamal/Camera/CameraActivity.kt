package com.example.zakamal.Camera

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.widget.TintableCompoundButton
import com.example.zakamal.R
import com.example.zakamal.utils.Constant.Companion.CAMERA_REQUEST_CODE
import com.google.common.util.concurrent.ListenableFuture
import java.io.File

class CameraActivity : AppCompatActivity() {

    private lateinit var previewView: PreviewView
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var button: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        previewView = findViewById(R.id.previewView)
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        button = findViewById(R.id.btn_capture)

        cameraProviderFuture.addListener(Runnable {
            val cameraProvider = cameraProviderFuture.get()
            bindPreview(cameraProvider)
        }, ContextCompat.getMainExecutor(this))

        button.setOnClickListener {
            captureImage()
        }


    }

    private fun bindPreview(cameraProvider: ProcessCameraProvider) {
        val preview = Preview.Builder().build()
        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        try {
            // Unbind any previous use cases before rebinding
            cameraProvider.unbindAll()

            // Bind the preview use case to the camera
            cameraProvider.bindToLifecycle(this, cameraSelector, preview)

            // Attach the preview view to the preview use case
            preview.setSurfaceProvider(previewView.surfaceProvider)

        } catch (exception: Exception) {
            // Handle any errors that occur during binding
            Toast.makeText(this, "Error binding camera preview", Toast.LENGTH_SHORT).show()
            exception.printStackTrace()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val imageUri = data?.getParcelableExtra<Uri>("imageResult")

            // Do something with the captured image URI


            val resultIntent = Intent()
            resultIntent.putExtra("imageResult", imageUri)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }


    private fun captureImage() {
        // Capture the image and get the result
        val imageFile = File(cacheDir, "captured_image.jpg")

        // Save the captured image to the file
        // ...

        // Generate a content URI for the image file using FileProvider
        val imageUri = FileProvider.getUriForFile(
            this,
            "com.example.zakamal.fileprovider",
            imageFile
        )

        // Set the result and finish the activity
        val resultIntent = Intent()
        resultIntent.putExtra("imageResult", imageUri)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }


}