package com.payment.ayushdigitils.ui.fragments.report.aeps_report


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.data.remote.AEPSReportData
import com.payment.ayushdigitils.databinding.ViewHolderItemReportsBinding

class AepsReportsPagingAdapter(private val listener: OnTrackClickListener): PagingDataAdapter<AEPSReportData, AepsReportsPagingAdapter.TrackViewHolder>(TrackComparator) {



    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val track = getItem(position)

        track?.let {
            holder.bind(track)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderItemReportsBinding.inflate(inflater, parent, false)
        return TrackViewHolder(binding)
    }

    inner class TrackViewHolder(val binding: ViewHolderItemReportsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(report: AEPSReportData) {

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



    object TrackComparator: DiffUtil.ItemCallback<AEPSReportData>() {
        override fun areItemsTheSame(oldItem: AEPSReportData, newItem: AEPSReportData): Boolean {
            // Id is unique.
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AEPSReportData, newItem: AEPSReportData): Boolean {
            return oldItem == newItem
        }
    }

    interface OnTrackClickListener {
        fun onShareClick(position: Int, reports: AEPSReportData, view: View)
        fun onInvoiceClick(position: Int, reports: AEPSReportData)
    }
}


