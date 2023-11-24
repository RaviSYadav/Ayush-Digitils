package com.payment.ayushdigitils.ui.fragments.report

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.payment.ayushdigitils.databinding.ViewHolderItemHistoryBinding

class ReportsAdapter(): RecyclerView.Adapter<ReportsAdapter.ReportsViewHolder>() {
    private var categoriesList = ArrayList<ReportData>()
    fun setCategoryList(categoriesList: MutableList<ReportData>){
        this.categoriesList = categoriesList as ArrayList<ReportData>
        notifyDataSetChanged()
    }
    inner class ReportsViewHolder(val binding: ViewHolderItemHistoryBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportsViewHolder {
        return ReportsViewHolder(
            ViewHolderItemHistoryBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: ReportsViewHolder, position: Int) {

        holder.binding.lblPaidFor.text = categoriesList[position].paidFor
        holder.binding.lblUserName.text = categoriesList[position].userName
        holder.binding.lblReportTime.text = categoriesList[position].time
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }
}