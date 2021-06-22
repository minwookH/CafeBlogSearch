package com.minwook.cafeblogsearch.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchItem(
    var title: String,
    var contents: String,
    var url: String,
    var name: String,
    var thumbnail: String,
    var datetime: String,
    var label: String,
    var isWebPageChecked: Boolean = false
) : Parcelable