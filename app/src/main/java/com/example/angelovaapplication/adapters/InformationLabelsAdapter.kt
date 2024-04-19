package com.example.angelovaapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.angelovaapplication.R

class InformationLabelsAdapter(private val labels: List<Pair<String, String>>) :
    RecyclerView.Adapter<InformationLabelsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.information_label_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (key, value) = labels[position]
        holder.keyTextView.text = key
        holder.valueTextView.text = value
    }

    override fun getItemCount(): Int = labels.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val keyTextView: TextView = itemView.findViewById(R.id.keyTextView)
        val valueTextView: TextView = itemView.findViewById(R.id.valueTextView)
    }
}