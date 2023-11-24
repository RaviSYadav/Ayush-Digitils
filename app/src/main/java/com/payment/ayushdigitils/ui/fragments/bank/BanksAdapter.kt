package com.payment.ayushdigitils.ui.fragments.bank

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.payment.ayushdigitils.data.remote.NetworkPaySprintDmtBanksData
import com.payment.ayushdigitils.databinding.ViewHolderItemDmtBanksBinding

class BanksAdapter(
    private val listener: OnBankClickListener
) : RecyclerView.Adapter<BanksAdapter.BanksViewHolder>(), Filterable {

    private val banksList: MutableList<NetworkPaySprintDmtBanksData> = mutableListOf()
    private val banksListFiltered: MutableList<NetworkPaySprintDmtBanksData> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BanksViewHolder {
        val itemBinding =
            ViewHolderItemDmtBanksBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BanksViewHolder(itemBinding, listener)
    }

    override fun onBindViewHolder(holder: BanksViewHolder, position: Int) {
        holder.bind(position, banksList[position])
    }

    override fun getItemCount(): Int = banksList.size

    fun setBanks(listItems: List<NetworkPaySprintDmtBanksData>) {
        banksList.clear()
        banksListFiltered.clear()
        banksList.addAll(listItems)
        banksListFiltered.addAll(listItems)
        notifyDataSetChanged()
    }


    class BanksViewHolder(
        private val binding: ViewHolderItemDmtBanksBinding,
        private val listener: OnBankClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int, bank: NetworkPaySprintDmtBanksData) {
            binding.lblBankName.text = bank.name
            binding.root.setOnClickListener {
                listener.onBankClicked(position, bank)
            }
        }
    }


    interface OnBankClickListener {
        fun onBankClicked(position: Int, bank: NetworkPaySprintDmtBanksData)
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
                    val filteredResults = ArrayList<NetworkPaySprintDmtBanksData>()

                    for (provider in banksListFiltered) {
                        if (provider.name.lowercase().contains(searchChar)) {
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
                banksList.addAll(p1!!.values as List<NetworkPaySprintDmtBanksData>)
                notifyDataSetChanged()
            }
        }

        return filter
    }
}