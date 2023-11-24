package com.payment.ayushdigitils.ui.fragments.bills

object FetchBills {


    fun getBillPayParam(type: String): List<ParameItem> {
        val dataList: MutableList<ParameItem> = ArrayList<ParameItem>()
        if (type.contains("electricity")) {
            dataList.add(ParameItem("Bill Number"))
        } else if (type.contains("lpggas")) {
            dataList.add(ParameItem("Customer ID"))
        } else if (type.contains("landline")) {
            dataList.add(ParameItem("Telephone Number"))
        } else if (type.contains("broadband")) {
            dataList.add(ParameItem("Account Number"))
        } else if (type.contains("water")) {
            dataList.add(ParameItem("Customer ID"))
        } else if (type.contains("loanrepay")) {
            dataList.add(ParameItem("Loan Account Number"))
        } else if (type.contains("lifeinsurance")) {
            dataList.add(ParameItem("Policy Number"))
        } else if (type.contains("fasttag")) {
            dataList.add(ParameItem("Vehicle Number"))
        } else if (type.contains("cable")) {
            dataList.add(ParameItem("Account Number"))
        } else if (type.contains("insurance")) {
            dataList.add(ParameItem("Policy Number"))
        } else if (type.contains("schoolfees")) {
            dataList.add(ParameItem("Date of Birth(dd/mm/yyyy)"))
        } else if (type.contains("muncipal")) {
            dataList.add(ParameItem("Property ID"))
        } else if (type.contains("postpaid")) {
            dataList.add(ParameItem("Customer Id"))
        } else if (type.contains("housing")) {
            dataList.add(ParameItem("Apartment Number"))
        } else {
            dataList.add(ParameItem("Customer id"))
        }
        dataList.add(ParameItem("Contact Number"))
        return dataList
    }
}