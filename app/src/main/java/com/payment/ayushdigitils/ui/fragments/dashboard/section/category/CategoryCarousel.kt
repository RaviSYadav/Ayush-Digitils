package com.payment.ayushdigitils.ui.fragments.dashboard.section.category

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.ModelView

private const val CATEGORY_CAROUSEL_SPAN_COUNT = 4

@ModelView(saveViewState = true, autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class CategoryCarousel(context: Context) : Carousel(context) {



    override fun createLayoutManager(): LayoutManager = GridLayoutManager(
        context,
        CATEGORY_CAROUSEL_SPAN_COUNT

    )
}