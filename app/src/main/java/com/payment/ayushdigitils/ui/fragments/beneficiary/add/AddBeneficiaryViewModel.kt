package com.payment.ayushdigitils.ui.fragments.beneficiary.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.payment.ayushdigitils.data.remote.PaySprintDmtBeneficiaryVerifyResponse
import com.payment.ayushdigitils.data.remote.PaySprintDmtRegistrationResponse
import com.payment.ayushdigitils.ui.base.BaseViewModel
import com.payment.ayushdigitils.utils.AbsentLiveData
import com.payment.ayushdigitils.vo.Resource
import okhttp3.RequestBody

class AddBeneficiaryViewModel : BaseViewModel() {
    val addBeneficiaryBody = MutableLiveData<RequestBody>()
    val verifyBeneficiaryBody = MutableLiveData<RequestBody>()



    val getDmtRegistrationResponse: LiveData<Resource<PaySprintDmtRegistrationResponse>> = addBeneficiaryBody.switchMap {
        if (it == null) {
            AbsentLiveData.create()
        } else {
            showLoader.value = true
            aawazRepository.requestPaySprintDmtRegistration(it)
        }
    }
    val getBeneficiaryVerifyResponse: LiveData<Resource<PaySprintDmtBeneficiaryVerifyResponse>> = verifyBeneficiaryBody.switchMap {
        if (it == null) {
            AbsentLiveData.create()
        } else {
            showLoader.value = true
            aawazRepository.requestPaySprintDmtAddVerifyBeneficiary(it)
        }
    }
}