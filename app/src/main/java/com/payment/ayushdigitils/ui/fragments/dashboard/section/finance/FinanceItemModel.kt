package com.payment.ayushdigitils.ui.fragments.dashboard.section.finance

import android.view.View

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.databinding.ViewHolderItemFinanceBinding
import com.payment.ayushdigitils.ui.fragments.dashboard.Dashboard
import com.payment.ayushdigitils.ui.utils.ViewBindingEpoxyModelWithHolder

@EpoxyModelClass
abstract class FinanceItemModel : ViewBindingEpoxyModelWithHolder<ViewHolderItemFinanceBinding>() {

    override fun getDefaultLayout(): Int = R.layout.view_holder_item_finance

    @EpoxyAttribute
    lateinit var dashboard: Dashboard

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
     lateinit var clickListener: View.OnClickListener

    override fun ViewHolderItemFinanceBinding.bind() {

        tvTitle.text = dashboard.title
        root.setOnClickListener(clickListener)
        imgArt.setImageResource(dashboard.imageUrl)

    }
}