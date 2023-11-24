package com.payment.ayushdigitils.ui.fragments.mtam

data class MatmDevice(
    val device_name: String,
    val device_manufacture: Int
)

data class MatmTransactionsType(
    val transaction: String,
    val transactions_type: String
)

val devices by lazy {
    listOf(
        MatmDevice(
            "Device :-AF60S",
            3
        ),
        MatmDevice(
            "Device :- MP-63",
            2
        ),
    )
}

val af_transactions by lazy {

    listOf(
        MatmTransactionsType(
            "Balance Inquiry",
            "ATMBE"
        ),
        MatmTransactionsType(
            "Cash Withdrawal",
            "ATMCW"
        )
    )
}
val mp_transactions by lazy {

    listOf(
        MatmTransactionsType(
            "Balance Inquiry",
            "BE"
        ),
        MatmTransactionsType(
            "Cash Withdrawal",
            "CW"
        )
    )
}


