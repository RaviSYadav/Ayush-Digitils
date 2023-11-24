package com.payment.ayushdigitils.ui.fragments.beneficiary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding




class DMTInvoiceAdapter<T : ViewBinding>(
    private val layoutInflater: (LayoutInflater, ViewGroup, Boolean) -> T,
    private val bindItem: (T, MutableList<Any>,Int) -> Unit
) : RecyclerView.Adapter<DMTInvoiceAdapter.DMTInvoiceViewHolder<T>>() {

    private val items = mutableListOf<Any>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DMTInvoiceViewHolder<T> {
        val binding = layoutInflater.invoke(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DMTInvoiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DMTInvoiceViewHolder<T>, position: Int) {
        bindItem(holder.binding,items, position)
    }

    override fun getItemCount(): Int = items.size

    class DMTInvoiceViewHolder<out T : ViewBinding>(val binding: T) : RecyclerView.ViewHolder(binding.root)

    fun submitList(newItems: List<Any>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}
