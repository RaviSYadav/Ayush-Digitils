package com.payment.ayushdigitils.ui.fragments.bills

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.payment.ayushdigitils.data.remote.NetworkBillParamsData
import com.payment.ayushdigitils.databinding.ViewHolderItemBillParmsBinding



class BillParamsAdapter(
    private val listener: OnParamsClickListener
) : RecyclerView.Adapter<BillParamsAdapter.BillParamViewHolder>() {

    private val paramsList: MutableList<NetworkBillParamsData> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillParamViewHolder {
        val itemBinding =
            ViewHolderItemBillParmsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BillParamViewHolder(itemBinding, listener)
    }

    override fun onBindViewHolder(holder: BillParamViewHolder, position: Int) {
        holder.bind(position, paramsList[position])
    }

    override fun getItemCount(): Int = paramsList.size

    fun setParams(listItems: List<NetworkBillParamsData>) {
        paramsList.clear()
        paramsList.addAll(listItems)
        notifyDataSetChanged()
    }


    class BillParamViewHolder(
        private val binding: ViewHolderItemBillParmsBinding,
        private val listener: OnParamsClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int, parms: NetworkBillParamsData) {

            binding.lblFetchBillParms.hint = parms.paramname

            binding.etFetchBillParms.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }


                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                    parms.fieldInputValue = binding.etFetchBillParms.text.toString()

                    listener.onGetBillParamsValue(position,parms)
                }

                override fun afterTextChanged(s: Editable?) {
                }

            })




        }



    }




    interface OnParamsClickListener {
        fun onGetBillParamsValue(position: Int, params: NetworkBillParamsData)
    }
}