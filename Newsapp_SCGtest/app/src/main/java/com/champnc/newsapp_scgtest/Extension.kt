package com.champnc.newsapp_scgtest

import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

fun String.dateFromISOtoSimpleUTC(): String {
    return Instant.parse(this)
        .atOffset(ZoneOffset.UTC)
        .format(DateTimeFormatter.ofPattern("EEE dd'th' MMM, hh:mm", Locale.ENGLISH))
}