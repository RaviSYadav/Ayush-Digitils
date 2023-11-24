package com.payment.ayushdigitils.ui.fragments.bills

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.payment.ayushdigitils.data.remote.BillParamsResponse
import com.payment.ayushdigitils.data.remote.BillValidateResponse
import com.payment.ayushdigitils.ui.base.BaseViewModel
import com.payment.ayushdigitils.utils.AbsentLiveData
import com.payment.ayushdigitils.vo.Resource
import okhttp3.RequestBody


class BillParamsViewModel : BaseViewModel() {

    val providerId = MutableLiveData<RequestBody>()
    val validateRequest = MutableLiveData<RequestBody>()





    val getBillParamsResponse: LiveData<Resource<BillParamsResponse>> = providerId.switchMap {
        if (it == null) {
            AbsentLiveData.create()
        } else {
            showLoader.value = true
            aawazRepository.requestBillParams(it)
        }
    }


    val getBillValidateResponse: LiveData<Resource<BillValidateResponse>> = validateRequest.switchMap {
        if (it == null) {
            AbsentLiveData.create()
        } else {
            showLoader.value = true
            aawazRepository.requestBillValidate(it)
        }
    }
}