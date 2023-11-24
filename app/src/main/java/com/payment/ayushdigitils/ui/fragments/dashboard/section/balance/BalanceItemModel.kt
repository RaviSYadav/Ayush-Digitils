package com.payment.ayushdigitils.ui.fragments.dashboard.section.balance

import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.databinding.ViewHolderItemBalanceBinding
import com.payment.ayushdigitils.ui.fragments.dashboard.section.Balance
import com.payment.ayushdigitils.ui.utils.ViewBindingEpoxyModelWithHolder



@EpoxyModelClass
abstract class BalanceItemModel : ViewBindingEpoxyModelWithHolder<ViewHolderItemBalanceBinding>() {

    override fun getDefaultLayout(): Int = R.layout.view_holder_item_balance

    @EpoxyAttribute
    lateinit var balance: Balance

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var clickListener: View.OnClickListener

    override fun ViewHolderItemBalanceBinding.bind() {

        lblTitle.text = balance.title
        lblAmount.apply {
            if (balance.amount.isNotEmpty()) {
                text = resources.getString(
                    R.string.remitter_full_name,
                    resources.getString(R.string.Rs),
                    balance.amount
                )
            }
        }

        if (balance.type.equals("aeps_fund")){
            balanceContainer.setBackgroundResource(R.drawable.ic_aeps_balance_bg)
        }else{
            balanceContainer.setBackgroundResource(R.drawable.ic_main_balance_bg)
        }
        root.setOnClickListener(clickListener)

    }
}