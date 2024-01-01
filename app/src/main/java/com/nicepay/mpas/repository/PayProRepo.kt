package com.nicepay.mpas.repository

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.nicepay.mpas.model.Order
import com.nicepay.mpas.model.PaymentData
import com.nicepay.mpas.network.Transaction
import com.nicepay.mpas.network.VanClient
import com.nicepay.mpas.util.Pay
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import java.util.concurrent.TimeUnit

//class PayProRepo(transaction: Transaction) {
@RequiresApi(Build.VERSION_CODES.O)
class PayProRepo(order: Order) {

    private val van: VanClient = VanClient()
    private var transaction = Transaction()
    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        transaction.setOrder(order)
    }


    val authorization = runBlocking {
        try {
            withTimeout(30000) {
                van.request(transaction.data())
            }
        } catch (e: TimeoutCancellationException) {
            //TODO : Exception
            e.printStackTrace()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    val inquiry: Single<ByteArray> = Single.defer {
        Single.fromCallable {

            transaction.setStatus(Pay.Status.INQUIRY)
            transaction.data().let { van.request(it) }

        }
    }



    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("CheckResult")
    val resp =
        Observable.interval(2, TimeUnit.SECONDS)
            .takeUntil(Observable.timer(30, TimeUnit.SECONDS))
            .subscribe {
                // 특정 함수를 호출합니다.
                Single.defer {
                    Single.fromCallable { transaction.data().let { van.request(it)}.let { transaction.paymentData(it) } }
            }.filter {
                    it.resCode.equals("UPAY")
                }.map { transaction.setStatus(Pay.Status.INQUIRY)}
                    .subscribe{
                        van.request(transaction.data())
                    }
    }


}


