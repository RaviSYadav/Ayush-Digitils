package com.payment.ayushdigitils.ui.fragments.dashboard.section.special

import android.view.View

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.databinding.ViewHolderItemFinanceBinding
import com.payment.ayushdigitils.databinding.ViewHolderItemSpecialBinding
import com.payment.ayushdigitils.ui.fragments.dashboard.Dashboard
import com.payment.ayushdigitils.ui.utils.ViewBindingEpoxyModelWithHolder

@EpoxyModelClass
abstract class SpecialItemModel : ViewBindingEpoxyModelWithHolder<ViewHolderItemSpecialBinding>() {

    override fun getDefaultLayout(): Int = R.layout.view_holder_item_special

    @EpoxyAttribute
    lateinit var dashboard: Dashboard

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
     lateinit var specialClickListener: View.OnClickListener

    override fun ViewHolderItemSpecialBinding.bind() {

        tvTitle.text = dashboard.title
        root.setOnClickListener(specialClickListener)
        imgArt.setImageResource(dashboard.imageUrl)

    }
}