package com.payment.ayushdigitils.ui.fragments.prepaid.contact


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.payment.ayushdigitils.repository.ContactRepository
import com.payment.ayushdigitils.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class ContactViewModel(private val repo: ContactRepository) : BaseViewModel() {


    private val _contactList = MutableLiveData<List<ContactModel>>()
    val contactList: LiveData<List<ContactModel>> = _contactList



    fun fetchContacts() {
        showLoader.value = true
        viewModelScope.launch {
            val contacts = repo.getContacts()
            _contactList.postValue(contacts)
        }
    }
}