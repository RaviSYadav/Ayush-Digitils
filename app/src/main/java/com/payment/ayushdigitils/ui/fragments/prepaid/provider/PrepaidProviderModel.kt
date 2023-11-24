package com.payment.ayushdigitils.ui.fragments.prepaid.provider

import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.data.remote.NetworkPrepaidProvidersData
import com.payment.ayushdigitils.databinding.ViewHolderItemBillOperatorBinding
import com.payment.ayushdigitils.ui.utils.Constanse
import com.payment.ayushdigitils.ui.utils.ViewBindingEpoxyModelWithHolder


@EpoxyModelClass
abstract class PrepaidProviderModel: ViewBindingEpoxyModelWithHolder<ViewHolderItemBillOperatorBinding>(){

    override fun getDefaultLayout(): Int = R.layout.view_holder_item_bill_operator


    @EpoxyAttribute
    lateinit var operatorItem: NetworkPrepaidProvidersData


    @EpoxyAttribute
    lateinit var type: String

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var providerClickListener: View.OnClickListener

    override fun ViewHolderItemBillOperatorBinding.bind() {

        lblOperatorTitle.text = operatorItem.name
        lblOperatorTitle.isSelected = true

        Constanse.setStaticProviderImg( operatorItem.name,ivArt, ivArt.context, type)


        operatorContainer.setOnClickListener(providerClickListener)


    }

}