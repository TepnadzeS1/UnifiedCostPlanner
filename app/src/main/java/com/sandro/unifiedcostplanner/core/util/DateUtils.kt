package com.sandro.unifiedcostplanner.core.util

import java.text.SimpleDateFormat
import java.util.*

fun Long?.toFormattedDate(): String {
    if (this == null) return "Ongoing"
    val sdf = SimpleDateFormat("MMM dd", Locale.getDefault())
    return sdf.format(Date(this))
}