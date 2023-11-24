package com.payment.ayushdigitils.ui.fragments.payout.wallet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.payment.ayushdigitils.data.remote.FundBankResponse
import com.payment.ayushdigitils.data.remote.FundRequestResponse
import com.payment.ayushdigitils.ui.base.BaseViewModel
import com.payment.ayushdigitils.utils.AbsentLiveData
import com.payment.ayushdigitils.vo.Resource
import okhttp3.RequestBody

class WalletFundViewModel : BaseViewModel() {

     val requestFundBank = MutableLiveData<RequestBody>()
    val requestFundRequest = MutableLiveData<RequestBody>()



    val getFundBankResponse: LiveData<Resource<FundBankResponse>> = requestFundBank.switchMap {
        if (it == null) {
            AbsentLiveData.create()
        } else {
            showLoader.value = true
            aawazRepository.requestFundBank(it)
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