package com.payment.ayushdigitils.ui.fragments.report

import com.payment.ayushdigitils.ui.base.BaseViewModel

class ReportsViewModel : BaseViewModel() {

    private var tabIndex = 0

    fun setTabIndex(value: Int) {
        tabIndex = value
    }

    fun getTabIndex() = tabIndex
}