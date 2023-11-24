package com.payment.ayushdigitils.ui.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class GenericAdapter<T, VB : ViewBinding>(
    private val layoutInflater: (LayoutInflater, ViewGroup, Boolean) -> VB,
    private val bind: (VB, T) -> Unit,
    private val onItemClick: (T) -> Unit
) : RecyclerView.Adapter<GenericAdapter<T, VB>.ViewHolder>() {

    private var dataList: List<T> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = layoutInflater.invoke(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        bind.invoke(holder.binding, item)
        holder.itemView.setOnClickListener { onItemClick.invoke(item) }
    }

    fun setData(data: List<T>) {
        dataList = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: VB) : RecyclerView.ViewHolder(binding.root)
}
