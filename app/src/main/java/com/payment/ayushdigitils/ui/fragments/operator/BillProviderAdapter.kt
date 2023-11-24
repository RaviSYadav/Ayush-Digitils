package com.payment.ayushdigitils.ui.fragments.operator

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.payment.ayushdigitils.data.remote.NetworkPrepaidProvidersData
import com.payment.ayushdigitils.databinding.ViewHolderItemBillProviderBinding
import com.payment.ayushdigitils.ui.utils.Constanse

class BillProviderAdapter(
    private val listener: AdapterCallbacks
) : RecyclerView.Adapter<BillProviderAdapter.BillProviderViewHolder>(), Filterable {

    private val providerList: MutableList<NetworkPrepaidProvidersData> = mutableListOf()
    private val providerListFiltered: MutableList<NetworkPrepaidProvidersData> = mutableListOf()
    private var type: String? = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillProviderViewHolder {
        val itemBinding =
            ViewHolderItemBillProviderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BillProviderViewHolder(itemBinding, listener)
    }

    override fun onBindViewHolder(holder: BillProviderViewHolder, position: Int) {
        holder.bind(position, providerList[position],type?:"")
    }

    override fun getItemCount(): Int = providerList.size

    fun setProviders(listItems: List<NetworkPrepaidProvidersData>,type:String?) {
        providerList.clear()
        providerListFiltered.clear()
        providerList.addAll(listItems)
        providerListFiltered.addAll(listItems)
        this.type = type

        notifyDataSetChanged()
    }


    class BillProviderViewHolder(
        private val binding: ViewHolderItemBillProviderBinding,
        private val listener: AdapterCallbacks
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int, provider: NetworkPrepaidProvidersData,type: String) {
            binding.lblOperatorTitle.text = provider.name

            Constanse.setStaticProviderImg( provider.name,binding.ivArt, binding.ivArt.context, type = type)

            binding.root.setOnClickListener {
                listener.onProviderClicked(provider,position)
            }
        }
    }


    interface AdapterCallbacks {
        fun onProviderClicked(networkPrepaidProvidersDa: NetworkPrepaidProvidersData, position: Int)
    }

    override fun getFilter(): Filter {
        val filter = object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val filterResults = FilterResults()

                if (p0.isNullOrEmpty()) {
                    filterResults.values = providerListFiltered
                    filterResults.count = providerListFiltered.size
                } else {
                    val searchChar = p0.toString().lowercase()
                    val filteredResults = ArrayList<NetworkPrepaidProvidersData>()

                    for (provider in providerListFiltered) {
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
                providerList.clear()
                providerList.addAll(p1!!.values as List<NetworkPrepaidProvidersData>)
                notifyDataSetChanged()
            }
        }

        return filter
    }
}