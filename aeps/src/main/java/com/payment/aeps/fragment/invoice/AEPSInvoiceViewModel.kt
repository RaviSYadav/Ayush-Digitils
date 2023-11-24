package com.payment.aeps.fragment.invoice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.payment.aeps.remote.AepsInvoiceData

class AEPSInvoiceViewModel : ViewModel() {


    private val invoiceList = MutableLiveData<MutableList<AepsInvoiceData>>()
    fun setInvoiceList(list: MutableList<AepsInvoiceData>) {
        invoiceList.value = list
    }

    fun getInvoiceList(): LiveData<MutableList<AepsInvoiceData>> {
        return invoiceList
    }

}