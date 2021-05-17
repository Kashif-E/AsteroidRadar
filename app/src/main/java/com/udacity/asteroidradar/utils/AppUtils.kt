package com.udacity.asteroidradar.utils

import com.udacity.asteroidradar.Constants.API_QUERY_DATE_FORMAT
import com.udacity.asteroidradar.Constants.DEFAULT_END_DATE_DAYS
import java.text.SimpleDateFormat
import java.util.*

fun getFormatedStartDate()=   SimpleDateFormat(API_QUERY_DATE_FORMAT, Locale.getDefault()).format(Calendar.getInstance().time)
fun getFormatedEndDate(): String {
    val calendar =Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, DEFAULT_END_DATE_DAYS)
    return SimpleDateFormat(API_QUERY_DATE_FORMAT, Locale.getDefault()).format(calendar.time)
}