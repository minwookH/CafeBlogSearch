package com.minwook.cafeblogsearch.ui.main.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.minwook.cafeblogsearch.data.SearchItem
import com.minwook.cafeblogsearch.databinding.ListItemSearchBinding

class SearchViewHolder(private var bind: ListItemSearchBinding) : RecyclerView.ViewHolder(bind.root) {

    var onClick: ((SearchItem) -> Unit)? = null

    fun bind(data: SearchItem) {
        bind.searchItem = data
        bind.clSearchItem.setOnClickListener {
            onClick?.invoke(data)
        }
    }
}
