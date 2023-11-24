package com.payment.ayushdigitils.ui.fragments.dashboard.section.special

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.ModelView

@ModelView(saveViewState = true, autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class SpecialCarousel(context: Context) : Carousel(context) {



    override fun createLayoutManager(): LayoutManager = LinearLayoutManager(
        context,
        LinearLayoutManager.HORIZONTAL,
        false

    )
}