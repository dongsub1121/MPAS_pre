package com.nicepay.mpas.protocol

import com.nicepay.mpas.model.Order
import com.nicepay.mpas.network.PayProBody
import com.nicepay.mpas.network.ProtocolBody
import com.nicepay.mpas.network.ProtocolEntity
import com.nicepay.mpas.network.ProtocolHeader
import com.nicepay.mpas.network.Transaction
import com.nicepay.mpas.pay.PAYPRO
import com.nicepay.mpas.util.Pay

class TransactionBuilder(private var stx: Byte = 0x02,
                         private var totalLen: ProtocolEntity = ProtocolEntity(4, 0),
                         private var identity: Int = 0x1212,
                         private var specVersion: ProtocolEntity = ProtocolEntity(3, "PFC"),
                         private var dllVersion: ProtocolEntity = ProtocolEntity(8, "1.01.001"),
                         private var cancelTransaction: ProtocolEntity = ProtocolEntity(1, 0),
                         private var filler: ProtocolEntity = ProtocolEntity(1, ""),
                         private var header: ProtocolHeader = ProtocolHeader(),
                         private var body: ProtocolBody?,
                         private var etx: Byte = 0x03) {

    private lateinit var service: Pay.Service
    private lateinit var status: Pay.Status

    init {
        status?.let {  }
    }

}