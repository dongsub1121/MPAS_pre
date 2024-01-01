package com.nicepay.mpas.network

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.nicepay.mpas.model.Order
import com.nicepay.mpas.model.PaymentData
import com.nicepay.mpas.util.Pay
import com.nicepay.mpas.util.Pay.Service
import com.nicepay.mpas.util.Pay.Status
import java.nio.ByteBuffer

@RequiresApi(Build.VERSION_CODES.O)
data class Transaction (private var stx: Byte = 0x02,
                        private var totalLen: ProtocolEntity = ProtocolEntity(4, 0),
                        private var identity: Int = 0x1212,
                        private var specVersion: ProtocolEntity = ProtocolEntity(3, "PFC"),
                        private var dllVersion: ProtocolEntity = ProtocolEntity(8, "1.01.001"),
                        private var cancelTransaction: ProtocolEntity = ProtocolEntity(1, 0),
                        private var filler: ProtocolEntity = ProtocolEntity(1, ' '),
                        private var header: ProtocolHeader = ProtocolHeader(),
                        private var body: ProtocolBody?,
                        private var etx: Byte = 0x03) {

    private lateinit var service: Service
    private  lateinit var status: Status


    fun setOrder(order: Order) {
        body?.setOrder(order)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun initialize(w: Service, s: Status) {

        service = w
        status = s
        header.setJobCode(w, s)
        body = setBody()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun setBody(): ProtocolBody {
        //TODO : edit other Service
        val protocolBody: ProtocolBody

        when (service) {
            Service.PAYPRO -> protocolBody = when( status) {
                    Status.AUTH -> PayProBody()
                    Status.REFUND -> PayProBody()
                    Status.INQUIRY -> PayProBody()
                    else -> throw IllegalArgumentException("Unknown Status")
                }

            else -> throw IllegalArgumentException("Unknown Wallet")
        }

        return protocolBody
    }

    fun setStatus(status: Status) {

        service.let { header.setJobCode(it, status) }
    }

    fun setPrice(price: Int) {
        body?.setPrice(price)
    }

    fun setBarcode(code: String) {
        body?.setBarcode(code)
    }

    fun data(): ByteArray {

        val headData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            header.toByteData()
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        val bodyData = body?.toByteData()

        totalLen.data = 21+96+ bodyData!!.size

        val buffer = ByteBuffer.allocateDirect(totalLen.data as Int)

        buffer.put(stx)
        buffer.put(totalLen.byte)
        buffer.put(identity.toByte())
        buffer.put(specVersion.byte)
        buffer.put(dllVersion.byte)
        buffer.put(cancelTransaction.byte)
        buffer.put(filler.byte)
        headData.let { buffer.put(it) }
        bodyData.let { buffer.put(it) }
        buffer.put(filler.byte)
        buffer.put(etx)

        Log.e("transaction",buffer.toString())
        return buffer.rewind().array() as ByteArray
    }

    fun paymentData(res: ByteArray): PaymentData {
       return if (body?.toPaymentData(res) != null) body!!.toPaymentData(res)
            else throw NullPointerException("Expression 'body?.toPaymentData(res)' must not be null")
    }
}