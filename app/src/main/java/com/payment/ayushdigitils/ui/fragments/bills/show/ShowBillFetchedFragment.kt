package com.payment.ayushdigitils.ui.fragments.bills.show

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.databinding.FragmentShowbillFetchedBinding
import com.payment.ayushdigitils.ex.transformIntoDatePicker
import com.payment.ayushdigitils.ui.fragments.bills.ShareBillViewModel
import com.payment.ayushdigitils.ui.fragments.bills.confirmation.BillProviderDetailsData
import com.payment.ayushdigitils.ui.utils.Constanse
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class ShowBillFetchedFragment : BottomSheetDialogFragment(R.layout.fragment_showbill_fetched) {

    private  var _binding: FragmentShowbillFetchedBinding?= null

    private val binding get() = _binding!!

    private val args:ShowBillFetchedFragmentArgs by navArgs()

    private val shareViewModel by sharedViewModel<ShareBillViewModel>()
    private val minDate: Long by lazy {
        val cal = Calendar.getInstance()
        cal.add(Calendar.YEAR, -100)
        cal.timeInMillis
    }
    private val maxDate: Long by lazy {
        val cal = Calendar.getInstance()
        cal.add(Calendar.YEAR, -13)
        cal.timeInMillis
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowbillFetchedBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClick()



    }

    fun initView(){
        binding.lblProviderName.text = args.providerName
        Constanse.setStaticProviderImg( args.providerName?:"",binding.ivArt, requireContext(), args.providerType?:"")
        args.billsData?.let {bill->
            binding.etConsumerName.setText(bill.customername)
            binding.etDueDate.setText(bill.duedate)
            binding.etBillAmount.setText(bill.dueamount)

            binding.tilMode.visibility =  if (bill.mode.equals("true",true)) View.VISIBLE else View.GONE



        }

    }

    fun initClick(){

        val updatedDob = binding.etDueDate.text.toString()
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val parsedCalendar = try {
            cal.time = sdf.parse(updatedDob)!!
            cal
        } catch (e: Exception) {
            Timber.d("Unable to parse date of birth")
            cal
        }
        binding.etDueDate.transformIntoDatePicker(
            requireContext(),
            "yyyy-MM-dd",
            minDate,
            maxDate,
            parsedCalendar
        )





        binding.btnSubmit.setOnClickListener {
            if (isValidate()) {
                val billers by lazy {
                    BillProviderDetailsData(
                        provider_id = args.providerId,
                        provider_name = args.providerName,
                        provider_type = args.providerType,
                        type = "payment",
                        consumer_name = binding.etConsumerName.text.toString(),
                        due_date = binding.etDueDate.text.toString(),
                        bill_amount = binding.etBillAmount.text.toString(),
                        t_pin = binding.etTransactionPin.text.toString(),
                        bu = "",
                        bbps_mode = binding.lblMode.text.toString() ?: "",


                        )
                }
                findNavController().navigate(
                    ShowBillFetchedFragmentDirections.actionShowFetchBbpsBillFragmentToConfirmedFragment(
                        billers
                    )
                )
            }
        }
    }

    private fun isValidate():Boolean{
        if (binding.etConsumerName.text.toString().isEmpty()){
            binding.etConsumerName.error = "Please enter appropriate Name"
            binding.etConsumerName.requestFocus()
            return false
        } else if (binding.etDueDate.text.toString().isEmpty()){
            binding.etDueDate.error = "Please select appropriate due date"
            binding.etDueDate.requestFocus()
            return false
        }else if (binding.etBillAmount.text.toString().isEmpty()){
            binding.etBillAmount.error = "Please enter appropriate amount"
            binding.etBillAmount.requestFocus()
            return false
        }else if (binding.etTransactionPin.text.toString().isEmpty()){
            binding.etTransactionPin.error = "Please enter appropriate t-pin"
            binding.etTransactionPin.requestFocus()
            return false
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}