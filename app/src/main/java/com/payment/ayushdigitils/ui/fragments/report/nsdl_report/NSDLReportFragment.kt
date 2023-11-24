package com.payment.ayushdigitils.ui.fragments.report.nsdl_report

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.data.remote.RechargeReportData
import com.payment.ayushdigitils.databinding.FragmentAepsReportBinding
import com.payment.ayushdigitils.ex.captureAndShareScreenshot
import com.payment.ayushdigitils.ui.base.BaseFragment
import com.payment.ayushdigitils.ui.fragments.invoice.InvoiceModel
import com.payment.ayushdigitils.ui.fragments.invoice.InvoiceViewModel
import com.payment.ayushdigitils.ui.fragments.report.filter.FiltersFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NSDLReportFragment : BaseFragment(R.layout.fragment_aeps_report),NSDLReportsAdapter.OnReportClickListener, FiltersFragment.FiltersDialogListener {

    private  var _binding: FragmentAepsReportBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<NSDLReportsViewModel>()

    private val invoiceList: MutableList<InvoiceModel> = mutableListOf()
    private val invoiceViewModel by sharedViewModel<InvoiceViewModel>()

    private lateinit  var reportsAdapter: NSDLReportsAdapter

    private lateinit var dialog: FiltersFragment
    lateinit var listener: FiltersFragment.FiltersDialogListener

    private var startDate = ""
    private var todate = ""
    private var searchtext = ""
    private var status = ""

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
        reportsAdapter = NSDLReportsAdapter(this)
        val layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        binding.recyclerTrack.apply {
            this.layoutManager = layoutManager
            adapter = reportsAdapter
        }
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
//                viewModel.searchtext.value = s.toString()
            }

        })

    }

    private fun observer() {
        viewModel.showLoader.observe(viewLifecycleOwner, Observer {
            progressBarVisibility?.setVisibility(if (it) View.VISIBLE else View.INVISIBLE)
        })
        viewModel.reportItem.observe(viewLifecycleOwner, Observer {
            it?.let {
                setUpReport(it)
            }


        })

    }

    private fun setUpReport(data: PagingData<RechargeReportData>) {
        viewModel.showLoader.value = false
        if (data.equals("")) {
            binding.recyclerTrack.gone()
//            binding.txtNoData.visible()
        } else {

            lifecycleScope.launch {
                reportsAdapter.submitData(viewLifecycleOwner.lifecycle,data)

            }
            binding.recyclerTrack.visible()

        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    override fun onShareClick(position: Int, reports: RechargeReportData, view: View) {
        view.captureAndShareScreenshot()
    }

    override fun onInvoiceClick(position: Int, reports: RechargeReportData) {
    }

    override fun onFilterClick(status: String?, fromDate: String, toDate: String) {

    }

}