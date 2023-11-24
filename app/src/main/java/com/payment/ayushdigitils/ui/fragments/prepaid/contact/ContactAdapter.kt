package com.payment.ayushdigitils.ui.fragments.prepaid.contact

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.payment.ayushdigitils.databinding.ViewHolderItemContactsBinding
import com.payment.ayushdigitils.ex.setFirstAndLastName


class ContactAdapter(
    private val listener: OnContactClickListener
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>(), Filterable {

    private val banksList: MutableList<ContactModel> = mutableListOf()
    private val banksListFiltered: MutableList<ContactModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val itemBinding =
            ViewHolderItemContactsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(itemBinding, listener)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(position, banksList[position])
    }

    override fun getItemCount(): Int = banksList.size

    fun setContacts(listItems: List<ContactModel>) {
        banksList.clear()
        banksListFiltered.clear()
        banksList.addAll(listItems)
        banksListFiltered.addAll(listItems)
        notifyDataSetChanged()
    }


    class ContactViewHolder(
        private val binding: ViewHolderItemContactsBinding,
        private val listener: OnContactClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int, contact: ContactModel) {
            binding.lblMainLetter.text = contact.name.subSequence(0,1)
            binding.lblMainName.text = contact.name
            binding.imgAvatar.setFirstAndLastName(contact.name)

            binding.root.setOnClickListener {
                listener.onContactClicked(position, contact)
            }
        }
    }


    interface OnContactClickListener {
        fun onContactClicked(position: Int, contact: ContactModel)
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
                    val filteredResults = ArrayList<ContactModel>()

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
                banksList.addAll(p1!!.values as List<ContactModel>)
                notifyDataSetChanged()
            }
        }

        return filter
    }
}