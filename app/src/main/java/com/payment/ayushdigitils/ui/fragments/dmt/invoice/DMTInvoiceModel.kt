package com.payment.ayushdigitils.ui.fragments.dmt.invoice


data class DMTInvoiceModel(
    var key: String = "",
    var value: String = ""
)

data class DMTTransactionInvoiceModel(
    val message: String = "",
    val status: String = "",
    val amount: String = "",
    val orderId: String = "",
    val utr: String = ""
)


