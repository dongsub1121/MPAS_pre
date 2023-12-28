package com.nicepay.mpas.network

import android.util.Log
import io.reactivex.rxjava3.core.Single
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.net.InetSocketAddress
import java.net.Socket

class VanClient {

    private val port: Int = 12345
    private val ip: String = "192.168.0.1"


    fun request(request: ByteArray): ByteArray {
        Log.e("res","request")

        val socket = Socket()
        socket.connect(InetSocketAddress(ip, port), 8000)

        val inputStream = socket.getInputStream()
        val outputStream = socket.getOutputStream()
        val reader = BufferedReader(InputStreamReader(inputStream))
        val writer = BufferedWriter(OutputStreamWriter(outputStream))


        outputStream.run {
            write(request)
        }

        val byteArray = inputStream.readAllBytes()

        reader.close()
        writer.close()
        socket.close()

        byteArray.let { Log.e("res", byteArray.toString(Charsets.UTF_8)) }

        return byteArray
    }
}