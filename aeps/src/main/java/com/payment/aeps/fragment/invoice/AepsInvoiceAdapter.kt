package com.payment.aeps.fragment.invoice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.payment.aeps.databinding.ViewHolderItemAepsInvoiceBinding
import com.payment.aeps.remote.AepsInvoiceData
import com.payment.aeps.utils.utils

class AepsInvoiceAdapter : RecyclerView.Adapter<AepsInvoiceAdapter.AepsInvoiceViewHolder>() {

    private val invoiceList: MutableList<AepsInvoiceData> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AepsInvoiceViewHolder {
        val itemBinding =
            ViewHolderItemAepsInvoiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AepsInvoiceViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: AepsInvoiceViewHolder, position: Int) {
        holder.bind(position, invoiceList[position])
    }

    override fun getItemCount(): Int = invoiceList.size

    fun setInvoice(listItems: List<AepsInvoiceData>) {
        invoiceList.clear()
        invoiceList.addAll(listItems)
        notifyDataSetChanged()
    }


    class AepsInvoiceViewHolder(
        private val binding: ViewHolderItemAepsInvoiceBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int, invoice: AepsInvoiceData) {
            binding.lblKey.text = invoice.key
            binding.lblValue.text = utils().truncateToTwoDecimalPlacesAeps(invoice.value?:"")

        }
    }


}