package com.example.zakamal.ui.Community.CommunityAll

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zakamal.R
import com.example.zakamal.model.CommunityItem

class CommunityAdapter(private val itemList: List<CommunityItem>) : RecyclerView.Adapter<CommunityAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_community_card, parent, false)
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
        private val textViewTitle: TextView = itemView.findViewById(R.id.tv_item_community_title)

        fun bind(item: CommunityItem) {
            // Set the data to the views in the item layout
            textViewTitle.text = item.communityTitle
        }
    }
}
