package com.payment.ayushdigitils.ui.fragments.operator

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.ModelView

private const val OPERATOR_CAROUSEL_SPAN_COUNT = 3
@ModelView(saveViewState = true, autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class BillOperatorCarousel(context: Context) : Carousel(context) {



    override fun createLayoutManager(): LayoutManager = GridLayoutManager(
        context,
        OPERATOR_CAROUSEL_SPAN_COUNT

    )
}