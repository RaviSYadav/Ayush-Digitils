package com.payment.ayushdigitils.ui.fragments.prepaid.provider

import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.Typed2EpoxyController
import com.payment.ayushdigitils.data.remote.NetworkPrepaidProvidersData
import com.payment.ayushdigitils.ui.fragments.operator.BillOperatorCarouselModel_


class PrepaidProviderController(private val callbacks: AdapterCallbacks) : Typed2EpoxyController<List<NetworkPrepaidProvidersData>,String>(){
    override fun buildModels(data: List<NetworkPrepaidProvidersData>?,type: String?) {



        val trending_ca = data?.map {
            PrepaidProviderModel_()
                .id(it.id)
                .operatorItem(it)
                .type(type)
                .providerClickListener { model, _, _, position ->
                    callbacks.onProviderClicked(model.operatorItem(),position)
                }
        }

        if (trending_ca != null) {
            BillOperatorCarouselModel_()
                .id("operator","id")
                .models(trending_ca)
                .padding(Carousel.Padding.dp(2, 2, 2, 20, 10))
                .addIf(trending_ca.isNotEmpty(),this)
        }


    }
    interface AdapterCallbacks {
        fun onProviderClicked(networkPrepaidProvidersDa: NetworkPrepaidProvidersData, position: Int)
    }



}