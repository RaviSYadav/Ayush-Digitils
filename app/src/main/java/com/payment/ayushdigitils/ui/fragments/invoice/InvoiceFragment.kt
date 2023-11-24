package com.payment.ayushdigitils.ui.fragments.invoice

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.databinding.FragmentInvoiceBinding
import com.payment.ayushdigitils.ex.captureAndShareScreenshot
import com.payment.ayushdigitils.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel



class InvoiceFragment : BaseFragment(R.layout.fragment_invoice) {

    private var _binding: FragmentInvoiceBinding? = null
    private val binding get() = _binding!!

    private val args : InvoiceFragmentArgs  by navArgs()
    private val viewModel by sharedViewModel<InvoiceViewModel>()

    private lateinit var invoiceAdapter: InvoiceAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentInvoiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClick()


    }

    override fun initView() {
        prepareRecyclerView()
        observer()
        binding.lblSubtitle.text = args.invoiceMessage
        setStatus(args.invoiceStatus)
    }

    override fun initClick() {
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.ivShare.setOnClickListener {
            getPermission(binding.nestedScroll)
        }
    }

    private fun prepareRecyclerView() {
        invoiceAdapter = InvoiceAdapter()
        binding.rvInvoice.apply {
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
            adapter = invoiceAdapter
        }
    }

    private fun setStatus(status: String) {
        if (status.equals("success", ignoreCase = true) || status.equals("TXN", ignoreCase = true) || status.equals("approved", ignoreCase = true)) {
            binding.statusContainer.setBackgroundResource(R.drawable.ic_invoice_success)
            binding.ivStatus.setImageResource(R.drawable.ic_invoice_txn_success)
            binding.lblSubtitle.text = getString(com.payment.aeps.R.string.your_payment_has_been_successfully_done)
        } else if (status.equals("failed", ignoreCase = true)||status.equals("failure", ignoreCase = true)||status.equals("fail", ignoreCase = true)) {
            binding.statusContainer.setBackgroundResource(R.drawable.ic_invoice_failed)
            binding.ivStatus.setImageResource(R.drawable.ic_invoice_txn_failed)
            binding.lblSubtitle.text = getString(com.payment.aeps.R.string.transaction_failed)
        } else {
            binding.statusContainer.setBackgroundResource(R.drawable.ic_invoice_pending)
            binding.ivStatus.setImageResource(R.drawable.ic_invoice_txn_pending)
            binding.lblSubtitle.text = getString(com.payment.aeps.R.string.transaction_pending)
        }

    }

    private fun observer() {
        viewModel.getInvoiceList().observe(viewLifecycleOwner, Observer {
            invoiceAdapter.setInvoice(it)
        })
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
                            view.captureAndShareScreenshot()
                        } else {
                            showToast("Please Grant Permissions to use the app")
                        }

                    }
                }
                override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest?>?, token: PermissionToken?) {
                    token?.continuePermissionRequest()
                }


            }).withErrorListener{
                showToast(it.name)
            }.check()

    }

}