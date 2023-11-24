package com.payment.ayushdigitils.ui.fragments.report.wallet_fund_report

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.data.remote.RechargeReportData
import com.payment.ayushdigitils.databinding.ViewHolderItemReportsBinding

class WalletFundReportAdapter(
    private val listener: OnReportClickListener
) : RecyclerView.Adapter<WalletFundReportAdapter.WalletFundReportsViewHolder>() {

    private val reportsList: MutableList<RechargeReportData> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletFundReportsViewHolder {
        val itemBinding =
            ViewHolderItemReportsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WalletFundReportsViewHolder(itemBinding, listener)
    }

    override fun onBindViewHolder(holder: WalletFundReportsViewHolder, position: Int) {
        holder.bind(position, reportsList[position])
    }

    override fun getItemCount(): Int = reportsList.size

    fun setReports(listItems: List<RechargeReportData>) {
        reportsList.clear()
        reportsList.addAll(listItems)
        notifyDataSetChanged()
    }


    class WalletFundReportsViewHolder(
        private val binding: ViewHolderItemReportsBinding,
        private val listener: OnReportClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int, report: RechargeReportData) {
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


    interface OnReportClickListener {
        fun onShareClick(position: Int, reports: RechargeReportData, view: View)
        fun onInvoiceClick(position: Int, reports: RechargeReportData)
    }

}