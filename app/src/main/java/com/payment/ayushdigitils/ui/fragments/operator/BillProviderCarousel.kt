package com.payment.ayushdigitils.ui.fragments.operator

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.ModelView



@ModelView(saveViewState = true, autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class BillProviderCarousel(context: Context) : Carousel(context) {

    override fun createLayoutManager(): LayoutManager = LinearLayoutManager(
        context,
        LinearLayoutManager.VERTICAL,
        false
    )
}