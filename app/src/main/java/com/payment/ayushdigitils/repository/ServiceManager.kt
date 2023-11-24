package com.payment.ayushdigitils.repository

import androidx.lifecycle.LiveData
import com.payment.ayushdigitils.data.remote.AEPSFundReportResponse
import com.payment.ayushdigitils.data.remote.AEPSReportResponse
import com.payment.ayushdigitils.data.remote.Aadhaar2FaResponse
import com.payment.ayushdigitils.data.remote.BalanceResponse
import com.payment.ayushdigitils.data.remote.BillParamsResponse
import com.payment.ayushdigitils.data.remote.BillPaymentResponse
import com.payment.ayushdigitils.data.remote.BillValidateResponse
import com.payment.ayushdigitils.data.remote.ChangePasswordResponse
import com.payment.ayushdigitils.data.remote.FundBankResponse
import com.payment.ayushdigitils.data.remote.FundRequestResponse
import com.payment.ayushdigitils.data.remote.FundUserBankResponse
import com.payment.ayushdigitils.data.remote.GenerateTPinResponse
import com.payment.ayushdigitils.data.remote.GetTPinResponse
import com.payment.ayushdigitils.data.remote.LoginResponse
import com.payment.ayushdigitils.data.remote.LogoutResponse
import com.payment.ayushdigitils.data.remote.MAtmInitiateResponse
import com.payment.ayushdigitils.data.remote.PaySprintDmtBanksResponse
import com.payment.ayushdigitils.data.remote.PaySprintDmtBeneficiaryVerifyResponse
import com.payment.ayushdigitils.data.remote.PaySprintDmtRegistrationResponse
import com.payment.ayushdigitils.data.remote.PaySprintDmtVerifyResponse
import com.payment.ayushdigitils.data.remote.PaySprintMoneyTransactionResponse
import com.payment.ayushdigitils.data.remote.PrepaidProvidersResponse
import com.payment.ayushdigitils.data.remote.RechargePayResponse
import com.payment.ayushdigitils.data.remote.RechargePlansResponse
import com.payment.ayushdigitils.data.remote.RechargeReportResponse
import com.payment.ayushdigitils.data.remote.SignupResponse
import com.payment.ayushdigitils.vo.Resource
import okhttp3.RequestBody
import okhttp3.ResponseBody

interface ServiceManager {

    fun requestRegister(data: RequestBody): LiveData<Resource<SignupResponse>>
    fun requestLogin(data: RequestBody): LiveData<Resource<LoginResponse>>
    fun requestLogOut(data: RequestBody): LiveData<Resource<LogoutResponse>>
    fun requestGetBalance(data: RequestBody): LiveData<Resource<BalanceResponse>>
    fun requestSearchPaySprintDmtVerify(data: RequestBody): LiveData<Resource<PaySprintDmtVerifyResponse>>
    fun requestSearchPaySprintBeneficiaryDelete(data: RequestBody): LiveData<Resource<PaySprintDmtVerifyResponse>>
    fun requestPaySprintDmtRegistration(data: RequestBody): LiveData<Resource<PaySprintDmtRegistrationResponse>>
    fun requestPaySprintDmtGetBank(type: String): LiveData<Resource<PaySprintDmtBanksResponse>>
    fun requestPaySprintDmtAddVerifyBeneficiary(data: RequestBody): LiveData<Resource<PaySprintDmtBeneficiaryVerifyResponse>>
    fun requestPaySprintDmtMoneyTransaction(data: RequestBody): LiveData<Resource<PaySprintMoneyTransactionResponse>>
    fun requestPaySprintMAtmInitiate(data: RequestBody): LiveData<Resource<MAtmInitiateResponse>>
    fun requestSecureMAtmInitiate(data: RequestBody): LiveData<Resource<MAtmInitiateResponse>>
    fun requestRechargeProviders(data: RequestBody): LiveData<Resource<PrepaidProvidersResponse>>
    fun requestRechargePay(data: RequestBody): LiveData<Resource<RechargePayResponse>>
    fun requestBillParams(data: RequestBody): LiveData<Resource<BillParamsResponse>>
    fun requestBillValidate(data: RequestBody): LiveData<Resource<BillValidateResponse>>
    fun requestBillPayment(data: RequestBody): LiveData<Resource<BillPaymentResponse>>
    fun requestOnBoarding(data: RequestBody): LiveData<Resource<SignupResponse>>
    fun requestAadhaar2Fa(data: RequestBody): LiveData<Resource<Aadhaar2FaResponse>>
    fun requestFundUserBank(data: RequestBody): LiveData<Resource<FundUserBankResponse>>
    fun requestFundUserRequest(data: RequestBody): LiveData<Resource<FundRequestResponse>>
    fun requestFundBank(data: RequestBody): LiveData<Resource<FundBankResponse>>
    fun fetchAepsReport(data: RequestBody): LiveData<Resource<AEPSReportResponse>>
    fun fetchRechargeReport(data: RequestBody): LiveData<Resource<RechargeReportResponse>>
    fun fetchAepsFundReport(data: RequestBody): LiveData<Resource<AEPSFundReportResponse>>
    fun fetchRechargePlans(data: RequestBody): LiveData<Resource<RechargePlansResponse>>
    fun requestChangePassword(data: RequestBody): LiveData<Resource<ChangePasswordResponse>>
    fun requestGetTOtp(data: RequestBody): LiveData<Resource<GetTPinResponse>>
    fun requestGenerateTPin(data: RequestBody): LiveData<Resource<GenerateTPinResponse>>

}