package com.example.zakamal.BottomNav

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.zakamal.BuildConfig
import com.example.zakamal.R
import com.example.zakamal.databinding.FragmentAkunBinding
import com.example.zakamal.databinding.FragmentMonitoringBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException

class AkunFragment : Fragment() {

    private var _binding: FragmentAkunBinding? =null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAkunBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Kumpulan id
        val versionName = BuildConfig.VERSION_NAME
        val idVersi: TextView = binding.textVersiCode
        val imgQR: ImageView = binding.generatedBarcode


//        panggilan data sharedPreferences
        val sharedPreferences = requireContext().getSharedPreferences("location", Context.MODE_PRIVATE)
        val setLocationText = sharedPreferences.getString("location", null)

//        qrCode Generator
        val bitmap = generateQRCode("Bagas Haryadi")
        imgQR.setImageBitmap(bitmap)


// result
        setLocationText.let {
            binding.akunLocation.text = it
        }
        idVersi.setText(versionName)
    }


    private fun generateQRCode(text: String): Bitmap {
        val width = 150
        val height = 150
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        val codeWriter = MultiFormatWriter()
        try {
            val bitMatrix = codeWriter.encode(text, BarcodeFormat.QR_CODE, width, height)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    val color = if (bitMatrix[x, y]) Color.BLACK else Color.WHITE
                    bitmap.setPixel(x, y, color)
                }
            }
        } catch (e: WriterException) {
            println("ERROR ${e.message}")
        }

        return bitmap
    }

}