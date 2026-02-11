package com.example.basicapp.ui.settings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.basicapp.R

class SettingsAdapter(
    private val items: List<Triple<String, Int, Int>>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<SettingsAdapter.SettingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_setting, parent, false)
        return SettingViewHolder(view)
    }

    override fun onBindViewHolder(holder: SettingViewHolder, position: Int) {
        val (title, icon, id) = items[position]
        holder.bind(title, icon, id)
    }

    override fun getItemCount(): Int = items.size

    inner class SettingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val iconView: ImageView = itemView.findViewById(R.id.settingIcon)
        private val titleView: TextView = itemView.findViewById(R.id.settingTitle)

        fun bind(title: String, icon: Int, id: Int) {
            iconView.setImageResource(icon)
            titleView.text = title

            itemView.setOnClickListener { onItemClick(id) }
        }
    }
}
