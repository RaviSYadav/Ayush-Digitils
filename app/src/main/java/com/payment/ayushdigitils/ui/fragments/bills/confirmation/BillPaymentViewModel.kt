package com.payment.ayushdigitils.ui.fragments.bills.confirmation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.payment.ayushdigitils.data.remote.BillPaymentResponse
import com.payment.ayushdigitils.ui.base.BaseViewModel
import com.payment.ayushdigitils.utils.AbsentLiveData
import com.payment.ayushdigitils.vo.Resource
import okhttp3.RequestBody

class BillPaymentViewModel : BaseViewModel() {

    val billPaymentRequest = MutableLiveData<RequestBody>()





    val getBillPaymentResponse: LiveData<Resource<BillPaymentResponse>> = billPaymentRequest.switchMap {
        if (it == null) {
            AbsentLiveData.create()
        } else {
            showLoader.value = true
            aawazRepository.requestBillPayment(it)
        }
    }

}