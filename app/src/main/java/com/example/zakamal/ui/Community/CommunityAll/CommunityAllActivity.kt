package com.example.zakamal.ui.Community.CommunityAll

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zakamal.R
import com.example.zakamal.databinding.ActivityCommunityAllBinding
import com.example.zakamal.model.CommunityItem

class CommunityAllActivity : AppCompatActivity() {
    lateinit var binding: ActivityCommunityAllBinding
    lateinit var communityAdapter: CommunityAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommunityAllBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val itemList = listOf<CommunityItem>(
            CommunityItem("Community 1", "Community 1 Description", "1"),
            CommunityItem("Community 2", "Community 2 Description", "2"),
            CommunityItem("Community 3", "Community 3 Description", "3"),
        )

        communityAdapter = CommunityAdapter(itemList)
        binding.rvCommunityAll.adapter = communityAdapter
        binding.rvCommunityAll.layoutManager = LinearLayoutManager(this)

    }
}