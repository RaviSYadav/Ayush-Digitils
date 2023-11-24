package com.payment.ayushdigitils.ui.fragments.beneficiary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.payment.ayushdigitils.data.remote.NetworkPaySprintBeneficiaryData
import com.payment.ayushdigitils.databinding.ViewHolderItemBeneficiaryBinding


class BeneficiaryAdapter(
    private val listener: OnBeneficiaryClickListener
) : RecyclerView.Adapter<BeneficiaryAdapter.BeneficiaryViewHolder>() {

    private val beneficiary: MutableList<NetworkPaySprintBeneficiaryData> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeneficiaryViewHolder {
        val itemBinding =
            ViewHolderItemBeneficiaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BeneficiaryViewHolder(itemBinding, listener)
    }

    override fun onBindViewHolder(holder: BeneficiaryViewHolder, position: Int) {
        holder.bind(position, beneficiary[position])
    }

    override fun getItemCount(): Int = beneficiary.size

    fun setBeneficiaryItems(listItems: List<NetworkPaySprintBeneficiaryData>) {
        beneficiary.clear()
        beneficiary.addAll(listItems)
        notifyDataSetChanged()
    }


    class BeneficiaryViewHolder(
        private val binding: ViewHolderItemBeneficiaryBinding,
        private val listener: OnBeneficiaryClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int, beneficiary: NetworkPaySprintBeneficiaryData) {

            binding.lblBeneficiaryId.text = "Beneficiary id: ${beneficiary.bene_id}"

            binding.lblBeneficiaryName.text = beneficiary.name
            binding.lblBeneficiaryAccount.text = beneficiary.accno
            binding.lblBeneficiaryBank.text = beneficiary.bankname
            binding.lblBeneficiaryStatus.text = if (beneficiary.verified == "1") "Verify" else "Not Verify"


            binding.lblTransfer.setOnClickListener {
                listener.onTransferClicked(position, beneficiary)
            }
            binding.lblDelete.setOnClickListener {
                listener.onDeleteClicked(position, beneficiary)
            }

        }



    }




    interface OnBeneficiaryClickListener {
        fun onTransferClicked(position: Int, beneficiary: NetworkPaySprintBeneficiaryData)
        fun onDeleteClicked(position: Int, beneficiary: NetworkPaySprintBeneficiaryData)
    }
}