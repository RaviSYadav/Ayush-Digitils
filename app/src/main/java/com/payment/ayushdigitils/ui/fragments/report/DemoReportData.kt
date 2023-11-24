package com.payment.ayushdigitils.ui.fragments.report


data class ReportData(
    val slug: String,
    val paidFor: String,
    val userName: String,
    val time:String
)

object DemoReportData {

    val reports by lazy {
        mutableListOf(
            ReportData(
                "slug_1",
                "Payment mode to",
                "User",
                "12 Aug 2023,10:11 AM",
            ),ReportData(
                "slug_2",
                "Payment mode to",
                "User",
                "12 Aug 2023,10:11 AM",
            ),ReportData(
                "slug_3",
                "Payment mode to",
                "User",
                "12 Aug 2023,10:11 AM",
            ),ReportData(
                "slug_4",
                "Payment mode to",
                "User",
                "12 Aug 2023,10:11 AM",
            ),ReportData(
                "slug_5",
                "Payment mode to",
                "User",
                "12 Aug 2023,10:11 AM",
            ),ReportData(
                "slug_6",
                "Payment mode to",
                "User",
                "12 Aug 2023,10:11 AM",
            ),ReportData(
                "slug_7",
                "Payment mode to",
                "User",
                "12 Aug 2023,10:11 AM",
            ),ReportData(
                "slug_8",
                "Payment mode to",
                "User",
                "12 Aug 2023,10:11 AM",
            ),ReportData(
                "slug_9",
                "Payment mode to",
                "User",
                "12 Aug 2023,10:11 AM",
            ),ReportData(
                "slug_10",
                "Payment mode to",
                "User",
                "12 Aug 2023,10:11 AM",
            ),ReportData(
                "slug_11",
                "Payment mode to",
                "User",
                "12 Aug 2023,10:11 AM",
            ),ReportData(
                "slug_12",
                "Payment mode to",
                "User",
                "12 Aug 2023,10:11 AM",
            ),ReportData(
                "slug_13",
                "Payment mode to",
                "User",
                "12 Aug 2023,10:11 AM",
            ),ReportData(
                "slug_14",
                "Payment mode to",
                "User",
                "12 Aug 2023,10:11 AM",
            ),ReportData(
                "slug_15",
                "Payment mode to",
                "User",
                "12 Aug 2023,10:11 AM",
            ),

        )


    }



}