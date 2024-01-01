package com.nicepay.mpas.model

import android.util.Log
import com.nicepay.mpas.util.Pay

class Order {

    private var goods: ArrayList<Goods> = ArrayList()
    var paymentData = PaymentData()


    var price: Int = 0
        set(value) {
            field = value
            Log.e("Order_Price", field.toString())
        }

    var goodsName: String = ""
        get() {
            if (goods.size == 1) {
                return goods[0].name
            } else if ( goods.size > 1) {
                return goods[0].name + " 외 ${goods.size-1} 건"
            }

            return field
        }

    fun setInitialize(service: Pay.Service, status: Pay.Status) {
        paymentData.service = service
        paymentData.status = status
    }

    fun setWallet(service: Pay.Service) {
        paymentData.service = service
    }

    fun setStatus(status: Pay.Status) {
        paymentData.status = status
    }

    fun setBarcode(barcode: String) {
        paymentData.barcode = barcode
    }

    fun setPrice(amount: Int) {
        paymentData.totPrice = amount
    }

    private fun createItemName() {
        goods.let {
            if (it.size == 1) {
                goodsName = goods[0].name
            } else if ( goods.size > 1) {
                goodsName = goods[0].name + " 외 ${goods.size-1} 건"
            }
        }
    }

    private fun calculateTotalPrice() {
        goods.let {
            price = it.sumOf { goodsItem -> goodsItem.price * goodsItem.quantity }
        }
    }

    fun putGood(goods: Goods) {
        this.goods.add(goods)
        calculateTotalPrice()
        createItemName()
    }

    override fun toString(): String {
        val sb1 = StringBuilder()
        for (good in goods){
            sb1.append(good.toString())
        }

        val sb = StringBuilder()
        sb.append("price = $price")
        sb.append("goodsName = $goodsName")
        sb.append("goods = $sb1")

        return sb.toString()
    }
}
