package com.payment.aeps.fragment.invoice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.payment.aeps.R
import com.payment.aeps.databinding.ViewHolderItemAepsStatementBinding
import com.payment.aeps.remote.AepsStatementData


class AepsStatementAdapter : RecyclerView.Adapter<AepsStatementAdapter.AepsStatementViewHolder>() {

    private val statementList: MutableList<AepsStatementData> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AepsStatementViewHolder {
        val itemBinding =
            ViewHolderItemAepsStatementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AepsStatementViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: AepsStatementViewHolder, position: Int) {
        holder.bind(position, statementList[position])
    }

    override fun getItemCount(): Int = statementList.size

    fun setStatement(listItems: List<AepsStatementData>) {
        statementList.clear()
        statementList.addAll(listItems)
        notifyDataSetChanged()
    }


    class AepsStatementViewHolder(
        private val binding: ViewHolderItemAepsStatementBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int, statement: AepsStatementData) {
            binding.lblDate.text = statement.date
            binding.lblType.text = statement.txnType
            binding.lblStatementAmount.apply {
                text = resources.getString(R.string.full_name,resources.getString(R.string.Rs),statement.amount.toString())
            }

        }
    }


}