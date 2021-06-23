package com.minwook.cafeblogsearch.ui.main.viewholder

import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.minwook.cafeblogsearch.data.Header
import com.minwook.cafeblogsearch.databinding.ListItemHeaderBinding

class HeaderViewHolder(private var bind: ListItemHeaderBinding) : RecyclerView.ViewHolder(bind.root) {

    //var onClick: ((Ticker) -> Unit)? = null

    fun bind(data: Header) {
        ArrayAdapter.createFromResource(
            bind.spFillter.context,
            data.arrayResource,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            bind.spFillter.adapter = adapter
        }
    }
}