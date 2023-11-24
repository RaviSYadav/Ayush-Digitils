package com.payment.ayushdigitils.ui.fragments.dashboard

data class Dashboard(
    val slug: String,
    val imageUrl: Int,
    val title: String
)


data class Section(
    val sectionId: Int,
    val slug: String,
    val title: String,
    val subTitle: String,
    val imageUrl: Int? = null,
    val dashboardList: List<Dashboard> = listOf(),
)
