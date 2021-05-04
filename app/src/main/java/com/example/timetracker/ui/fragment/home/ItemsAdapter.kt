package com.example.timetracker.ui.fragment.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.timetracker.R
import com.example.timetracker.data.db.model.Task
import java.text.SimpleDateFormat
import java.util.*

class ItemsAdapter(private val onItemDismiss: OnItemDismiss) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), TaskTouchHelperAdapter {

    private var mItems = mutableListOf<Task>()

    fun setItems(items: List<Task>) {
        mItems = items.toMutableList()
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

    override fun onItemDismiss(position: Int) {
        mItems.removeAt(position)
        notifyItemRemoved(position)
        onItemDismiss.onDismiss(position)
    }
}

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val itemName = itemView.findViewById<TextView>(R.id.task_name)
    private val itemCategory = itemView.findViewById<TextView>(R.id.task_category)
    private val itemDate = itemView.findViewById<TextView>(R.id.task_date)

    fun onBind(task: Task) {
        task.date?.let { date ->
            itemDate.text = getCharacterDate(date)
        }
        itemName.text = task.title
        itemCategory.text = task.description

    }

    private fun getCharacterDate(stringDate: String): String {
        if (stringDate.isBlank()) return ""

        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)

        var finalDate = ""
        sdf.parse(stringDate)?.let { date ->
            calendar.time = date
            val outputFormat = SimpleDateFormat("dd MMM")
            val outputFormatWithYear = SimpleDateFormat("dd MMM yyyy")
            finalDate = if (calendar.get(Calendar.YEAR) == Calendar.getInstance()
                    .get(Calendar.YEAR)
            ) outputFormat.format(calendar.time) else outputFormatWithYear.format(calendar.time)
        }
        return finalDate
    }

    private fun getCharacterDate(date: Date): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val dateString = sdf.format(date)
        return getCharacterDate(dateString)
    }
}

interface OnItemDismiss {
    fun onDismiss(position: Int)
}