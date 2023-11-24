package com.payment.ayushdigitils.ui.fragments.dmt.invoice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.databinding.FragmentPaysprintDmtInvoiceBinding
import com.payment.ayushdigitils.databinding.ViewHolderItemBeneficiaryInvoiceBinding
import com.payment.ayushdigitils.databinding.ViewHolderItemTransactionInvoiceDataBinding
import com.payment.ayushdigitils.ui.base.BaseFragment
import com.payment.ayushdigitils.ui.fragments.beneficiary.DMTInvoiceAdapter
import java.text.SimpleDateFormat
import java.util.Date


class PaySprintDmtInvoiceFragment : BaseFragment(R.layout.fragment_paysprint_dmt_invoice) {
    private var _binding: FragmentPaysprintDmtInvoiceBinding? = null
    private val binding get() = _binding!!

    private val args: PaySprintDmtInvoiceFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaysprintDmtInvoiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClick()
    }

    override fun initView() {
        binding.lblRemitterName.text = getString(
            R.string.remitter_full_name,
            args.remitter.fname,
            args.remitter.lname
        )
        binding.lblRemitterNum.text = getString(
            R.string.remitter_full_name,
            "+91",
            args.remitter.mobile
        )
        binding.lblTotalAmount.text = "Total : ${getString(
            R.string.remitter_full_name,
            requireContext().getString(R.string.Rs),
            args.totalAmount
            )}"


        /*if (args.transaction?.message!!.isNotEmpty()){
            binding.lblMessage.visible()
            binding.lblMessage.text =  "args.transaction.message"
        }else{
            binding.lblMessage.gone()
        }*/




        setBeneficiaryData()
        setBankData()
        setTransactionData()

    }

    override fun initClick() {

        binding.ivBak.setOnClickListener {
            findNavController().navigateUp()
        }

    }


    private fun setBeneficiaryData() {
        val setBeneficiaryData = listOf(
            DMTInvoiceModel(
                key = "Name",
                value = args.beneficiary.name
            )
        )

        val recyclerView = binding.recBeneficiary
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        val adapter = DMTInvoiceAdapter(
            layoutInflater = { inflater, parent, attachToRoot ->
                // Specify the layout you want to use here
                ViewHolderItemBeneficiaryInvoiceBinding.inflate(inflater, parent, attachToRoot)
            },

            bindItem = { binding, items, position ->
                // Bind data to the layout here based on the position
                when (binding) {
                    is ViewHolderItemBeneficiaryInvoiceBinding -> {
                        val item =
                            items[position] as DMTInvoiceModel // Replace with your data model type
                        // Bind other views as
                        binding.lblKey.text = item.key
                        binding.lblValue.text = item.value
                    }
                    // Add more cases for other ViewBinding types if necessary
                }
            }
        )

        if (setBeneficiaryData.isEmpty())


        recyclerView.adapter = adapter

        adapter.submitList(setBeneficiaryData)
    }

    private fun setBankData() {
        val setBeneficiaryData = listOf(
            DMTInvoiceModel(
                key = "Bank",
                value = args.beneficiary.bankname
            ),
            DMTInvoiceModel(
                key = "IFSC Code",
                value = args.beneficiary.ifsc
            ),
            DMTInvoiceModel(
                key = "Txn Date",
                value = COMMON_DATE_FORMAT.format(Date())
            ),
        )

        val recyclerView = binding.recBank
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        val adapter = DMTInvoiceAdapter(
            layoutInflater = { inflater, parent, attachToRoot ->
                // Specify the layout you want to use here
                ViewHolderItemBeneficiaryInvoiceBinding.inflate(inflater, parent, attachToRoot)
            },

            bindItem = { binding, items, position ->
                // Bind data to the layout here based on the position
                when (binding) {
                    is ViewHolderItemBeneficiaryInvoiceBinding -> {
                        val item =
                            items[position] as DMTInvoiceModel // Replace with your data model type
                        // Bind other views as
                        binding.lblKey.text = item.key
                        binding.lblValue.text = item.value
                    }
                    // Add more cases for other ViewBinding types if necessary
                }
            }
        )

        recyclerView.adapter = adapter

        adapter.submitList(setBeneficiaryData)
    }

    private fun setTransactionData() {


        val recyclerView = binding.recTransaction
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        val adapter = DMTInvoiceAdapter(
            layoutInflater = { inflater, parent, attachToRoot ->
                // Specify the layout you want to use here
                ViewHolderItemTransactionInvoiceDataBinding.inflate(inflater, parent, attachToRoot)
            },

            bindItem = { binding, items, position ->
                // Bind data to the layout here based on the position
                when (binding) {
                    is ViewHolderItemTransactionInvoiceDataBinding -> {
                        val item =
                            items[position] as DMTTransactionInvoiceModel // Replace with your data model type
                        // Bind other views as
                        binding.lblPayId.text = item.orderId
                        binding.lblUtr.text = item.utr
                        binding.lblStatus.text = item.status
                        binding.lblAmount.text = item.amount
                        changeColor(item.status, binding.lblStatus)
                    }

                    else -> {

                    }
                    // Add more cases for other ViewBinding types if necessary
                }
            }
        )

        recyclerView.adapter = adapter


        args.transaction.data?.map { it1 ->
            val transactionData = listOf(
                DMTTransactionInvoiceModel(
                    message = it1.data?.message ?: "NA",
                    status = it1.data?.statuscode ?: "NA",
                    amount = it1.amount.toString(),
                    orderId = it1.data?.payid.toString() ?: "NA",
                    utr = it1.data?.rrn.toString() ?: "NA"

                )
            )


            adapter.submitList(transactionData)
        }



    }

    private fun changeColor(status: String, tvTxnStatus: AppCompatTextView) {
        when (status) {
            "success", "txn", "TXN", "approved" -> {
                tvTxnStatus.setTextColor(requireContext().resources.getColor(R.color.textGreen))
                tvTxnStatus.text = "Success"
            }

            "pending", "TUP" -> {
                tvTxnStatus.setTextColor(requireContext().resources.getColor(R.color.text_yellow))
                tvTxnStatus.text = "Pending"
            }

            "rejected", "failed", "failure", "fail", "TXF", "Failed" -> {
                tvTxnStatus.setTextColor(requireContext().resources.getColor(R.color.textRed))
                tvTxnStatus.text = "Failed"
            }

            else -> tvTxnStatus.setTextColor(requireContext().resources.getColor(R.color.textBlack))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        val COMMON_DATE_FORMAT = SimpleDateFormat("dd-MM-yyyy")
    }

}