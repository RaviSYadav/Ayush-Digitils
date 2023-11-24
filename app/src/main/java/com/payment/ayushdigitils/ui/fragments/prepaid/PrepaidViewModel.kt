package com.payment.ayushdigitils.ui.fragments.prepaid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.payment.ayushdigitils.data.remote.PrepaidProvidersResponse
import com.payment.ayushdigitils.data.remote.RechargePayResponse
import com.payment.ayushdigitils.ui.base.BaseViewModel
import com.payment.ayushdigitils.utils.AbsentLiveData
import com.payment.ayushdigitils.vo.Resource
import okhttp3.RequestBody

class PrepaidViewModel: BaseViewModel() {


    val providerJson = MutableLiveData<RequestBody>()
    val rechargePayJson = MutableLiveData<RequestBody>()





    val getPrepaidProviders: LiveData<Resource<PrepaidProvidersResponse>> = providerJson.switchMap {
        if (it == null) {
            AbsentLiveData.create()
        } else {
            showLoader.value = true
            aawazRepository.requestRechargeProviders(it)
        }
    }

    val getRechargePayResponse: LiveData<Resource<RechargePayResponse>> = rechargePayJson.switchMap {
        if (it == null) {
            AbsentLiveData.create()
        } else {
            showLoader.value = true
            aawazRepository.requestRechargePay(it)
        }
    }
}