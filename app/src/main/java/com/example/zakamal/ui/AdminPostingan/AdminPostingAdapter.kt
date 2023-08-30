package com.example.zakamal.ui.AdminPostingan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zakamal.R
import com.example.zakamal.api.AdminGetCommunity.AdminGetCommunityDetail

class AdminPostinganAdapter(private val dataList: List<AdminGetCommunityDetail>) : RecyclerView.Adapter<AdminPostinganAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_admin_postingan, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val statusTextView: TextView = itemView.findViewById(R.id.tv_status_postingan_admin_card)
        private val titleTextView: TextView = itemView.findViewById(R.id.tv_item_community_title_card)
        private val numberTextView: TextView = itemView.findViewById(R.id.tv_item_community_number_card)

        fun bind(data: AdminGetCommunityDetail) {
            statusTextView.text = data.status
            titleTextView.text = data.judulPost
            numberTextView.text = data.idPostFeed.toString()
        }
    }
}
