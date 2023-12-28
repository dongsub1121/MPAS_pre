package com.nicepay.mpas.network

import com.nicepay.mpas.model.Order
import com.nicepay.mpas.util.Pay
import java.nio.ByteBuffer

data class Protocol (
    private var stx: Byte = 0x02,
    private var totalLen: ProtocolEntity = ProtocolEntity(4, 0),
    private var identity: Int = 0x1212,
    private var specVersion: ProtocolEntity = ProtocolEntity(3, "PFC"),
    private var dllVersion: ProtocolEntity = ProtocolEntity(8, "1.01.001"),
    private var cancelTransaction: ProtocolEntity = ProtocolEntity(1, 0),
    private var filler: ProtocolEntity = ProtocolEntity(1, ""),
    private var header: ProtocolHeader? = null,
    private var body: ProtocolBody? = null,
    private var etx: Byte = 0x03
        ) {

    fun init(wallet: Pay.Wallet, status: Pay.Status ) {

        ProtocolHeader().also {
            header = it
            header?.setJobCode(wallet, status)
        }
        setBody(wallet).also { body = it }
    }

    fun authorization(order: Order) {

    }

    fun setJobCode(wallet: Pay.Wallet, status: Pay.Status) {
        header?.setJobCode(wallet, status)
    }

    fun setPrice(price: Int) {
        body?.setPrice(price)
    }

    fun setBarcode(code: String) {
        body?.setBarcode(code)
    }

    fun setOrder(order: Order) {
        setPrice(order.price)
    }

    fun getAuthData(): ByteArray {
        setLen()

        val buffer = ByteBuffer.allocateDirect(2000)

        buffer.put(stx)
        buffer.put(totalLen.byte)
        buffer.put(identity.toByte())
        buffer.put(specVersion.byte)
        buffer.put(dllVersion.byte)
        buffer.put(cancelTransaction.byte)
        buffer.put(filler.byte)
        header?.toByteData()?.let { buffer.put(it) }
        body?.toByteData()?.let { buffer.put(it) }
        buffer.put(filler.byte)

        return ByteArray(buffer.capacity())//buffer.rewind().array() as ByteArray
    }
    val data = fun(): ByteArray{

        setLen()

        val buffer = ByteBuffer.allocateDirect(2000)

        buffer.put(stx)
        buffer.put(totalLen.byte)
        buffer.put(identity.toByte())
        buffer.put(specVersion.byte)
        buffer.put(dllVersion.byte)
        buffer.put(cancelTransaction.byte)
        buffer.put(filler.byte)
        header?.toByteData()?.let { buffer.put(it) }
        body?.toByteData()?.let { buffer.put(it) }
        buffer.put(filler.byte)

        return ByteArray(buffer.capacity())//buffer.rewind().array() as ByteArray
    }

    private fun setLen() {
        var defaultLen = 21

        header?.toByteData()?.let { defaultLen =+ it.size }
        body?.toByteData()?.let { defaultLen =+ it.size }

        totalLen.data = defaultLen
    }

    private fun setBody(pay: Pay.Wallet): ProtocolBody {
        //TODO : edit other wallet
        return when (pay) {
            Pay.Wallet.PAYPRO -> PayProBody()
            Pay.Wallet.ALI -> PayProBody()

            else -> throw IllegalArgumentException("Unknown Wallet")
        }
    }

    fun getPayData(body: ProtocolBody) {

    }

    private fun setAuthenticationType(status: Pay.Status) {
        //header?.setJobCode()
    }

}


