package com.nicepay.mpas.network

import android.util.Log
import java.io.Serializable

data class ProtocolEntity(private var length: Int, var data: Any){

    val byte: ByteArray
        get() {
            return when (data) {
                is String -> {
                    val byteArray = ByteArray(length)
                    val dataByteArray = (data as String).toByteArray(Charsets.UTF_8)
                    for (i in 0 until length) {
                        byteArray[i] =
                            if (i < dataByteArray.size) dataByteArray[i] else ' '.code.toByte()
                    }
                    byteArray
                }

                is Int -> {
                    val formattedData = "%0${length}d".format(data)
                    formattedData.toByteArray(Charsets.UTF_8)
                }

                else -> throw IllegalArgumentException("Unsupported data type")
            }
        }

    fun getLength(): Int {
        return length
    }
}