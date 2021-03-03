package com.example.timetracker.ui.fragment.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.timetracker.R
import com.example.timetracker.data.db.model.Task

class ItemsAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mItems: List<Task> = mutableListOf()

    fun setItems(items: List<Task>) {
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
                holder.onBind(mItems[position])
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

    fun onBind(task: Task) {
        itemName.text = task.title
        itemCategory.text = task.description
        itemDate.text = task.date

    }
}