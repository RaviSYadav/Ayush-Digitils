package com.payment.ayushdigitils.ui.fragments.dashboard.section.header

import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.databinding.ViewHolderHeaderBinding
import com.payment.ayushdigitils.ui.fragments.dashboard.Section
import com.payment.ayushdigitils.ui.utils.ViewBindingEpoxyModelWithHolder


@EpoxyModelClass
abstract class HeaderItemModel(private val displayViewAll: Boolean) : ViewBindingEpoxyModelWithHolder<ViewHolderHeaderBinding>() {

    @EpoxyAttribute
    lateinit var section: Section

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var clickListener: View.OnClickListener

    override fun getDefaultLayout(): Int = R.layout.view_holder_header

    override fun ViewHolderHeaderBinding.bind() {

        tvHeaderTitle.text = section.title


        section.imageUrl?.let {art->
            imgArt.setImageResource(art)
        }


    }
}