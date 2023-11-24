package com.payment.ayushdigitils.ui.fragments.browseplan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.payment.ayushdigitils.ui.base.BaseViewModel
import com.payment.ayushdigitils.utils.AbsentLiveData
import okhttp3.RequestBody


class RechargePlansViewModel : BaseViewModel() {

    val requestRechargePlans = MutableLiveData<RequestBody>()


    val getRechargePlans = requestRechargePlans.switchMap {
        if (it == null) {
            AbsentLiveData.create()
        } else {
            showLoader.value = true
            aawazRepository.fetchRechargePlans(it)
        }
    }
}