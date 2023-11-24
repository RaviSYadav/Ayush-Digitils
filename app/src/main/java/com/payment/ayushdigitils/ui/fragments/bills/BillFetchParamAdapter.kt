package com.payment.ayushdigitils.ui.fragments.bills

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.payment.ayushdigitils.databinding.ViewHolderItemBillParmsBinding

class BillFetchParamAdapter(private val listener: OnListDataLisListener) : RecyclerView.Adapter<BillFetchParamAdapter.BillFetchParamViewHolder>() {

    private var categoriesList = ArrayList<ParameItem>()

    fun setCategoryList(categoriesList: MutableList<ParameItem>){

        this.categoriesList = categoriesList as ArrayList<ParameItem>

        notifyDataSetChanged()
    }
    inner class BillFetchParamViewHolder(val binding: ViewHolderItemBillParmsBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillFetchParamViewHolder {
        return BillFetchParamViewHolder(
            ViewHolderItemBillParmsBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: BillFetchParamViewHolder, position: Int) {

        holder.binding.lblFetchBillParms.hint = categoriesList[position].paramname
        var model: ParameItem = categoriesList[position]
        holder.binding.etFetchBillParms.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }


            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                model.fieldInputValue = holder.binding.etFetchBillParms.text.toString()

                listener.onGetRecords(position,model)
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })



    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    interface OnListDataLisListener {
        fun onGetRecords(position: Int, item: ParameItem)

    }
}