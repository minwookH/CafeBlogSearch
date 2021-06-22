package com.minwook.cafeblogsearch.ui.main.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.minwook.cafeblogsearch.databinding.ListItemHeaderBinding

class HeaderViewHolder(private var bind: ListItemHeaderBinding) : RecyclerView.ViewHolder(bind.root) {

    //var onClick: ((Ticker) -> Unit)? = null

    fun bind() {
        /*bind.ticker = ticker
        bind.clCoinContent.setOnClickListener { onClick?.invoke(ticker) }*/
    }
}