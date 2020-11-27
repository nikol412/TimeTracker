package com.example.timetracker.ui.fragment.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.timetracker.R

class ItemsAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mItems: List<String> = mutableListOf()

    fun setItems(items: List<String>) {
        mItems = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.home_item_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemViewHolder -> {
                holder.onBind()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return 1
    }

    override fun getItemCount(): Int = mItems.size
}

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val itemName = itemView.findViewById<TextView>(R.id.task_name)
    private val itemCategory = itemView.findViewById<TextView>(R.id.task_category)
    private val itemDate = itemView.findViewById<TextView>(R.id.task_date)

    fun onBind() {
        itemName.text = "test name"
        itemCategory.text = "test category"
        itemDate.text = "21 Nov"

    }
}