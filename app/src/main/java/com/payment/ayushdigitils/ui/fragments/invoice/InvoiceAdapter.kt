package com.payment.ayushdigitils.ui.fragments.invoice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.payment.ayushdigitils.databinding.ViewHolderItemInvoiceBinding
import com.payment.ayushdigitils.ui.utils.Constanse.truncateToTwoDecimalPlaces


class InvoiceAdapter : RecyclerView.Adapter<InvoiceAdapter.InvoiceViewHolder>() {

    private val invoiceList: MutableList<InvoiceModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvoiceViewHolder {
        val itemBinding =
            ViewHolderItemInvoiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InvoiceViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: InvoiceViewHolder, position: Int) {
        holder.bind(position, invoiceList[position])
    }

    override fun getItemCount(): Int = invoiceList.size

    fun setInvoice(listItems: List<InvoiceModel>) {
        invoiceList.clear()
        invoiceList.addAll(listItems)
        notifyDataSetChanged()
    }


    class InvoiceViewHolder(
        private val binding: ViewHolderItemInvoiceBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int, invoice: InvoiceModel) {
            binding.lblKey.text = invoice.key
            binding.lblValue.text = truncateToTwoDecimalPlaces(invoice.value?:"")

        }
    }


}