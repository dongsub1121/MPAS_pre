package com.nicepay.mpas.network

import android.os.Build
import androidx.annotation.RequiresApi
import com.nicepay.mpas.util.Pay
import com.nicepay.mpas.util.Utils
import java.nio.ByteBuffer

data class ProtocolHeader(

    var jobCode: ProtocolEntity = ProtocolEntity(4,0),
    var tid: ProtocolEntity = ProtocolEntity(10,""),
    var protocolVersion: ProtocolEntity = ProtocolEntity(12,""),
    var swVersion: ProtocolEntity = ProtocolEntity(10,"1000100001"),
    var hwUniqueId: ProtocolEntity = ProtocolEntity(10,"Mpas"),
    var ktc: ProtocolEntity = ProtocolEntity(32,""),
    var sendDate: ProtocolEntity = ProtocolEntity(12,""),
    var filler: ProtocolEntity = ProtocolEntity(5,""),
    var packet: ProtocolEntity = ProtocolEntity(1,"R")
) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun toByteData(): ByteArray {

        setDate()
        setMerchant()

        val buffer = ByteBuffer.allocate(96)
        buffer.put(jobCode.byte)
        buffer.put(tid.byte)
        buffer.put(protocolVersion.byte)
        buffer.put(swVersion.byte)
        buffer.put(hwUniqueId.byte)
        buffer.put(ktc.byte)
        buffer.put(sendDate.byte)
        buffer.put(filler.byte)
        buffer.put(packet.byte)

        return buffer.rewind().array() as ByteArray
    }

    fun setJobCode(service: Pay.Service, status: Pay.Status) {
        Pay.getJobCode(service, status).also { this.jobCode.data = it }
    }

    private fun setMerchant() {

    }

    fun setMerchant(tid: String) {
        tid.also { this.tid.data = it } }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setDate() {
        Utils.getDate12().also { sendDate.data = it }
    }

    fun toHeader(res: ByteArray): ProtocolHeader{
        val point = 20
        val protocolHeader = ProtocolHeader()

        protocolHeader.jobCode.data = res.sliceArray(point+0 until point+4).toString(Charsets.UTF_8)
        protocolHeader.tid.data = res.sliceArray(point+4 until point+14).toString(Charsets.UTF_8)
        protocolHeader.protocolVersion.data = res.sliceArray(point+14 until point+26).toString(Charsets.UTF_8)
        protocolHeader.swVersion.data = res.sliceArray(point+26 until point+36).toString(Charsets.UTF_8)
        protocolHeader.hwUniqueId.data = res.sliceArray(point+36 until point+46).toString(Charsets.UTF_8)
        protocolHeader.ktc.data = res.sliceArray(point+46 until point+78).toString(Charsets.UTF_8)
        protocolHeader.sendDate.data = res.sliceArray(point+78 until point+90).toString(Charsets.UTF_8)
        protocolHeader.filler.data = res.sliceArray(point+90 until point+95).toString(Charsets.UTF_8)
        protocolHeader.packet.data = res.sliceArray(point+95 until point+96).toString(Charsets.UTF_8)

        return protocolHeader
    }
}

