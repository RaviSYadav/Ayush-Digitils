package com.payment.aeps.fragment.invoice

import android.Manifest
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.crazylegend.viewbinding.viewBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.payment.aeps.R
import com.payment.aeps.base.AepsBaseFragment
import com.payment.aeps.databinding.FragmentAepsInvoiceBinding
import com.payment.aeps.remote.AepsInvoiceData
import com.payment.aeps.utils.captureAndShareAepsScreenshot


class AEPSInvoiceFragment : AepsBaseFragment(R.layout.fragment_aeps_invoice) {

    private val invoiceList: MutableList<AepsInvoiceData> = mutableListOf()

    val binding by viewBinding(FragmentAepsInvoiceBinding::bind)
    private val args: AEPSInvoiceFragmentArgs by navArgs()
    private val viewModel by viewModels<AEPSInvoiceViewModel>()

    private lateinit var invoiceAdapter: AepsInvoiceAdapter
    private lateinit var statementAdapter: AepsStatementAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        initView()
        initClick()
        observer()
    }

    private fun prepareRecyclerView() {
        invoiceAdapter = AepsInvoiceAdapter()
        binding.rvInvoice.apply {
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
            adapter = invoiceAdapter
        }
        statementAdapter = AepsStatementAdapter()
        binding.rvInvoiceStatement.apply {
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
            adapter = statementAdapter
        }

    }

    private fun initView() {
        binding.lblTransaction.text = "Detail Transactions"
        args.aepsInvoice.let { invoiceData ->
            invoiceData.status?.let {
                invoiceList.add(AepsInvoiceData("Status", it))
                setStatus(it)
            }

            invoiceData.statuscode?.let {
                invoiceList.add(AepsInvoiceData("Status", it))
                setStatus(it)
            }
            invoiceData.rrn?.let {
                invoiceList.add(AepsInvoiceData("RRN", it))
            }
            invoiceData.id?.let {
                invoiceList.add(AepsInvoiceData("Transaction ID", it.toString()))
            }
            invoiceData.txnid?.let {
                invoiceList.add(AepsInvoiceData("Transaction ID", it.toString()))
            }
            invoiceData.transactionType?.let {
                invoiceList.add(AepsInvoiceData("Type", it))

            }
            invoiceData.title?.let {
                invoiceList.add(AepsInvoiceData("Title", it))
                binding.lblHeader.text = "$it Invoice"
            }
            invoiceData.aadhar?.let {
                invoiceList.add(AepsInvoiceData("Aadhaar No", it))
            }
            invoiceData.balance?.let {
                invoiceList.add(
                    AepsInvoiceData(
                        "Balance",
                        getString(R.string.full_name, resources.getString(R.string.Rs), it)
                    )
                )
            }
            invoiceData.amount?.let {
                invoiceList.add(
                    AepsInvoiceData(
                        "Amount",
                        getString(
                            R.string.full_name,
                            resources.getString(R.string.Rs),
                            it.toString()
                        )
                    )
                )

            }
            if (invoiceData.transactionType == "BE" || invoiceData.transactionType == "MS") {
                binding.lblAmount.text =
                    getString(R.string.full_name, getString(R.string.Rs), invoiceData.balance
                        ?: "0")
            }else{
                binding.lblAmount.text =
                    getString(R.string.full_name, getString(R.string.Rs), invoiceData.amount ?: "0")
            }
            invoiceData.aadhar?.let {
                invoiceList.add(AepsInvoiceData("Aadhaar No", it))
            }
            invoiceData.bank?.let {
                invoiceList.add(AepsInvoiceData("Bank", it))
            }
            invoiceData.benename?.let {
                invoiceList.add(AepsInvoiceData("Benename", it))
            }
            invoiceData.account?.let {
                invoiceList.add(AepsInvoiceData("Account", it))
            }
            invoiceData.date?.let {
                invoiceList.add(AepsInvoiceData("Date", it))
            }
            viewModel.setInvoiceList(invoiceList)

            args.aepsInvoice.transactionType?.let {
                if (it == "MS") {
                    args.aepsInvoice.data?.let {invoiceData->
                        if (invoiceData.isNotEmpty()) {
                            binding.lblDate.text = "Date"
                            binding.lblType.text = "Type"
                            binding.lblStatementAmount.text = "Amount"
                            statementAdapter.setStatement(invoiceData)
                        }
                    }

                }
            }
        }

    }
    private fun initClick(){
        binding.ivBack.setOnClickListener {
        findNavController().navigateUp()
        }

        binding.ivShare.setOnClickListener {
            getPermission(binding.nestedScroll)
        }
    }


    private fun setStatus(status: String) {
        if (status.equals("success", ignoreCase = true) || status.equals("TXN", ignoreCase = true)) {
            binding.statusContainer.setBackgroundResource(R.drawable.ic_aeps_success)
            binding.ivStatus.setImageResource(R.drawable.ic_aeps_txn_success)
            binding.lblSubtitle.text = getString(R.string.your_payment_has_been_successfully_done)
            binding.lblMessage.visibility = View.GONE
        } else {
            binding.statusContainer.setBackgroundResource(R.drawable.ic_aeps_failed)
            binding.ivStatus.setImageResource(R.drawable.ic_aeps_txn_failed)
            binding.lblSubtitle.text = getString(R.string.transaction_failed)
            binding.lblMessage.visibility = View.VISIBLE
            binding.lblMessage.text = args.aepsInvoice.message?:""
        }

    }

    private fun observer() {
        viewModel.getInvoiceList().observe(viewLifecycleOwner, Observer {
            invoiceAdapter.setInvoice(it)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {

                val action = AEPSInvoiceFragmentDirections.actionAEPSInvoiceFragmentToAEPSHomeFragment()
                findNavController().navigate(action)
                return true
            }
        }
        return super.onOptionsItemSelected(item)

    }

    private fun getPermission(view: View) {
        Dexter.withContext(requireContext())
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    report.let {

                        if (report.areAllPermissionsGranted()) {
                            view.captureAndShareAepsScreenshot()
                        } else {
                            Toast.makeText(requireContext(), "Please Grant Permissions to use the app", Toast.LENGTH_SHORT).show()
                        }

                    }
                }
                override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest?>?, token: PermissionToken?) {
                    token?.continuePermissionRequest()
                }


            }).withErrorListener{
                Toast.makeText(requireContext(), it.name, Toast.LENGTH_SHORT).show()
            }.check()

    }


}