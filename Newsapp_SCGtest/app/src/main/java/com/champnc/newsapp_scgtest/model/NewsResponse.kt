package com.champnc.newsapp_scgtest.model


import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class NewsResponse(
    var articles: List<Article>?,
    var status: String?,
    var totalResults: Int?
) : Parcelable