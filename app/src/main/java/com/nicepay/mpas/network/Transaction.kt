package com.nicepay.mpas.network

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.nicepay.mpas.model.Order
import com.nicepay.mpas.model.PaymentData
import com.nicepay.mpas.util.Pay.Wallet
import com.nicepay.mpas.util.Pay.Status
import java.nio.ByteBuffer

@RequiresApi(Build.VERSION_CODES.O)
data class Transaction (private var stx: Byte = 0x02,
                        private var totalLen: ProtocolEntity = ProtocolEntity(4, 0),
                        private var identity: Int = 0x1212,
                        private var specVersion: ProtocolEntity = ProtocolEntity(3, "PFC"),
                        private var dllVersion: ProtocolEntity = ProtocolEntity(8, "1.01.001"),
                        private var cancelTransaction: ProtocolEntity = ProtocolEntity(1, 0),
                        private var filler: ProtocolEntity = ProtocolEntity(1, ""),
                        private var header: ProtocolHeader = ProtocolHeader(),
                        private var body: ProtocolBody = PayProBody(),
                        private var etx: Byte = 0x03) {

    private lateinit var wallet: Wallet


    fun setOrder(order: Order) {
        body.setOrder(order)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun initialize(w: Wallet, s: Status) {

        wallet = w
        header.setJobCode(w, s)
        setBody(wallet)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setBody(wallet: Wallet): ProtocolBody {
        //TODO : edit other wallet
        return when (wallet) {
            Wallet.PAYPRO -> PayProBody()
            Wallet.ALI -> PayProBody()

            else -> throw IllegalArgumentException("Unknown Wallet")
        }
    }

    fun setStatus(status: Status) {

        wallet.let { header.setJobCode(it, status) }
    }

    fun setPrice(price: Int) {
        body.setPrice(price)
    }

    fun setBarcode(code: String) {
        body.setBarcode(code)
    }

    fun data(): ByteArray {

        val headData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            header.toByteData()
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        val bodyData = body.toByteData()

        totalLen.data = 21+96+ bodyData.size

        val buffer = ByteBuffer.allocateDirect(totalLen.data as Int)

        buffer.put(stx)
        buffer.put(totalLen.byte)
        buffer.put(identity.toByte())
        buffer.put(specVersion.byte)
        buffer.put(dllVersion.byte)
        buffer.put(cancelTransaction.byte)
        buffer.put(filler.byte)
        headData.let { it -> buffer.put(it) }
        bodyData.let { it-> buffer.put(it) }
        buffer.put(filler.byte)
        buffer.put(etx)

        Log.e("transaction",buffer.toString())
        return buffer.rewind().array() as ByteArray
    }

    fun paymentData(res: ByteArray): PaymentData {
       return body.toPaymentData(res)
    }
}