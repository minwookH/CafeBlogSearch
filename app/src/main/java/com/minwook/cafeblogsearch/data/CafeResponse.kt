package com.minwook.cafeblogsearch.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class CafeResponse(
    var meta: Meta,
    var documents: ArrayList<Cafe>
)

@Parcelize
data class Cafe(
    var title: String,
    var contents: String,
    var url: String,
    var cafename: String,
    var thumbnail: String,
    var datetime: String
) : Parcelable