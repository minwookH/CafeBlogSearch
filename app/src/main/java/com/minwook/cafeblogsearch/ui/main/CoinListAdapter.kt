package com.minwook.cafeblogsearch.ui.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.minwook.cafeblogsearch.data.SearchItem
import com.minwook.cafeblogsearch.databinding.ListItemHeaderBinding
import com.minwook.cafeblogsearch.databinding.ListItemSearchBinding
import com.minwook.cafeblogsearch.ui.main.viewholder.HeaderViewHolder
import com.minwook.cafeblogsearch.ui.main.viewholder.SearchViewHolder

class CoinListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
        //(holder as CoinViewHolder).bind(list[position])
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun getList(): ArrayList<Any> = list

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }
}