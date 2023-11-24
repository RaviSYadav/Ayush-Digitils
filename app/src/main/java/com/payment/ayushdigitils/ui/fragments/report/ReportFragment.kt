package com.payment.ayushdigitils.ui.fragments.report

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.payment.ayushdigitils.R
import com.payment.ayushdigitils.databinding.FragmentReportBinding
import com.payment.ayushdigitils.ui.base.BaseFragment
import com.payment.ayushdigitils.ui.fragments.report.aeps_fund_report.AepsFundReportFragment
import com.payment.ayushdigitils.ui.fragments.report.aeps_report.AEPSReportFragment
import com.payment.ayushdigitils.ui.fragments.report.bill_report.BillReportFragment
import com.payment.ayushdigitils.ui.fragments.report.dmt_report.DMTReportFragment
import com.payment.ayushdigitils.ui.fragments.report.lic_report.LICReportFragment
import com.payment.ayushdigitils.ui.fragments.report.matm_report.MATMReportFragment
import com.payment.ayushdigitils.ui.fragments.report.recharge.RechargeReportFragment
import com.payment.ayushdigitils.ui.fragments.report.wallet_fund_report.WalletFundReportFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.payment.ayushdigitils.ui.fragments.report.aeps_wallet.AepsWalletReportFragment
import com.payment.ayushdigitils.ui.fragments.report.main_wallet.MainWalletReportFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class ReportFragment : BaseFragment(R.layout.fragment_report) {

    private var _binding:FragmentReportBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<ReportsViewModel>()
    private val titles = arrayOf("Aeps", "Billpay", "Money Transfer","Recharge","MATM","Wallet Fund","AEPS Fund","Lic Bill Pay","Main Wallet","Aeps Wallet")
    private val args : ReportFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentReportBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClick()
    }

    override fun initView() {


        binding.viewPager2.currentItem = 2
        val adapter = ViewPagerFragmentAdapter(this, titles)
        binding.viewPager2.adapter = adapter
        binding.viewPager2.offscreenPageLimit = 1
        binding.tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        TabLayoutMediator(
            binding.tabLayout,
            binding.viewPager2
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = titles[position]
//            tab.setIcon(icons[position])
        }.attach()



        binding.viewPager2.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.setTabIndex(position)
            }
        })

        viewModel.setTabIndex(
            when (args.tabName) {
                "Aeps" ->0
                "Billpay" ->1
                "Money Transfer" ->2
                "Recharge" ->3
                "MATM" ->4
                "Main Wallet" ->5
                "Aeps Wallet" ->6
                "Matm Wallet" ->7
                "Wallet Fund" ->8
                "AEPS Fund" ->9
                "AEPS Fund" ->10
                "Lic Bill Pay" ->11
                "NSDL" ->12
                //getString(R.string.args_active_tab_history) -> 2
                else -> 0
            }
        )

        if (binding.tabLayout.tabCount > viewModel.getTabIndex()) {
//            binding.myShowsPager.postDelayed({
            binding.viewPager2.currentItem = viewModel.getTabIndex()
//            }, 100)
        }

        binding.viewPager2.isUserInputEnabled = false
    }

    override fun initClick() {
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private class ViewPagerFragmentAdapter(
        context: Fragment,
        var titles: Array<String>
    ) :
        FragmentStateAdapter(context) {
        override fun createFragment(position: Int): Fragment {
            when (position) {

                0 -> return AEPSReportFragment()
                1 -> return BillReportFragment()
                2 -> return DMTReportFragment()
                3 -> return RechargeReportFragment()
                4 -> return MATMReportFragment()
                5 -> return WalletFundReportFragment()
                6 -> return AepsFundReportFragment()
                7 -> return LICReportFragment()
                8 -> return MainWalletReportFragment()
                9 -> return AepsWalletReportFragment()
            }
            return AEPSReportFragment()
        }

        override fun getItemCount(): Int {
            return titles.size
        }
    }
}