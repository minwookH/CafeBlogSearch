package com.minwook.cafeblogsearch.ui.main.viewholder

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.minwook.cafeblogsearch.data.Header
import com.minwook.cafeblogsearch.databinding.ListItemHeaderBinding

class HeaderViewHolder(private var bind: ListItemHeaderBinding) : RecyclerView.ViewHolder(bind.root) {

    var onFillterClick: ((String) -> Unit)? = null
    var onSortClick: ((String) -> Unit)? = null

    @SuppressLint("ClickableViewAccessibility")
    fun bind(data: Header) {
        var isSpinnerClick = false
        val sortArray = bind.btSort.context.resources.getStringArray(data.sortArrayResource)
        var sortSeletedPosition = 0

        ArrayAdapter.createFromResource(
            bind.spFillter.context,
            data.fillterArrayResource,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            bind.spFillter.adapter = adapter
        }

        bind.spFillter.setSelection(data.fillterSelectPosition)
        // Spinner 직접 터치후에만 이벤트 처리 가능하기 위해
        bind.spFillter.setOnTouchListener { v, event ->
            isSpinnerClick = true
            false
        }
        bind.spFillter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (!isSpinnerClick) {
                    return
                }

                if (bind.spFillter.getItemAtPosition(position) is String) {
                    data.fillterSelectPosition = position
                    data.sortSelectPosition = 0
                    onFillterClick?.invoke(bind.spFillter.getItemAtPosition(position) as String)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        bind.btSort.setOnClickListener {
            AlertDialog.Builder(it.context).setSingleChoiceItems(sortArray, data.sortSelectPosition,
                DialogInterface.OnClickListener { dialog, which ->
                    sortSeletedPosition = which
                })
                .setPositiveButton("선택") { dialog, which ->
                    // 기존 정렬 Type과 다른 경우만 정렬 재시도
                    if (data.sortSelectPosition != sortSeletedPosition) {
                        data.sortSelectPosition = sortSeletedPosition
                        onSortClick?.invoke(sortArray[sortSeletedPosition])
                    }
                }
                .setNegativeButton("취소", null).show()
        }
    }
}