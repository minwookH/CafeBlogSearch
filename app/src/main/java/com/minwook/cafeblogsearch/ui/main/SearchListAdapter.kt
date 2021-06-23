package com.minwook.cafeblogsearch.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.minwook.cafeblogsearch.data.Header
import com.minwook.cafeblogsearch.data.SearchItem
import com.minwook.cafeblogsearch.databinding.ListItemHeaderBinding
import com.minwook.cafeblogsearch.databinding.ListItemSearchBinding
import com.minwook.cafeblogsearch.ui.main.viewholder.HeaderViewHolder
import com.minwook.cafeblogsearch.ui.main.viewholder.SearchViewHolder

class SearchListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_HEADER = 1
        const val TYPE_SEARCH_ITEM = 2
    }

    private var list: ArrayList<Any> = arrayListOf()

    var onClickContents: ((SearchItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> {
                val bind = ListItemHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HeaderViewHolder(bind)
            }
            else -> {
                val bind = ListItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                SearchViewHolder(bind)
            }
        }

        /*val bind = ListItemCoinBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoinViewHolder(bind).apply {
            onClick = { coin -> onClickContents?.invoke(coin) }
        }*/
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            TYPE_HEADER -> (holder as HeaderViewHolder).bind(list[position] as Header)
            else -> (holder as SearchViewHolder).bind(list[position] as SearchItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TYPE_HEADER
            else -> TYPE_SEARCH_ITEM
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun addHeader(header: Header) {
        list.add(header)
    }

    fun setSearchList(searchlist: ArrayList<SearchItem>) {
        this.list.addAll(searchlist)
        notifyDataSetChanged()
    }

    fun getList(): ArrayList<Any> = list

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }
}