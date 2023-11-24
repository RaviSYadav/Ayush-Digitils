package com.payment.ayushdigitils.ui.fragments.dashboard

import com.payment.ayushdigitils.R


object DemoData {

    val section1 by lazy {
        listOf(
            Section(
                1,
                slug = "1",
                title = "Finance",
                subTitle = "Loan",
                imageUrl = R.drawable.ic_finance_icon,
                dashboardList = finance
            ),
            Section(
                2,
                slug = "2",
                title = "Recharge & Pay Bills",
                subTitle = "Recharge & Pay Bills",
                dashboardList = recharge,
                imageUrl = R.drawable.ic_recharge,
            ),
            Section(
                3,
                slug = "3",
                title = "Tour & Travels",
                subTitle = "Sub Title / Selected Display Language",
                dashboardList = travels,
                imageUrl = R.drawable.ic_tours_travels,
            ),
            Section(
                11,
                slug = "3",
                title = "Special Services",
                subTitle = "Sub Title / Selected Display Language",
                dashboardList = special_services,
                imageUrl = R.drawable.ic_special,
            )

        )


    }




    val finance = listOf(
        Dashboard(
            "aeps",
            R.drawable.ic_fingerprint,
            "Aeps"
        ),

        Dashboard(
            "matm",
            R.drawable.ic_matm,
            "MATM"
        ),
        Dashboard(
            "upi",
            R.drawable.ic_upi,
            "UPI"
        ),
        Dashboard(
            "dmt",
            R.drawable.ic_dmt,
            "DMT"
        ),

    )
    val recharge = listOf(
        Dashboard(
            "prepaid",
            R.drawable.ic_prepaid,
            "Prepaid"
        ),
        Dashboard(
            "dth",
            R.drawable.ic_dth,
            "DTH"
        ),
        Dashboard(
            "postpaid",
            R.drawable.ic_postpaid,
            "Postpaid"
        ),
        Dashboard(
            "electricity",
            R.drawable.ic_electricity,
            "Electricity"
        ),
        Dashboard(
            "water",
            R.drawable.ic_water,
            "Water"
        ),
        Dashboard(
            "broadband",
            R.drawable.ic_broadband,
            "Broadband"
        ),
        Dashboard(
            "rent_payment",
            R.drawable.ic_rent_payment,
            "Rent Payment"
        ),
        Dashboard(
            "loanrepay",
            R.drawable.ic_loan,
            "Loan Payment"
        ),
        Dashboard(
            "schoolfees",
            R.drawable.ic_education,
            "Education"
        ),
        Dashboard(
            "lpg",
            R.drawable.ic_cylinder,
            "LPG Gas"
        ),
        Dashboard(
            "gas",
            R.drawable.ic_piped_gas,
            "Piped Gas"
        )
    )
    val travels = listOf(
        Dashboard(
            "flight",
            R.drawable.ic_flight,
            "Flight Ticket"
        ),
        Dashboard(
            "travels",
            R.drawable.ic_travels,
            "Tour & Travels"
        ),
        Dashboard(
            "fasttag",
            R.drawable.ic_fast_tag,
            "Fast Tag"
        ),
        Dashboard(
            "train",
            R.drawable.ic_train,
            "Train Ticket"
        ),
        /*Dashboard(
            "online_hopping",
            R.drawable.ic_online_hopping,
            "Online Shopping"
        ),*/
        Dashboard(
            "bus",
            R.drawable.ic_bus,
            "Bus Travel"
        ),
        Dashboard(
            "domestic_travel",
            R.drawable.ic_domestic_travel,
            "Domestic Travel"
        ),
        Dashboard(
            "international_ravel",
            R.drawable.ic_international_ravel,
            "International Travel"
        ),
        Dashboard(
            "shopping",
            R.drawable.ic_shopping,
            "Shopping"
        ),
        Dashboard(
            "loanrepay",
            R.drawable.ic_loan,
            "Loan"
        ),
        Dashboard(
            "insurance",
            R.drawable.ic_car_insurance,
            "Car Insurance"
        ),
        Dashboard(
            "credit_card",
            R.drawable.ic_credit_card,
            "Credit Card"
        ),
        Dashboard(
            "lifeinsurance",
            R.drawable.ic_health_plus,
            "Health +"
        ),
        Dashboard(
            "muncipal",
            R.drawable.ic_muncipal,
            "Muncipal"
        )

        )
    private val special_services = listOf(

        Dashboard(
            "https://login.ayusdigital.co.in/open-account/open-account/list",
            R.drawable.ic_icici_bank,
            "ICICI"
        ),
        Dashboard(
            "https://login.ayusdigital.co.in/aadhar/create",
            R.drawable.ic_pan_card,
            "Create Aadhaar"
        ),
        Dashboard(
            "https://login.ayusdigital.co.in/aadhar/update",
            R.drawable.ic_pan_card,
            "Update Aadhaar"
        ),
    )

}