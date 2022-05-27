package com.champnc.newsapp_scgtest.model


import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Source(
    var id: String?,
    var name: String?
) : Parcelable