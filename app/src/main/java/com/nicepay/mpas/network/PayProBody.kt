package com.nicepay.mpas.network

import android.os.Build
import androidx.annotation.RequiresApi
import com.nicepay.mpas.model.PaymentData
import com.nicepay.mpas.util.Utils
import java.nio.ByteBuffer
import java.util.Random

@RequiresApi(Build.VERSION_CODES.O)
open class PayProBody : ProtocolBody() {

    private var barcodeType = ProtocolEntity(1,"Q")
    private var barcode = ProtocolEntity(30,"")
    private var amount = ProtocolEntity(12,0)
    private var tax = ProtocolEntity(12,0)
    private var tip = ProtocolEntity(12,0)
    private var pay = ProtocolEntity(3,"") // "WXP": 위챗, "ALP": 알리페이, "LI Q"리퀴드페이
    private var catType = ProtocolEntity(1,"P")
    private var currency = ProtocolEntity(3,"KRW")
    private var orderNumber = ProtocolEntity(12,"")
    private var noTax = ProtocolEntity(12,0)
    private var exchangeRate = ProtocolEntity(24,"")
    private var secondBusinessNumber = ProtocolEntity(10,"")
    private var authorizationDate = ProtocolEntity(6,"")
    private var authorizationNumber = ProtocolEntity(12,"")
    private var cancelType = ProtocolEntity(1,"")
    private var cancelReason = ProtocolEntity(2,"")
    private var goodsItem = ProtocolEntity(256,"")
    private var reserved = ProtocolEntity(512,"")


    init {
        orderNumber.data = setOrderNumber()
    }
    override fun setAmount(value: Int) {
        amount.data = value
    }

    override fun setGoods(value: String) {
        goodsItem.data = value
    }

    override fun setTax(value: Int) {
        tax.data = value
    }

    override fun setNoTax(value: Int) {
        noTax.data = value
    }

    override fun setTip(value: Int) {
        tip.data = value
    }

    override fun setBarcode(value: String) {
        barcode.data = value
    }

    override fun setBarcodeType(value: String) {
        barcodeType.data = value
    }

    override fun setCurrency(value: String) {
        currency.data = value
    }

    override fun setAuthorizationDate(value: String) {
        authorizationDate.data = value
    }

    override fun setAuthorizationNumber(value: String) {
        authorizationNumber.data = value
    }

    override fun toByteData(): ByteArray {

        val buffer = ByteBuffer.allocate(921)
        buffer.put(barcodeType.byte)
        buffer.put(barcode.byte)
        buffer.put(amount.byte)
        buffer.put(tax.byte)
        buffer.put(tip.byte)
        buffer.put(pay.byte)
        buffer.put(catType.byte)
        buffer.put(currency.byte)
        buffer.put(orderNumber.byte)
        buffer.put(noTax.byte)
        buffer.put(exchangeRate.byte)
        buffer.put(secondBusinessNumber.byte)
        buffer.put(authorizationDate.byte)
        buffer.put(authorizationNumber.byte)
        buffer.put(cancelType.byte)
        buffer.put(cancelReason.byte)
        buffer.put(goodsItem.byte)
        buffer.put(reserved.byte)
        buffer.put(0x0d)

        return buffer.rewind().array() as ByteArray
    }

    override fun refreshOrderNumber() {
        orderNumber.data = setOrderNumber()
    }

    override fun toPaymentData(res: ByteArray): PaymentData {
        val point = 116
        val paymentData = PaymentData()

        paymentData.resCode =
            res.sliceArray(point+0 until point+4).toString(Charsets.UTF_8)
        paymentData.msg =
            res.sliceArray(point+4 until point+40).toString(Charsets.UTF_8)
        paymentData.authDate =
            res.sliceArray(point+4 until point+16).toString(Charsets.UTF_8)
        paymentData.authNum =
            res.sliceArray(point+16 until point+28).toString(Charsets.UTF_8)
        paymentData.authOrderNumber =
            res.sliceArray(point+28 until point+40).toString(Charsets.UTF_8)
        paymentData.totPrice =
            res.sliceArray(point+52 until point+64).toString(Charsets.UTF_8).toInt()
        paymentData.issuerCode = res.sliceArray(point+148 until point+151).toString(Charsets.UTF_8)

        paymentData.issuer = when(paymentData.issuerCode){
            "WXP" -> { "WeChat" }
            "ALP" -> { "AliPay" }
            "LIQ" -> { "Liquid" }
            else -> {
                ({}).toString()
            }

        }

        paymentData.orderNumber = res.sliceArray(point+136 until point+148).toString(Charsets.UTF_8)

        return paymentData
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setOrderNumber() {
        val random = Random(System.currentTimeMillis()).nextInt(5)
        orderNumber.data = "M".plus(Utils.getTime6()).plus(random)
    }

}