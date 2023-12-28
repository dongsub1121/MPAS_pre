package com.nicepay.mpas.pay

import android.util.Log
import com.nicepay.mpas.network.Body
import com.nicepay.mpas.network.ProtocolEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import java.nio.ByteBuffer

class PAYPRO: Body() {

    companion object {
        var barcodeType = ProtocolEntity(1,"Q")
        var barcode = ProtocolEntity(30,"")
        var amount = ProtocolEntity(12,0)
        var tax = ProtocolEntity(12,0)
        var tip = ProtocolEntity(12,0)
        var pay = ProtocolEntity(3,"") // "WXP": 위챗, "ALP": 알리페이, "LI Q"리퀴드페이
        var catType = ProtocolEntity(1,"P")
        var currency = ProtocolEntity(3,"KRW")
        var orderNumber = ProtocolEntity(12,"")
        var noTax = ProtocolEntity(12,0)
        var exchangeRate = ProtocolEntity(24,"")
        var secondBusinessNumber = ProtocolEntity(10,"")
        var authorizationDate = ProtocolEntity(6,"")
        var authorizationNumber = ProtocolEntity(12,"")
        var cancelType = ProtocolEntity(1,"")
        var cancelReason = ProtocolEntity(2,"")
        var goodsItem = ProtocolEntity(256,"")
        var reserved = ProtocolEntity(512,"")

    }

    init {
        val barcodeFlow = MutableStateFlow<String?>(barcode.data.toString())
        val ali = "^(25|26|27|28|29|30).{17,24}\$".toRegex()
        val wechat = "^(10|11|12|13|14|15).{18}\$".toRegex()
        val liquid = "^LN.{24}\$".toRegex()

        runBlocking {
            barcodeFlow
                .collect { newValue ->
                    newValue?.let { barcodeValue ->
                        val payValue = when {
                            ali.matches(barcodeValue) -> "ALI"
                            wechat.matches(barcodeValue) -> "WXP"
                            liquid.matches(barcodeValue) -> "LIQ"
                            else -> ""
                        }
                        println("Pay: $payValue")
                        Log.e("PayPro", payValue)
                    }
                }
        }
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

    override fun toByte(): ByteArray {

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
}