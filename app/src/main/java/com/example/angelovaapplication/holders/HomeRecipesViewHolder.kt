package com.example.angelovaapplication.holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.angelovaapplication.R
import com.example.angelovaapplication.listeners.OnItemListener

class HomeRecipesViewHolder(itemView: View, onItemListener: OnItemListener) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private val tvTitle: TextView
    private val ivBackground: ImageView
    private val bookmarkImageView: ImageView
    private var onItemListener: OnItemListener

    init {
        tvTitle = itemView.findViewById(R.id.titleTextView)
        ivBackground = itemView.findViewById(R.id.backgroundImageView)
        bookmarkImageView = itemView.findViewById(R.id.bookmarkImageView)
        this.onItemListener = onItemListener
        itemView.setOnClickListener(this)
    }

    fun setTvTitle(tvTitle: String?) {
        this.tvTitle.text = tvTitle
    }

    fun setIvBackground(resourceId: Int) {
        this.ivBackground.setImageResource(resourceId)
    }

    fun setBookmarkImage(resourceId: Int) {
        this.bookmarkImageView.setImageResource(resourceId)
    }



    override fun onClick(v: View) {
        onItemListener.onItemClick(v, adapterPosition)
    }
}