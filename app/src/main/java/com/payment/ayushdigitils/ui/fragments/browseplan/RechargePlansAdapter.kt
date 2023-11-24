package com.payment.ayushdigitils.ui.fragments.browseplan

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.payment.ayushdigitils.data.remote.NetworkRechargePlansData
import com.payment.ayushdigitils.databinding.ViewHolderItemRechargePlansBinding


class RechargePlansAdapter(
    private val listener: OnPlanClickListener
) : RecyclerView.Adapter<RechargePlansAdapter.RechargePlansViewHolder>(), Filterable {

    private val plansList: MutableList<NetworkRechargePlansData> = mutableListOf()
    private val planListFiltered: MutableList<NetworkRechargePlansData> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RechargePlansViewHolder {
        val itemBinding =
            ViewHolderItemRechargePlansBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RechargePlansViewHolder(itemBinding, listener)
    }

    override fun onBindViewHolder(holder: RechargePlansViewHolder, position: Int) {
        holder.bind(position, plansList[position])
    }

    override fun getItemCount(): Int = plansList.size

    fun setPlans(listItems: List<NetworkRechargePlansData>) {
        plansList.clear()
        planListFiltered.clear()
        plansList.addAll(listItems)
        planListFiltered.addAll(listItems)
        notifyDataSetChanged()
    }


    class RechargePlansViewHolder(
        private val binding: ViewHolderItemRechargePlansBinding,
        private val listener: OnPlanClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int, plan: NetworkRechargePlansData) {
            binding.tvPlanPrice.text = "₹"+plan.rs

            binding.tvDetail.text = plan.desc+ """
                    
                    
                    Validity - ${plan.validity}
                    """.trimIndent()
            binding.btnSelect.setOnClickListener {
                listener.onButtonSelect(position, plan)
            }
            binding.imgExpand.setOnClickListener {
                listener.onImgExpand(position,plan)
            }
        }
    }


    interface OnPlanClickListener {
        fun onButtonSelect(position: Int, plan: NetworkRechargePlansData)
        fun onImgExpand(position: Int, plan: NetworkRechargePlansData)
    }

    override fun getFilter(): Filter {
        val filter = object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val filterResults = FilterResults()

                if (p0.isNullOrEmpty()) {
                    filterResults.values = planListFiltered
                    filterResults.count = planListFiltered.size
                } else {
                    val searchChar = p0.toString().lowercase()
                    val filteredResults = ArrayList<NetworkRechargePlansData>()

                    for (provider in planListFiltered) {
                        if (provider.rs?.lowercase()!!.contains(searchChar)) {
                            filteredResults.add(provider)
                        }
                    }

                    filterResults.values = filteredResults
                    filterResults.count = filteredResults.size
                }

                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                plansList.clear()
                plansList.addAll(p1!!.values as List<NetworkRechargePlansData>)
                notifyDataSetChanged()
            }
        }

        return filter
    }
}