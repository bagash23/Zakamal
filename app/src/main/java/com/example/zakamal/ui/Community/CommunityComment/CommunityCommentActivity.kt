package com.example.zakamal.ui.Community.CommunityComment

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zakamal.R
import com.example.zakamal.api.DomainApi
import com.example.zakamal.databinding.ActivityCommunityCommentBinding
import com.example.zakamal.model.CommunityItem
import com.example.zakamal.model.monitoring.AddKomentarResponse
import com.example.zakamal.model.monitoring.AllProvinsiData
import com.example.zakamal.model.monitoring.KomentarRequest
import com.example.zakamal.model.monitoring.KomentarResponse
import com.example.zakamal.utils.Preference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class CommunityCommentActivity : AppCompatActivity() {

    lateinit var binding: ActivityCommunityCommentBinding
    lateinit var communityCommentAdapter: CommunityCommentAdapter

    private lateinit var preferences: Preference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommunityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = Preference(this)

        val idPost = intent.getStringExtra("EXTRA_DATA_KOMUNITAS")
        val idInt: Int = idPost!!.toInt()
        val idUser = preferences.getValues("ID_USER")
        val idUserInt: Int = idUser!!.toInt()
        println("userID $idUser")
        println("postID $idInt")

//        api
        val apiServices = DomainApi.monitoringService
        val getById: Call<AllProvinsiData> = apiServices.getByIDPost(idInt)
        val getKomentarById: Call<List<KomentarResponse>> = apiServices.getKomentarByID(idInt)



        findViewById<Button>(R.id.btn_send_add).setOnClickListener {
            val requestData = KomentarRequest()
            requestData.komentar = findViewById<EditText>(R.id.edt_add_komen).text.toString().trim()

            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Mohon Tunggu...")
            progressDialog.show()

            val addKomentar: Call<AddKomentarResponse> = apiServices.addKomentar(idUserInt, idInt, requestData)
            addKomentar.enqueue(object : Callback<AddKomentarResponse> {
                override fun onResponse(
                    call: Call<AddKomentarResponse>,
                    response: Response<AddKomentarResponse>
                ) {
                    if (response.isSuccessful) {
                        val message = response.body()
                        Toast.makeText(this@CommunityCommentActivity, "${message!!.message}", Toast.LENGTH_SHORT).show()
                        progressDialog.dismiss()
                        findViewById<EditText>(R.id.edt_add_komen).setText("")
                        fetchComments(idInt)

                    } else {
                        println("ERROR CODE ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<AddKomentarResponse>, t: Throwable) {
                    println("ERROR ${t.message}")
                }

            })



        }

        getById.enqueue(object : Callback<AllProvinsiData> {
            override fun onResponse(
                call: Call<AllProvinsiData>,
                response: Response<AllProvinsiData>
            ) {
                if (response.isSuccessful) {
                    val dataObject = response.body()

                    println("data object $dataObject")

                    if (dataObject != null) {
                        binding.txtStatus.text = dataObject.status
                        binding.txtJudul.text = dataObject.judul_post
                        binding.txtHarga.text = dataObject.biaya.toString()

                        binding.txtAlamat.text = dataObject.alamat
                        binding.txtTelpone.text = dataObject.telepon.toString()
                        binding.txtKeterangan.text = dataObject.keterangan

                        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                        val parsedDate: Date? = dateFormat.parse(dataObject?.tanggal_post)
                        val targetDateFormat = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
                        val formattedDate: String = targetDateFormat.format(parsedDate)

                        binding.txtTglPost.text = formattedDate

                        if (dataObject.status == "Diterima") {
                            binding.txtStatus.setTextColor(resources.getColor(R.color.Light_Green))
                        } else if (dataObject.status == "Ditolak") {
                            binding.txtStatus.setTextColor(resources.getColor(R.color.Primary_Red))
                        } else {
                            binding.txtStatus.setTextColor(resources.getColor(R.color.Primary_Orange))
                        }

                    } else {
                        println("No data received")
                    }
                } else {
                    println("RESPONSE CODE ${response.code()}")
                }
            }

            override fun onFailure(call: Call<AllProvinsiData>, t: Throwable) {
                println("ERROR ${t.message}")
            }
        })

        fetchComments(idInt)


        binding.ivBackComment.setOnClickListener {
            onBackPressed()
        }
    }

    private fun fetchComments(id: Int) {
        val apiServices = DomainApi.monitoringService
        val getKomentarById: Call<List<KomentarResponse>> = apiServices.getKomentarByID(id)
        getKomentarById.enqueue(object : Callback<List<KomentarResponse>> {
            override fun onResponse(
                call: Call<List<KomentarResponse>>,
                response: Response<List<KomentarResponse>>
            ) {
                if (response.isSuccessful) {
                    val komentarList = response.body()

                    println("komentarList $komentarList")

                    komentarList?.let {
                        // Set up RecyclerView adapter
                        communityCommentAdapter = CommunityCommentAdapter(it.map { comment ->
                            CommunityItem(
                                comment.nama.toString(),
                                comment.komentar.toString(),
                                comment.id_user.toString()
                            )
                        })
                        binding.rvChat.adapter = communityCommentAdapter

                        // Set up RecyclerView layout manager
                        binding.rvChat.layoutManager = LinearLayoutManager(this@CommunityCommentActivity)
                    }
                } else {
                    println("ERROR CODE ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<KomentarResponse>>, t: Throwable) {
                println("ERROR ${t.message}")
            }

        })

    }
}