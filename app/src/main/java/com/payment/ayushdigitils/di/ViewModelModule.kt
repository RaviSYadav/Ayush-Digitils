package com.payment.ayushdigitils.di

import com.payment.ayushdigitils.ui.fragments.aadhar2fa.Aadhaar2FaViewModel
import com.payment.ayushdigitils.ui.fragments.bank.BanksViewModel
import com.payment.ayushdigitils.ui.fragments.beneficiary.BeneficiaryViewModel
import com.payment.ayushdigitils.ui.fragments.beneficiary.add.AddBeneficiaryViewModel
import com.payment.ayushdigitils.ui.fragments.bills.BillParamsViewModel
import com.payment.ayushdigitils.ui.fragments.bills.ShareBillViewModel
import com.payment.ayushdigitils.ui.fragments.bills.confirmation.BillPaymentViewModel
import com.payment.ayushdigitils.ui.fragments.dashboard.HomeViewModel
import com.payment.ayushdigitils.ui.fragments.dmt.DMTVerifyViewModel
import com.payment.ayushdigitils.ui.fragments.invoice.InvoiceViewModel
import com.payment.ayushdigitils.ui.fragments.mtam.MAtmViewModel
import com.payment.ayushdigitils.ui.fragments.operator.ProviderViewModel
import com.payment.ayushdigitils.ui.fragments.payout.aeps.AEPSFundViewModel
import com.payment.ayushdigitils.ui.fragments.payout.wallet.WalletFundViewModel
import com.payment.ayushdigitils.ui.fragments.prepaid.PrepaidViewModel
import com.payment.ayushdigitils.ui.fragments.prepaid.contact.ContactViewModel
import com.payment.ayushdigitils.ui.fragments.profile.password.ChangePasswordViewModel
import com.payment.ayushdigitils.ui.fragments.profile.tpin.ChangeTPinViewModel
import com.payment.ayushdigitils.ui.fragments.report.ReportsViewModel
import com.payment.ayushdigitils.ui.fragments.report.aeps_fund_report.AepsFundReportsViewModel
import com.payment.ayushdigitils.ui.fragments.report.aeps_report.AepsReportsViewModel
import com.payment.ayushdigitils.ui.fragments.report.bill_report.BillReportsViewModel
import com.payment.ayushdigitils.ui.fragments.report.dmt_report.DMTReportsViewModel
import com.payment.ayushdigitils.ui.fragments.report.lic_report.LICReportsViewModel
import com.payment.ayushdigitils.ui.fragments.report.matm_fund_report.MATMFundReportsViewModel
import com.payment.ayushdigitils.ui.fragments.report.matm_report.MATMReportsViewModel
import com.payment.ayushdigitils.ui.fragments.report.nsdl_report.NSDLReportsViewModel
import com.payment.ayushdigitils.ui.fragments.report.recharge.RechargeReportsViewModel
import com.payment.ayushdigitils.ui.fragments.report.wallet_fund_report.WalletFundReportsViewModel
import com.payment.ayushdigitils.ui.fragments.transection.MoneyTransactionViewModel
import com.payment.ayushdigitils.ui.fragments.browseplan.RechargePlansViewModel
import com.payment.ayushdigitils.ui.fragments.profile.setting.SettingsViewModel
import com.payment.ayushdigitils.ui.fragments.report.main_wallet.MainWalletReportsViewModel
import com.payment.ayushdigitils.ui.signin.LoginViewModel
import com.payment.ayushdigitils.ui.signup.SignupViewModel
import com.payment.ayushdigitils.ui.web.WebViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Rinav Gangar <rinav.dev> on 21/10/20.
 * Agrahyah Technologies Pvt Ltd
 * rinav4all@gmail.com
 */
val viewModelModule = module {
    viewModel { SignupViewModel() }
    viewModel { LoginViewModel() }
    viewModel { HomeViewModel() }
    viewModel { SettingsViewModel() }
    viewModel { WebViewModel() }
    viewModel { MAtmViewModel() }
    viewModel { DMTVerifyViewModel() }
    viewModel { BeneficiaryViewModel() }
    viewModel { AddBeneficiaryViewModel() }
    viewModel { BanksViewModel() }
    viewModel { MoneyTransactionViewModel() }
    viewModel { PrepaidViewModel() }
    viewModel { ContactViewModel(repo = get()) }
    viewModel { ProviderViewModel() }
    viewModel { BillParamsViewModel() }
    viewModel { BillPaymentViewModel() }
    viewModel { Aadhaar2FaViewModel() }
    viewModel { ReportsViewModel() }
    viewModel { AEPSFundViewModel() }
    viewModel { ShareBillViewModel() }
    viewModel { InvoiceViewModel() }
    viewModel { WalletFundViewModel() }
    viewModel { AepsFundReportsViewModel(repo = get()) }
    viewModel { AepsReportsViewModel(repo = get()) }
    viewModel { BillReportsViewModel(repo = get()) }
    viewModel { DMTReportsViewModel(repo = get()) }
    viewModel { LICReportsViewModel(repo = get()) }
    viewModel { MATMFundReportsViewModel(repo = get()) }
    viewModel { MATMReportsViewModel(repo = get()) }
    viewModel { NSDLReportsViewModel(repo = get()) }
    viewModel { RechargeReportsViewModel(repo = get()) }
    viewModel { WalletFundReportsViewModel(repo = get()) }
    viewModel { MainWalletReportsViewModel() }
    viewModel { RechargePlansViewModel() }
    viewModel { ChangePasswordViewModel() }
    viewModel { ChangeTPinViewModel() }
}
