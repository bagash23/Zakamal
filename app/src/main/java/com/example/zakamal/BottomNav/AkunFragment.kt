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
import com.example.zakamal.utils.Preference
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException

class AkunFragment : Fragment() {

    private var _binding: FragmentAkunBinding? =null
    private val binding get() = _binding!!

    private lateinit var preference: Preference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        preference = Preference(requireActivity().applicationContext)
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


        val idUser = preference.getValues("NAMA_LENGKAP")
        val idPezakat = preference.getValues("TELEPON")
        val namaLengkap = preference.getValues("EMAIL")

        // Combine all data into a single string
        val userData = "ID_USER: $idUser\n" +
                "ID_PEZAKAT: $idPezakat\n" +
                "NAMA_LENGKAP: $namaLengkap\n"

//        qrCode Generator
        val bitmap = generateQRCode(userData)
        imgQR.setImageBitmap(bitmap)


// result
        setLocationText.let {
            binding.akunLocation.text = it
        }
        idVersi.setText(versionName)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preference = Preference(requireActivity().applicationContext)
        binding.tvGreetingUser.setText(preference.getValues("NAMA_LENGKAP"))
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