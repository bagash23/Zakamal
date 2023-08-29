package com.example.zakamal.ui.Community.CommunityComment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zakamal.api.DomainApi
import com.example.zakamal.databinding.ActivityCommunityCommentBinding
import com.example.zakamal.model.CommunityItem
import com.example.zakamal.model.monitoring.AllProvinsiData
import com.example.zakamal.model.monitoring.KomentarResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommunityCommentActivity : AppCompatActivity() {

    lateinit var binding: ActivityCommunityCommentBinding
    lateinit var communityCommentAdapter: CommunityCommentAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommunityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idPost = intent.getStringExtra("EXTRA_DATA_KOMUNITAS")
        val idInt: Int = idPost!!.toInt()
        println("ID POST ${idInt}")

//        api
        val apiServices = DomainApi.monitoringService
        val getById: Call<AllProvinsiData> = apiServices.getByIDPost(idInt)
        val getKomentarById: Call<List<KomentarResponse>> = apiServices.getKomentarByID(idInt)

        getById.enqueue(object : Callback<AllProvinsiData> {
            override fun onResponse(
                call: Call<AllProvinsiData>,
                response: Response<AllProvinsiData>
            ) {
                if (response.isSuccessful) {
                    val dataObject = response.body()

                    println("data object $dataObject")

                    if (dataObject != null) {
                        binding.textView11.text = dataObject.judul_post
                        binding.textView12.text = dataObject.biaya.toString()
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

        binding.ivBackComment.setOnClickListener {
            onBackPressed()
        }
    }
}