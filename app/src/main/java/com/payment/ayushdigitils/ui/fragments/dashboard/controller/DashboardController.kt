package com.payment.ayushdigitils.ui.fragments.dashboard.controller


import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.Typed2EpoxyController
import com.payment.ayushdigitils.vo.SectionType
import com.airbnb.epoxy.group
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.ui.fragments.dashboard.Dashboard
import com.payment.ayushdigitils.ui.fragments.dashboard.section.category.CategoryCarouselModel_
import com.payment.ayushdigitils.ui.fragments.dashboard.section.finance.FinanceItemModel_
import com.payment.ayushdigitils.ui.fragments.dashboard.section.finance.moneyTransferCarousel
import com.payment.ayushdigitils.ui.fragments.dashboard.section.header.HeaderItemModel_
import com.payment.ayushdigitils.ui.fragments.dashboard.section.header.financeHeaderItem
import com.payment.ayushdigitils.ui.fragments.dashboard.section.recharge.RechargeItemModel_
import com.payment.ayushdigitils.ui.fragments.dashboard.Section
import com.payment.ayushdigitils.ui.fragments.dashboard.section.Balance
import com.payment.ayushdigitils.ui.fragments.dashboard.section.balance.BalanceCarouselModel_
import com.payment.ayushdigitils.ui.fragments.dashboard.section.balance.BalanceItemModel_
import com.payment.ayushdigitils.ui.fragments.dashboard.section.special.SpecialCarouselModel_
import com.payment.ayushdigitils.ui.fragments.dashboard.section.special.SpecialItemModel
import com.payment.ayushdigitils.ui.fragments.dashboard.section.special.SpecialItemModel_

class DashboardController(private val callbacks: AdapterCallbacks):
    Typed2EpoxyController< List<Balance>,List<Section>>() {

    override fun buildModels(balances: List<Balance>?,sections:List<Section>?) {


        val balanceModels = balances?.mapIndexed { index, balance ->
            BalanceItemModel_()
                .id(balance.type)
                .balance(balance)
                .clickListener { model, _, _, position ->
                    val clickedItem = model.balance
                    callbacks.onBalanceClicked(clickedItem, index)
                }
        }
        if (balanceModels != null) {
            BalanceCarouselModel_()
                .id("banners")
                .padding(Carousel.Padding.dp(16, 16, 16, 25, 10))
                .numViewsToShowOnScreen(1.05F)
                .models(balanceModels)
                .addIf(balanceModels.isNotEmpty(), this)
        }
        sections?.forEachIndexed { index, section ->
            var bottomDP = 16
            bottomDP = if (index != sections.lastIndex) {
                16
            }else{
                16
            }

            when (SectionType.fromInt(section.sectionId)) {
                SectionType.FINANCE -> {
                    group {
                        id("finance","group")
                        layout(R.layout.view_holder_finance_background)



                        financeHeaderItem(section.dashboardList.size > 3){
                            id(section.slug)
                            section(section)
                            clickListener { model, _, _, _ ->
                                val clickedItem = model.section
                            }
                        }

                        val albumModels =section.dashboardList.map {

                            FinanceItemModel_()
                                .id(it.slug)
                                .dashboard(it)
                                .clickListener { model, _, _, _ ->
                                    val clickedItem = model.dashboard
                                    this@DashboardController.callbacks.onFinanceClicked(
                                        clickedItem,
                                        section.slug,
                                        section.title
                                    )
                                }
                        }

                        moneyTransferCarousel {
                            id("channelShows", section.slug)
                            models(albumModels)
                            numViewsToShowOnScreen(2.5f)
                            padding(Carousel.Padding.dp(16, 0, 16, 16, 10))
                        }




                    }

                }
                SectionType.RECHARGE -> {

                    HeaderItemModel_(
                        section.dashboardList.size > 3
                    )
                        .id(section.slug)
                        .section(section)
                        .clickListener { model, _, _, _ ->
                            val clickedItem = model.section
                        }


                        .addTo(this)

                    val rechargeModels = section.dashboardList.map {
                        RechargeItemModel_()
                            .id(it.slug)
                            .dashboard(it)
                            .clickListener { model, _, _, _ ->
                                val clickedItem = model.dashboard
                                callbacks.onRechargeClicked(
                                    clickedItem,
                                    section.slug,
                                    section.title
                                )
                            }
                    }

                    CategoryCarouselModel_()
                        .id("album")
                        .padding(Carousel.Padding.dp(0, 0, 0, 0, 5))
                        .models(rechargeModels)
                        .addIf(rechargeModels.isNotEmpty(),this)






                    //Timber.d("Adding: section album: ${section.slug} :: ${section.title} :: with count ${section.dashboardList.size}")

                }
                SectionType.TRAVELS -> {

                    HeaderItemModel_(
                        section.dashboardList.size > 3
                    )
                        .id(section.slug)
                        .section(section)
                        .clickListener { model, _, _, _ ->
                            val clickedItem = model.section
                        }
                        .addTo(this)

                    val rechargeModels = section.dashboardList.map {
                        RechargeItemModel_()
                            .id(it.slug)
                            .dashboard(it)
                            .clickListener { model, _, _, _ ->
                                val clickedItem = model.dashboard
                                callbacks.onTravelsClicked(
                                    clickedItem,
                                    section.slug,
                                    section.title
                                )
                            }
                    }

                    CategoryCarouselModel_()
                        .id("album")
                        .padding(Carousel.Padding.dp(0, 16, 0, bottomDP, 15))
                        .models(rechargeModels)
                        .addIf(rechargeModels.isNotEmpty(),this)

                    //Timber.d("Adding: section album: ${section.slug} :: ${section.title} :: with count ${section.dashboardList.size}")

                }
                SectionType.SPECIAL -> {

                    HeaderItemModel_(
                        section.dashboardList.size > 3
                    )
                        .id(section.slug)
                        .section(section)
                        .clickListener { model, _, _, _ ->
                            val clickedItem = model.section
                        }
                        .addTo(this)

                    val specialModels = section.dashboardList.map {
                        SpecialItemModel_()
                            .id(it.slug)
                            .dashboard(it)
                            .specialClickListener { model, _, _, position ->
                                val clickedItem = model.dashboard
                                callbacks.onSpecialClicked(
                                    clickedItem,
                                    position
                                )
                            }
                    }

                    SpecialCarouselModel_()
                        .id("special")
                        .padding(Carousel.Padding.dp(16, 16, 16, bottomDP, 15))
                        .models(specialModels)
                        .numViewsToShowOnScreen(2.6F)
                        .addIf(specialModels.isNotEmpty(),this)

                    //Timber.d("Adding: section album: ${section.slug} :: ${section.title} :: with count ${section.dashboardList.size}")

                }
                else -> {
                }
            }
        }

    }
    interface AdapterCallbacks {



        fun onFinanceClicked(
            dashboard: Dashboard,
            sectionSlug: String,
            sectionTitle: String
        )
        fun onRechargeClicked(
            dashboard: Dashboard,
            sectionSlug: String,
            sectionTitle: String
        )
        fun onTravelsClicked(
            dashboard: Dashboard,
            sectionSlug: String,
            sectionTitle: String
        )
        fun onBalanceClicked(
            balance: Balance,
            position: Int
        )
        fun onSpecialClicked(
            dashboard: Dashboard,
            position: Int
        )


    }

}




