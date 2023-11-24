package com.payment.ayushdigitils.ui.fragments.invoice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.payment.ayushdigitils.ui.base.BaseViewModel

class InvoiceViewModel : BaseViewModel() {
    private val invoiceList = MutableLiveData<MutableList<InvoiceModel>>()
    fun setInvoiceList(list: MutableList<InvoiceModel>) {
        invoiceList.value = list
    }

     fun clearInvoiceList(){
        invoiceList.value?.clear()
    }

    fun getInvoiceList(): LiveData<MutableList<InvoiceModel>> {
        return invoiceList
    }
}