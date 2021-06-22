package com.minwook.cafeblogsearch.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class BlogResponse(
    var meta: Meta,
    var documents: ArrayList<Blog>
)

data class Meta(
    @SerializedName("total_count")
    var totalCount: Int,
    @SerializedName("pageable_count")
    var pageableCount: Int,
    @SerializedName("is_end")
    var isEnd: Boolean,
)

@Parcelize
data class Blog(
    var title: String,
    var contents: String,
    var url: String,
    var blogname: String,
    var thumbnail: String,
    var datetime: String
) : Parcelable