package com.payment.ayushdigitils.ui.fragments.report.aeps_report

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.data.remote.AEPSReportData
import com.payment.ayushdigitils.databinding.ViewHolderItemReportsBinding



class AepsReportsAdapter(
    private val listener: OnTrackClickListener
) : RecyclerView.Adapter<AepsReportsAdapter.AepsReportsViewHolder>() {

    private val reportsList: MutableList<AEPSReportData> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AepsReportsViewHolder {
        val itemBinding =
            ViewHolderItemReportsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AepsReportsViewHolder(itemBinding, listener)
    }

    override fun onBindViewHolder(holder: AepsReportsViewHolder, position: Int) {
        holder.bind(position, reportsList[position])
    }

    override fun getItemCount(): Int = reportsList.size

    fun setReports(listItems: List<AEPSReportData>) {
        reportsList.clear()
        reportsList.addAll(listItems)
        notifyDataSetChanged()
    }


    class AepsReportsViewHolder(
        private val binding: ViewHolderItemReportsBinding,
        private val listener: OnTrackClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int, report: AEPSReportData) {
            binding.lblId.text = report.txnid
            binding.lblAmount.apply {
                text = resources.getString(
                    R.string.remitter_full_name,
                    resources.getString(R.string.Rs),
                    report.amount
                )
            }
            binding.lblMobile.apply {
                text = resources.getString(
                    R.string.remitter_full_name,
                    "+91",
                    " ${report.mobile}"
                )
            }
            binding.lblTime.text = report.updated_at
            when(report.status){
                "success"->binding.ivArt.setImageResource(com.payment.aeps.R.drawable.ic_aeps_txn_success)
                "failed"->binding.ivArt.setImageResource(com.payment.aeps.R.drawable.ic_aeps_txn_failed)
                else->binding.ivArt.setImageResource(com.payment.aeps.R.drawable.ic_aeps_txn_pending)



            }

            binding.ivInvoice.setOnClickListener {
                listener.onInvoiceClick(position,report)
            }
            binding.ivShare.setOnClickListener {
                listener.onShareClick(position,report,binding.containerReport)
            }
        }
    }


    interface OnTrackClickListener {
        fun onShareClick(position: Int, reports: AEPSReportData, view: View)
        fun onInvoiceClick(position: Int, reports: AEPSReportData)
    }

}