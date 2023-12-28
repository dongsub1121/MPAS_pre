package com.nicepay.mpas.network

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class NicePayVan {
    private val van = VanClient()
/*
    @SuppressLint("CheckResult")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun auth(req: ByteArray): Observable<ByteArray> {
        return Single.defer {
            Single.fromCallable {
                van.connect()
                val response: ByteArray = van.sendAndReceive(req) as ByteArray
                van.close()
                response
            }
                .timeout(30, TimeUnit.SECONDS) // 타임아웃 30초 설정
                .flatMap { response ->
                }
        }
            .repeatWhen { completed -> completed.delay(2, TimeUnit.SECONDS) } // 2초 간격으로 반복
            .singleOrError()
            .subscribeOn(Schedulers.io())
        }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun inquiry(req: ByteArray): Observable<ByteArray> {
        return Single.defer {
            Single.fromCallable {

                van.connect()
                val response: ByteArray = van.sendAndReceive(req) as ByteArray
                van.close()
                response
            }
                .timeout(30, TimeUnit.SECONDS) // 타임아웃 30초 설정
                .flatMap { response ->
                }
        }
            .repeatWhen { completed -> completed.delay(2, TimeUnit.SECONDS) } // 2초 간격으로 반복
            .singleOrError()
            .subscribeOn(Schedulers.io())
    }


    fun startNet(protocol: Protocol) : Single<ByteArray> {
        this.protocol = protocol


    }*/
}


