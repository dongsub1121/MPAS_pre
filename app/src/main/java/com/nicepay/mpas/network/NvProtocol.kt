package com.nicepay.mpas.network

import android.content.Context
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.InetSocketAddress
import java.net.Socket
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class NvProtocol (var context: Context){

    private val port: Int = 12345
    private val ip: String = "192.168.0.1"

/*    private var socket = Socket()

    var inputStream = socket.getInputStream()
    var outputStream = socket.getOutputStream()
    val reader = BufferedReader(InputStreamReader(inputStream))
    val writer = BufferedWriter(OutputStreamWriter(outputStream))*/

    private val socket = Socket(ip, port)


    fun connect() {


        }
}




