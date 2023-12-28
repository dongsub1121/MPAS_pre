package com.nicepay.mpas.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Utils {

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDate6(): String {
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyMMdd")
        return currentDateTime.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDate12(): String {
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyMMddHHmmSS")
        return currentDateTime.format(formatter)
    }

    fun getTime6(): String{
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("HHmmSS")
        return currentDateTime.format(formatter)
    }
    @JvmStatic
    fun byteArrayToString(byteArray: ByteArray): String {
        return String(byteArray)
    }
}