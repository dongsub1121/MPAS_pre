package com.nicepay.mpas.ui.payment

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nicepay.mpas.model.Order
import com.nicepay.mpas.network.Transaction
import com.nicepay.mpas.repository.PayProRepo
import com.nicepay.mpas.util.Pay
import io.reactivex.rxjava3.disposables.CompositeDisposable

class PaymentViewModel : ViewModel(){

    private val compositeDisposable = CompositeDisposable()
    private var order: Order? = null
    private var transaction: Transaction? = null
    private val _barcode =  MutableLiveData<Boolean>()
    private val _amount = MutableLiveData<Boolean>()
    private var _order = MutableLiveData<Boolean>()

    val amount: LiveData<Boolean>
        get() = _amount

    val barcode: LiveData<Boolean>
        get() = _barcode

    init {
        _amount.value  = false
        _barcode.value = false
        _order.value = false
        order = null
    }

    fun initialize() {
        _amount.value  = false
        _barcode.value = false
        _order.value = false
        order = null
    }

/*    @RequiresApi(Build.VERSION_CODES.O)
    fun setTransaction(order: Order) {
        Log.e("viewModel","setTransaction")
        initialize()
        transaction = Transaction(order)

    }*/

    fun setInitOrder(service: Pay.Service, status: Pay.Status) {
        order?.let {
            it.setInitialize(service, status)
            _order.value= true
        } ?: kotlin.run {
            order = Order()
            order!!.setInitialize(service, status)
            _order.value= true
        }
    }

    fun setOrderAmount(amount: Int) {
        order?.let {
            it.price = amount
            _amount.value = true
        } ?: kotlin.run {
            order = Order()
            order!!.price = amount
            _amount.value = true
        }
    }

    fun setOrderWallet(service: Pay.Service) {
        order?.let {
            it.setWallet(service)
            _order.value = true
        } ?: kotlin.run {
            order = Order()
            order!!.setWallet(service)
            order!!.setStatus(Pay.Status.AUTH)
            _order.value = true
        }
    }

    fun setOrderBarcode(barcode: String) {
        order?.let {
            it.setBarcode(barcode)
            _barcode.value = true
        } ?: kotlin.run {
            order = Order()
            order!!.setBarcode(barcode)
            _barcode.value = true
        }
    }


    fun setOrderPrice(amount: Int) {
        order?.let {
            it.setPrice(amount)
            _amount.value = true
        } ?: kotlin.run {
            order = Order()
            order!!.setPrice(amount)
            _amount.value = true
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setOrder(ord: Order) {
        transaction?.setOrder(ord)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setPrice(newAmount: Int) {
        transaction?.setPrice(newAmount).let { _amount.value = true }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setBarcode(newBarcode: String) {
        transaction?.setBarcode(newBarcode).let { _barcode.value = true }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("CheckResult")
    fun authorization() {
        Log.e("viewmodel","authorization")
        val repository = transaction?.let { PayProRepo(it) }



    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}


