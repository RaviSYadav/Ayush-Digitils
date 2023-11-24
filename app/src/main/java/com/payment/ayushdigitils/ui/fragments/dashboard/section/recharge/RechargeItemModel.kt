package com.payment.ayushdigitils.ui.fragments.dashboard.section.recharge

import android.view.View

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.databinding.ViewHolderItemRechargeBinding
import com.payment.ayushdigitils.ui.fragments.dashboard.Dashboard
import com.payment.ayushdigitils.ui.utils.ViewBindingEpoxyModelWithHolder

@EpoxyModelClass
abstract class RechargeItemModel : ViewBindingEpoxyModelWithHolder<ViewHolderItemRechargeBinding>() {

    override fun getDefaultLayout(): Int = R.layout.view_holder_item_recharge

    @EpoxyAttribute
    lateinit var dashboard: Dashboard

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
     lateinit var clickListener: View.OnClickListener

    override fun ViewHolderItemRechargeBinding.bind() {

        tvTitle.text = dashboard.title
        root.setOnClickListener(clickListener)
        imgArt.setImageResource(dashboard.imageUrl)

//        imgArt.loadImage(dashboard.imageUrl,imgArt.width,imgArt.width)

    }
}