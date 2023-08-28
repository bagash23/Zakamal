package com.example.zakamal.ui.Community.CommunityComment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zakamal.R
import com.example.zakamal.databinding.ActivityCommunityCommentBinding
import com.example.zakamal.model.CommunityItem

class CommunityCommentActivity : AppCompatActivity() {

    lateinit var binding: ActivityCommunityCommentBinding
    lateinit var communityCommentAdapter: CommunityCommentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommunityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val itemList = listOf<CommunityItem>(
            CommunityItem("Community 1", "Community 1 Description", "1"),
            CommunityItem("Community 2", "Community 2 Description", "2"),
            CommunityItem("Community 3", "Community 3 Description", "3"),
        )

        communityCommentAdapter = CommunityCommentAdapter(itemList)
        binding.rvChat.adapter = communityCommentAdapter
        binding.rvChat.layoutManager = LinearLayoutManager(this)

        binding.ivBackComment.setOnClickListener {
            onBackPressed()
        }
    }
}