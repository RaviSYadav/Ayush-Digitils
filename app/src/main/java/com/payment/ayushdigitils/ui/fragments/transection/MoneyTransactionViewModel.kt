package com.payment.ayushdigitils.ui.fragments.transection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.payment.ayushdigitils.data.remote.PaySprintMoneyTransactionResponse
import com.payment.ayushdigitils.ui.base.BaseViewModel
import com.payment.ayushdigitils.utils.AbsentLiveData
import com.payment.ayushdigitils.vo.Resource
import okhttp3.RequestBody

class MoneyTransactionViewModel : BaseViewModel() {
    val transactionBody = MutableLiveData<RequestBody>()


    val getMoneyTransactionResponse: LiveData<Resource<PaySprintMoneyTransactionResponse>> = transactionBody.switchMap {
        if (it == null) {
            AbsentLiveData.create()
        } else {
            showLoader.value = true
            aawazRepository.requestPaySprintDmtMoneyTransaction(it)
        }
    }

}