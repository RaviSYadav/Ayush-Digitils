package com.payment.ayushdigitils.ui.fragments.beneficiary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.payment.ayushdigitils.data.remote.PaySprintDmtVerifyResponse
import com.payment.ayushdigitils.ui.base.BaseViewModel
import com.payment.ayushdigitils.utils.AbsentLiveData
import com.payment.ayushdigitils.vo.Resource
import okhttp3.RequestBody

class BeneficiaryViewModel : BaseViewModel() {
    val beneficiaryBody = MutableLiveData<RequestBody>()
    val deleteBeneficiaryBody = MutableLiveData<RequestBody>()


    val getBeneficiaryBodyResponse: LiveData<Resource<PaySprintDmtVerifyResponse>> = beneficiaryBody.switchMap {
            if (it == null) {
                AbsentLiveData.create()
            } else {
                showLoader.value = true
                aawazRepository.requestSearchPaySprintDmtVerify(it)
            }
        }

    val deleteBeneficiaryResponse: LiveData<Resource<PaySprintDmtVerifyResponse>> = deleteBeneficiaryBody.switchMap {
        if (it == null) {
            AbsentLiveData.create()
        } else {
            showLoader.value = true
            aawazRepository.requestSearchPaySprintBeneficiaryDelete(it)
        }
    }
}