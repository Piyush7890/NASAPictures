package com.piyush.nasapictures.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.jvm.Throws

object DateUtils
{
    @Throws(ParseException::class)
    fun String.toDate(): Date? {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(this)
    }
}