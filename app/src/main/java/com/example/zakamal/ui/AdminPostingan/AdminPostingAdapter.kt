package com.example.zakamal.ui.AdminPostingan

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zakamal.R
import com.example.zakamal.model.monitoring.AdminPendingResponse

class AdminPostinganAdapter(private val dataList: MutableList<AdminPendingResponse>) : RecyclerView.Adapter<AdminPostinganAdapter.ViewHolder>() {


    private var itemClickListener: ((AdminPendingResponse) -> Unit)? = null

    // Function to set the click listener
    fun setOnItemClickListener(listener: (AdminPendingResponse) -> Unit) {
        itemClickListener = listener
    }

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

        fun bind(data: AdminPendingResponse) {
            statusTextView.text = data.status
            titleTextView.text = data.judul_post
            numberTextView.text = data.biaya.toString()
            itemView.setOnClickListener {
                itemClickListener?.invoke(data)
            }
        }
    }

    // Function to update the adapter's data
    fun updateData(newData: List<AdminPendingResponse>) {
        dataList.clear()
        dataList.addAll(newData)
        notifyDataSetChanged()
    }
}
