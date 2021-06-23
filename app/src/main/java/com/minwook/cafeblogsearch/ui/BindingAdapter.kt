package com.minwook.cafeblogsearch.ui

import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.minwook.cafeblogsearch.R
import org.joda.time.DateTime

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("thumbnailImage")
    fun bindThumbnailImage(view: ImageView, url: String) {
        Glide.with(view.context)
            .load(url)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("dateTime")
    fun bindDateTime(view: TextView, date: String) {
        view.text = DateTime(date).toString("yyyy년 MM월 dd일")
    }

    @JvmStatic
    @BindingAdapter("dateTimeHHmm")
    fun bindDateTimeHHmm(view: TextView, date: String) {
        view.text = DateTime(date).toString("yyyy년 MM월 dd일 aa hh시 mm분")
    }

    @JvmStatic
    @BindingAdapter("label")
    fun bindLabel(view: TextView, label: String) {
        view.text = label

        if (label == "blog") {
            view.setTextColor(ContextCompat.getColor(view.context, R.color.c_f15f5f))
        } else {
            view.setTextColor(ContextCompat.getColor(view.context, R.color.c_6799ff))
        }
    }

    @JvmStatic
    @BindingAdapter("htmlText")
    fun bindHtmlText(view: TextView, text: String) {
        view.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}