package com.payment.ayushdigitils.ui.fragments.dashboard.section.balance

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.ModelView
import com.payment.ayushdigitils.ui.utils.CyclicAdapter
import com.payment.ayushdigitils.ui.utils.LinearSnapHelperFactory

private const val BALANCE_CAROUSEL_SPAN_COUNT = 1

@ModelView(saveViewState = true, autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class BalanceCarousel(context: Context) : Carousel(context) {

    override fun createLayoutManager(): LayoutManager {
        return GridLayoutManager(
            context,
            BALANCE_CAROUSEL_SPAN_COUNT,
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }


}