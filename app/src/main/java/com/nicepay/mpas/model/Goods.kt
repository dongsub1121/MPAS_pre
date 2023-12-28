package com.nicepay.mpas.model

data class Goods(
    var name: String = "",
    var quantity: Int = 0,
    var price: Int = 0
) {
    // Callback function to notify price change
    private var priceChangeCallback: (() -> Unit)? = null

    // Set the callback
    fun setOnPriceChange(callback: () -> Unit) {
        priceChangeCallback = callback
    }

    // Update price and notify the change
    fun updatePrice(newPrice: Int) {
        price = newPrice
        priceChangeCallback?.invoke()
    }
}



