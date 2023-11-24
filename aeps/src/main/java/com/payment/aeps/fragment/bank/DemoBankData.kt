package com.payment.aeps.fragment.bank

import com.payment.aeps.R


data class BankData(
    val bankName: String,
    val bankImage:Int
)

object DemoBankData {

    val reports by lazy {
        mutableListOf(
            BankData(
                "Aditya Birla Idea Payments Bank Ltd",
                R.mipmap.ic_finance_icon,
            ),
            BankData(
                "Airtel Payments Bank Ltd",
                R.mipmap.ic_finance_icon,
            ),
            BankData(
                "Axis Bank Ltd",
                R.mipmap.ic_finance_icon,
            ),
            BankData(
                "Bank of Baroda",
                R.mipmap.ic_finance_icon,
            ),
            BankData(
                "Bank of India",
                R.mipmap.ic_finance_icon,
            ),
            BankData(
                "Bank of Maharashtra",
                R.mipmap.ic_finance_icon,
            ),
            BankData(
                "Baroda Gujarat Gramin Bank",
                R.mipmap.ic_finance_icon,
            ),
            BankData(
                "Central Bank of India",
                R.mipmap.ic_finance_icon,
            ),
            BankData(
                "Fino Payments Bank Ltd",
                R.mipmap.ic_finance_icon,
            ),
            BankData(
                "HDFC Bank Ltd",
                R.mipmap.ic_finance_icon,
            ),
            BankData(
                "ICICI Bank Ltd",
                R.mipmap.ic_finance_icon,
            ),
            BankData(
                "IDFC FIRST Bank Limited",
                R.mipmap.ic_finance_icon,
            ),
            BankData(
                "India Post Payments Bank Ltd",
                R.mipmap.ic_finance_icon,
            ),
            BankData(
                "IndusInd Bank Ltd",
                R.mipmap.ic_finance_icon,
            ),
            BankData(
                "Jio Payments Bank Ltd",
                R.mipmap.ic_finance_icon,
            ),
            BankData(
                "Kashi Gomti Samyut Gramin Bank",
                R.mipmap.ic_finance_icon,
            ),
            BankData(
                "Kotak Mahindra Bank Ltd",
                R.mipmap.ic_finance_icon,
            ),
            BankData(
                "South Indian Bank Ltd",
                R.mipmap.ic_finance_icon,
            ),
            BankData(
                "State Bank of India",
                R.mipmap.ic_finance_icon,
            ),

        )


    }



}