package com.payment.ayushdigitils.ui.fragments.dmt

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.payment.ayushdigitils.data.remote.PaySprintDmtRegistrationResponse
import com.payment.ayushdigitils.data.remote.PaySprintDmtVerifyResponse
import com.payment.ayushdigitils.ui.base.BaseViewModel
import com.payment.ayushdigitils.utils.AbsentLiveData
import com.payment.ayushdigitils.vo.Resource
import okhttp3.RequestBody

class DMTVerifyViewModel : BaseViewModel() {
    val dmtVerifyBody = MutableLiveData<RequestBody>()
    val dmtRegistrationBody = MutableLiveData<RequestBody>()



    val getDmtVerifyResponse: LiveData<Resource<PaySprintDmtVerifyResponse>> = dmtVerifyBody.switchMap {
        if (it == null) {
            AbsentLiveData.create()
        } else {
            showLoader.value = true
            aawazRepository.requestSearchPaySprintDmtVerify(it)
        }
    }
    val getDmtRegistrationResponse: LiveData<Resource<PaySprintDmtRegistrationResponse>> = dmtRegistrationBody.switchMap {
        if (it == null) {
            AbsentLiveData.create()
        } else {
            showLoader.value = true
            aawazRepository.requestPaySprintDmtRegistration(it)
        }
    }
}