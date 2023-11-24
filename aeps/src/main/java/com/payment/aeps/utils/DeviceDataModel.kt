package com.payment.aeps.utils

class DeviceDataModel {
    var errCode: String? = null
    var errMsg: String? = null
    var fingerData: String? = null
    var dc: String? = null
    var dpId: String? = null
    var mc: String? = null
    var mi: String? = null
    var rdsId: String? = null
    var rdsVer: String? = null
    var srno: String? = null
    var sysid: String? = null
    var ts: String? = null
    var hmac: String? = null
    var ci: String? = null
    var skey: String? = null
    var nmPoints: String? = null

    override fun toString(): String {
        return """
               DeviceDataModel{errCode='$errCode'
               , errMsg='$errMsg'
               , fingerData='$fingerData'
               , dc='$dc'
               , dpId='$dpId'
               , mc='$mc'
               , mi='$mi'
               , rdsId='$rdsId'
               , rdsVer='$rdsVer'
               , srno='$srno'
               , sysid='$sysid'
               , ts='$ts'
               , Hmac='${hmac}'
               , ci='$ci'
               , Skey='${skey}'
               , nmPoints='$nmPoints'}
               """.trimIndent()
    }
}
