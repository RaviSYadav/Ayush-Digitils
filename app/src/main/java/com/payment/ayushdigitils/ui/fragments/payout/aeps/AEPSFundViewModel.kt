package com.payment.ayushdigitils.ui.fragments.payout.aeps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.payment.ayushdigitils.data.remote.FundRequestResponse
import com.payment.ayushdigitils.data.remote.FundUserBankResponse
import com.payment.ayushdigitils.ui.base.BaseViewModel
import com.payment.ayushdigitils.utils.AbsentLiveData
import com.payment.ayushdigitils.vo.Resource
import okhttp3.RequestBody

class AEPSFundViewModel : BaseViewModel() {

    val requestUserBank = MutableLiveData<RequestBody>()
    val requestFundRequest = MutableLiveData<RequestBody>()



    val getUserBankResponse: LiveData<Resource<FundUserBankResponse>> = requestUserBank.switchMap {
        if (it == null) {
            AbsentLiveData.create()
        } else {
            showLoader.value = true
            aawazRepository.requestFundUserBank(it)
        }
    }
    val getFundRequestResponse: LiveData<Resource<FundRequestResponse>> = requestFundRequest.switchMap {
        if (it == null) {
            AbsentLiveData.create()
        } else {
            showLoader.value = true
            aawazRepository.requestFundUserRequest(it)
        }
    }
}