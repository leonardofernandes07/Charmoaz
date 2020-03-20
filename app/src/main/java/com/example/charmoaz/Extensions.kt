package com.example.charmoaz

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.ParseException
import java.util.*

fun <T> MutableLiveData<T>.asImmutable(): LiveData<T> = this

@Throws(ParseException::class)
private fun formatStringToNumber(number: String): Number {
    val brazilianFormat = NumberFormat.getInstance(Locale("pt", "br"))
    return brazilianFormat.parse(number.trim()) ?: 0
}

fun String.formatIntBrazil(): Int {
    return try {
        val parsed = formatStringToNumber(this)
        parsed.toInt()
    } catch (e: ParseException) {
        0
    }
}
