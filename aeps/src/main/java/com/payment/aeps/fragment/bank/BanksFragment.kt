package com.payment.aeps.fragment.bank

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.crazylegend.viewbinding.viewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.payment.aeps.R
import com.payment.aeps.databinding.FragmentBanksBinding
import com.payment.aeps.preferences.AepsPrefRepository
import com.payment.aeps.remote.NetworkAepsBank
import org.json.JSONObject


class BanksFragment(listener: BankDialogListener) : BottomSheetDialogFragment(R.layout.fragment_banks),BankAdapter.OnBankClickListener {

    private var mBottomSheetListener: BankDialogListener?=null
    private val viewModel by viewModels<AepsBankViewModel>()

    val binding by viewBinding(FragmentBanksBinding::bind)

    private lateinit var prefRepository :AepsPrefRepository

    private lateinit var bankAdapter: BankAdapter

    init {
        this.mBottomSheetListener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setContentView(R.layout.fragment_banks)

        // Set the full-screen style
        val bottomSheetBehavior = dialog.behavior
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetBehavior.peekHeight = 0

        prefRepository = AepsPrefRepository(requireContext())

        return dialog
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observerBank()
        initClick()
        val jsonObject = JSONObject().apply {
            put("user_id", prefRepository.getUserId())
            put("apptoken", prefRepository.getAppToken())
        }
        binding.progressBar.visibility = View.VISIBLE
        Log.v("getBankList","===$jsonObject")
        viewModel.getBank(jsonObject)
        prepareCategoriesRecyclerView()



    }
    private fun initClick(){
        binding.searchViewBanks.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                bankAdapter.filter.filter(newText)
                return true
            }
        })
    }
    private fun prepareCategoriesRecyclerView() {
        bankAdapter = BankAdapter(this)
        binding.rvBank.apply {
            layoutManager = GridLayoutManager(context,1, GridLayoutManager.VERTICAL,false)
            adapter = bankAdapter
        }

    }
    private fun observerBank() {
        viewModel.observeInitiateLiveData().observe(this, Observer { init ->

            Log.v("bankList","----${init.data}")
            binding.progressBar.visibility = View.GONE
            if (init.statuscode == "TXN") {
                init.data?.let { networkAepsData ->

                    networkAepsData.bankName.let {bankAeps->

                        bankAdapter.setBanks(bankAeps)
                    }


                }

            }else{
                Toast.makeText(requireContext(), ".${init.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

        /** attach listener from parent fragment */
        try {
            mBottomSheetListener = context as BankDialogListener?
        }
        catch (e: ClassCastException){
        }
    }

    interface BankDialogListener {
        fun chooseBankClick(bank: NetworkAepsBank)
    }

    override fun onBankClicked(position: Int, bank: NetworkAepsBank) {
        mBottomSheetListener?.chooseBankClick(bank)
    }
}