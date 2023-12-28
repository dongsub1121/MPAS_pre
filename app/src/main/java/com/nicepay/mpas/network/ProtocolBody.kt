package com.nicepay.mpas.network

import android.util.Log
import com.nicepay.mpas.model.Order
import com.nicepay.mpas.model.PaymentData

abstract class ProtocolBody() {

    abstract fun setAmount(value: Int)
    abstract fun setGoods(value: String)
    abstract fun setTax(value: Int)
    abstract fun setNoTax(value: Int)
    abstract fun setTip(value: Int)
    abstract fun setBarcode(value: String)
    abstract fun setBarcodeType(value: String)
    abstract fun setCurrency(value: String)
    abstract fun setAuthorizationDate(value: String)
    abstract fun setAuthorizationNumber(value: String)
    abstract fun toByteData(): ByteArray
    abstract fun refreshOrderNumber()
    abstract fun toPaymentData(res: ByteArray): PaymentData


    val body: ByteArray
        get() {
            return toByteData()
        }

    fun setPrice(value: Int) {
        //TODO(reason: implement settingFragment)
            val amount =  (value / 1.1).toInt()
            val tax = value - amount
            setAmount(amount)
            setTax(tax)
            Log.e("putPrice","amount: $amount , tax: $tax")
        }

    fun setCode(code: String) {
        this.setBarcode(code)
    }

    fun setOrder(order: Order) {
        setPrice(order.price)
    }
}