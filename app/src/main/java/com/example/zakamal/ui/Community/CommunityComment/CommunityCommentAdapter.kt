package com.example.zakamal.ui.Community.CommunityComment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.zakamal.R
import com.example.zakamal.model.CommunityItem
import de.hdodenhof.circleimageview.CircleImageView

class CommunityCommentAdapter(private val itemList: List<CommunityItem>) : RecyclerView.Adapter<CommunityCommentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_other, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        // Bind the data to the views in the item layout
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Declare and initialize the views in the item layout
        private val textViewTitle: TextView = itemView.findViewById(R.id.tv_item_comment_other)
        private val other: CircleImageView = itemView.findViewById(R.id.iv_comment_other)

        fun bind(item: CommunityItem) {
            // Set the data to the views in the item layout
            textViewTitle.text = item.communityTitle
            Glide.with(itemView.context)
                .load(R.drawable.ic_profile_active)
                .into(other)
        }
    }
}