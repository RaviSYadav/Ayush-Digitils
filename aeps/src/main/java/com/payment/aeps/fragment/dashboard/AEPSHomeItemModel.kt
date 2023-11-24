package com.payment.aeps.fragment.dashboard

import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.payment.aeps.R
import com.payment.aeps.databinding.ViewHolderItemAepsBinding
import com.payment.aeps.utils.ViewBindingEpoxyModelWithHolder

/**
 * Created by Ravi Yadav <> on 28/08/23.
 * Safepe pvt
 *
 */

@EpoxyModelClass
abstract class AEPSHomeItemModel :
    ViewBindingEpoxyModelWithHolder<ViewHolderItemAepsBinding>() {

    @EpoxyAttribute
    lateinit var aepsItems: AEPSDashboard

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var clickListener: View.OnClickListener

    override fun getDefaultLayout(): Int = R.layout.view_holder_item_aeps

    override fun ViewHolderItemAepsBinding.bind() {

        tvTitle.text = aepsItems.title
        imgArt.setImageResource(aepsItems.imageUrl)
        root.setOnClickListener(clickListener)
        imgArt.setOnClickListener(clickListener)


    }
}