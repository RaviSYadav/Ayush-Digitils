package com.payment.aeps.fragment.bank

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.payment.aeps.databinding.ViewHolderItemBanksBinding
import com.payment.aeps.remote.NetworkAepsBank




class BankAdapter(
    private val listener: OnBankClickListener
) : RecyclerView.Adapter<BankAdapter.BanksViewHolder>(), Filterable {

    private val banksList: MutableList<NetworkAepsBank> = mutableListOf()
    private val banksListFiltered: MutableList<NetworkAepsBank> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BanksViewHolder {
        val itemBinding =
            ViewHolderItemBanksBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BanksViewHolder(itemBinding, listener)
    }

    override fun onBindViewHolder(holder: BanksViewHolder, position: Int) {
        holder.bind(position, banksList[position])
    }

    override fun getItemCount(): Int = banksList.size

    fun setBanks(listItems: List<NetworkAepsBank>) {
        banksList.clear()
        banksListFiltered.clear()
        banksList.addAll(listItems)
        banksListFiltered.addAll(listItems)
        notifyDataSetChanged()
    }


    class BanksViewHolder(
        private val binding: ViewHolderItemBanksBinding,
        private val listener: OnBankClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int, bank: NetworkAepsBank) {
            binding.lblBankName.text = bank.bankName
            binding.root.setOnClickListener {
                listener.onBankClicked(position, bank)
            }
        }
    }


    interface OnBankClickListener {
        fun onBankClicked(position: Int, bank: NetworkAepsBank)
    }

    override fun getFilter(): Filter {
        val filter = object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val filterResults = FilterResults()

                if (p0.isNullOrEmpty()) {
                    filterResults.values = banksListFiltered
                    filterResults.count = banksListFiltered.size
                } else {
                    val searchChar = p0.toString().lowercase()
                    val filteredResults = ArrayList<NetworkAepsBank>()

                    for (provider in banksListFiltered) {
                        if (provider.bankName.lowercase().contains(searchChar)) {
                            filteredResults.add(provider)
                        }
                    }

                    filterResults.values = filteredResults
                    filterResults.count = filteredResults.size
                }

                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                banksList.clear()
                banksList.addAll(p1!!.values as List<NetworkAepsBank>)
                notifyDataSetChanged()
            }
        }

        return filter
    }
}