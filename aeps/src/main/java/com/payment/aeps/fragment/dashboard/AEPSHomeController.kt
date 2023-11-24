package com.payment.aeps.fragment.dashboard

import com.airbnb.epoxy.TypedEpoxyController

/**
 * Created by Ravi Yadav <> on 28/08/23.
 * Safepe pvt
 *
 */
class AEPSHomeController(
        private val callbacks: AdapterCallbacks
) : TypedEpoxyController<List<AEPSDashboard>>() {

    override fun buildModels(data: List<AEPSDashboard>?) {

        if (!data.isNullOrEmpty()) {

            data.map { aepsItem ->

                AEPSHomeItemModel_()
                    .id(aepsItem.slug)
                    .aepsItems(aepsItem)
                    .clickListener { model, _, _, position ->
                        val clickedItem = model.aepsItems
                        callbacks.onAepsItemClicked(clickedItem, position)
                    }
                    //.addTo(this)
                    .addIf(data.isNotEmpty(), this)
            }
        }
    }

    interface AdapterCallbacks {
        fun onAepsItemClicked(aepsDashboard: AEPSDashboard, position: Int)
    }
}