package com.payment.ayushdigitils.ui.fragments.report

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.databinding.FragmentHistoryBinding


class HistoryFragment : Fragment(R.layout.fragment_history) {
    private var _binding: FragmentHistoryBinding? = null
    // This property is only valid between onCreateView and

    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var reportsAdapter: ReportsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareCategoriesRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun prepareCategoriesRecyclerView() {
        reportsAdapter = ReportsAdapter()
        binding.rvChooseLanguage.apply {
            layoutManager = GridLayoutManager(context,1, GridLayoutManager.VERTICAL,false)
            adapter = reportsAdapter
        }

//        binding.rvChooseLanguage.addItemDecoration(GridDividerItemDecoration())

        reportsAdapter.setCategoryList(DemoReportData.reports)
    }


}