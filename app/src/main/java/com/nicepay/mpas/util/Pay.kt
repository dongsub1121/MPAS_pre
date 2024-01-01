package com.nicepay.mpas.util

object Pay {

    enum class Service{
        ALI,
        WECHAT,
        LIQUID,
        KAKAO,
        NAVER,
        PAYCO,
        PAYPRO
    }

    enum class Status{
        AUTH,
        CANCEL,
        REFUND,
        PARTIAL_REFUND,
        INQUIRY
    }

    enum class CpmType{
        BARCODE,
        QR,
        KEY_IN
    }

    //PAYPRO
    private const val JOB_PAYPRO_AUTH: String = "8066"
    private const val JOB_PAYPRO_REFUND: String = "8067"
    private const val JOB_PAYPRO_INQUIRY: String = "8068"
    private const val JOB_PAYPRO_CANCEL: String = "8069"

    fun getJobCode(service: Service, status: Status): String {
        return when (service) {
            Service.PAYPRO -> {
                when (status) {
                    Status.AUTH -> JOB_PAYPRO_AUTH
                    Status.CANCEL -> JOB_PAYPRO_CANCEL
                    Status.REFUND -> JOB_PAYPRO_REFUND
                    Status.INQUIRY -> JOB_PAYPRO_INQUIRY
                    Status.PARTIAL_REFUND -> JOB_PAYPRO_REFUND
                }
            }
            else -> throw IllegalArgumentException("${service.name} is not defined")
        }
    }

}