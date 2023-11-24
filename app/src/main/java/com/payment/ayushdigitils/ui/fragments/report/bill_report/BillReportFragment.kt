package com.payment.ayushdigitils.ui.fragments.report.bill_report

import android.Manifest
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.payment.ayushdigitils.NavGraphInvoiceDirections
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.data.remote.RechargeReportData
import com.payment.ayushdigitils.databinding.FragmentAepsReportBinding
import com.payment.ayushdigitils.ex.captureAndShareScreenshot
import com.payment.ayushdigitils.ui.base.BaseFragment
import com.payment.ayushdigitils.ui.fragments.invoice.InvoiceModel
import com.payment.ayushdigitils.ui.fragments.invoice.InvoiceViewModel
import com.payment.ayushdigitils.ui.fragments.report.filter.FiltersFragment
import com.payment.ayushdigitils.vo.Resource
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class BillReportFragment : BaseFragment(R.layout.fragment_aeps_report),BillReportsAdapter.OnReportClickListener, FiltersFragment.FiltersDialogListener {

    private  var _binding: FragmentAepsReportBinding? = null
    private val binding get() = _binding!!
    private val invoiceList: MutableList<InvoiceModel> = mutableListOf()
    private val invoiceViewModel by sharedViewModel<InvoiceViewModel>()
    private val viewModel by viewModel<BillReportsViewModel>()

    private lateinit  var reportsAdapter: BillReportsAdapter


    var pastVisiblesItems = 0
    var visibleItemCount:Int = 0
    var totalItemCount:Int = 0

    private var PAGE_COUNT = 1
    private var MAX_PAGE_COUNT = 1


    private var startDate = ""
    private var todate = ""
    private var searchtext = ""
    private var status = ""

    private lateinit var dialog: FiltersFragment
    lateinit var listener: FiltersFragment.FiltersDialogListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listener = this
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAepsReportBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClick()
    }
    override fun initView() {
        observer()
        reportsAdapter = BillReportsAdapter(this)
        val layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        binding.recyclerTrack.apply {
            this.layoutManager = layoutManager
            adapter = reportsAdapter
        }

        binding.recyclerTrack.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) { //check for scroll down
                    visibleItemCount = layoutManager.getChildCount()
                    totalItemCount = layoutManager.getItemCount()
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition()
                    if (viewModel.showLoader.value == true) {
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            viewModel.showLoader.value = false
                            if (PAGE_COUNT < MAX_PAGE_COUNT) {
                                PAGE_COUNT++
                                getReports()


                            }
                            viewModel.showLoader.value = true
                        }
                    }
                }
            }
        })
    }

    override fun initClick() {
        binding.imgFilter.setOnClickListener {
            dialog = FiltersFragment(listener,status,startDate,todate)
            dialog.show(this.parentFragmentManager, "tag")
        }
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                searchtext = s.toString()
                getReports()
            }

        })

    }

    private fun observer() {
        viewModel.showLoader.observe(viewLifecycleOwner, Observer {
            progressBarVisibility?.setVisibility(if (it) View.VISIBLE else View.INVISIBLE)
        })
        viewModel.reportItem.observe(viewLifecycleOwner, Observer {

            when (it) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    Timber.d("aeps reports Res---- : ${it.data}")
                    it.data?.let { data ->
                        viewModel.showLoader.value = false
                        if (data.statuscode == "TXN") {
                            MAX_PAGE_COUNT = data.pages
                            data.data.let {res->

                                setUpReport(res)

                            }
                        } else {
                            viewModel.toastMsg.value = data.message
                        }


                    }
                }

                is Resource.Error -> {
                    viewModel.showLoader.value = false
                    Timber.e(it.exception, "bank ex:")
                    viewModel.toastMsg.value = "Something went wrong"
                }
            }
        })

    }
    private fun setUpReport(data: List<RechargeReportData>) {
        viewModel.showLoader.value = false
        if (data.equals("")) {
            binding.recyclerTrack.gone()
            binding.errorContainer.visible()
        } else {
            reportsAdapter.setReports(data)


            binding.recyclerTrack.visible()
            binding.errorContainer.gone()

        }
    }
    override fun onResume() {
        super.onResume()
        getReports()
    }
    private fun getReports(){
        val jsonObject = JSONObject().apply {
            put("user_id", prefs.getUserId())
            put("apptoken", prefs.getAppToken())
            put("start", "$PAGE_COUNT")
            put("type", "billpaystatement")
            if (startDate != ""){
                put("startDate",startDate)
            }
            if (todate != ""){
                put("todate",todate)
            }
            if (searchtext != ""){
                put("searchtext",searchtext)
            }
            if (status != ""){
                put("status",status)
            }

        }
        viewModel.requestReportItem.value= jsonObject.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    override fun onShareClick(position: Int, reports: RechargeReportData, view: View) {
       getPermission(view)
    }

    override fun onInvoiceClick(position: Int, reports: RechargeReportData) {

        invoiceViewModel.clearInvoiceList()
        invoiceList.add(InvoiceModel("Id", reports.id))
        invoiceList.add(InvoiceModel("Txnid", reports.txnid))
        invoiceList.add(InvoiceModel("Ref No", reports.refno ?: "NA"))
        invoiceList.add(InvoiceModel("Number", reports.number ?: "NA"))
        invoiceList.add(InvoiceModel("Mobile", reports.mobile ?: "NA"))
        invoiceList.add(InvoiceModel("Amount", getString(R.string.remitter_full_name, getString(R.string.Rs), reports.amount)))
        invoiceList.add(InvoiceModel("Charge", getString(R.string.remitter_full_name, getString(R.string.Rs), reports.charge)))
        invoiceList.add(InvoiceModel("Balance", getString(R.string.remitter_full_name, getString(R.string.Rs), reports.balance)))
        invoiceList.add(InvoiceModel("Profit", getString(R.string.remitter_full_name, getString(R.string.Rs), reports.profit?:"0")))
        invoiceList.add(InvoiceModel("R Type", reports.rtype ?: "NA"))
        invoiceList.add(InvoiceModel("Via", reports.via ?: "NA"))
        invoiceList.add(InvoiceModel("Trans Type", reports.trans_type ?: "NA"))
        invoiceList.add(InvoiceModel("Product", reports.product ?: "NA"))
        invoiceList.add(InvoiceModel("Provider Name", reports.providername ?: "NA"))
        invoiceList.add(InvoiceModel("Status", reports.status ?: "NA"))

        invoiceViewModel.setInvoiceList(invoiceList)

        val action = NavGraphInvoiceDirections.actionGlobalInvoiceFragment(reports.status?:"success","")
        findNavController().navigate(action)
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

    override fun onFilterClick(status: String?, fromDate: String, toDate: String) {
        this.status = status?:""
        startDate = fromDate?:""
        todate = toDate?:""
        PAGE_COUNT = 1
        MAX_PAGE_COUNT = 1

        getReports()

        dialog.dismiss()
    }
}