package com.payment.ayushdigitils.network

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
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*

interface PayApiServices {


    @POST("api/android/auth/user/register")
    fun requestRegister(@Body data: RequestBody): LiveData<ApiResponse<SignupResponse>>

    @POST("api/android/auth")
    fun requestLogin(@Body data: RequestBody): LiveData<ApiResponse<LoginResponse>>

    @POST("api/android/getbalance")
    fun requestGetBalance(@Body data: RequestBody): LiveData<ApiResponse<BalanceResponse>>

    @POST("api/android/paysprint/kyc")
    fun requestOnBoarding(@Body data: RequestBody): LiveData<ApiResponse<SignupResponse>>

    @POST("api/android/pdmt/transaction")
    fun requestSearchPaySprintDmtVerify(@Body data: RequestBody): LiveData<ApiResponse<PaySprintDmtVerifyResponse>>

    @POST("api/android/pdmt/transaction")
    fun requestSearchPaySprintBeneficiaryDelete(@Body data: RequestBody): LiveData<ApiResponse<PaySprintDmtVerifyResponse>>

    @POST("api/android/pdmt/transaction")
    fun requestPaySprintDmtRegistration(@Body data: RequestBody): LiveData<ApiResponse<PaySprintDmtRegistrationResponse>>

    @POST("api/android/pdmt/getbank")
    fun requestPaySprintDmtGetBank(@Query("type") type: String): LiveData<ApiResponse<PaySprintDmtBanksResponse>>


    @POST("api/android/pdmt/transaction")
    fun requestPaySprintDmtAddVerifyBeneficiary(@Body data: RequestBody): LiveData<ApiResponse<PaySprintDmtBeneficiaryVerifyResponse>>


    @POST("api/android/pdmt/transaction")
    fun requestPaySprintDmtMoneyTransaction(@Body data: RequestBody): LiveData<ApiResponse<PaySprintMoneyTransactionResponse>>

    @POST("api/android/payoutdmt/transaction")
    fun requestSearchPayOutDmt(@Body data: RequestBody): LiveData<ApiResponse<BalanceResponse>>

    @POST("api/android/paysprint/microatm/initiate")
    fun requestPaySprintMAtmInitiate(@Body data: RequestBody): LiveData<ApiResponse<MAtmInitiateResponse>>

    @POST("api/android/secure/microatm/initiate")
    fun requestSecureMAtmInitiate(@Body data: RequestBody): LiveData<ApiResponse<MAtmInitiateResponse>>

    @POST("api/android/recharge/providers")
    fun requestRechargeProviders(@Body data: RequestBody): LiveData<ApiResponse<PrepaidProvidersResponse>>

    @POST("api/android/recharge/pay")
    fun requestRechargePay(@Body data: RequestBody): LiveData<ApiResponse<RechargePayResponse>>

    @POST("api/android/billpay/getprovider")
    fun requestBillParams(@Body data: RequestBody): LiveData<ApiResponse<BillParamsResponse>>

    @POST("api/android/billpay/transaction")
    fun requestBillValidate(@Body data: RequestBody): LiveData<ApiResponse<BillValidateResponse>>

    @POST("api/android/billpay/transaction")
    fun requestBillPayment(@Body data: RequestBody): LiveData<ApiResponse<BillPaymentResponse>>


    @POST("api/android/paysprint/aeps2fa")
    fun requestAadhaar2Fa(@Body data: RequestBody): LiveData<ApiResponse<Aadhaar2FaResponse>>


    @POST("api/android/transaction")
    fun fetchAepsReport(@Body data: RequestBody): LiveData<ApiResponse<AEPSReportResponse>>


    @POST("api/android/transaction")
    fun fetchRechargeReport(@Body data: RequestBody): LiveData<ApiResponse<RechargeReportResponse>>

    @POST("api/android/transaction")
    fun fetchAepsFundReport(@Body data: RequestBody): LiveData<ApiResponse<AEPSFundReportResponse>>

    @POST("api/android/recharge/getplan")
    fun fetchRechargePlans(@Body data: RequestBody): LiveData<ApiResponse<RechargePlansResponse>>



    @GET("api/android/transaction")
    suspend fun fetchAepsReport(
        @Query("user_id") user_id: String?,
        @Query("apptoken") apptoken: String?,
        @Query("type") type: String?,
        @Query("searchtext") searchtext: String?,
        @Query("status") status: String?,
        @Query("fromdate") fromdate: String?,
        @Query("todate") todate: String?,
        @Query("start") start: Int
    ): AEPSReportResponse

    @GET("api/android/transaction")
    suspend fun fetchAepsFundReport(
        @Query("user_id") user_id: String?,
        @Query("apptoken") apptoken: String?,
        @Query("searchtext") searchtext: String?,
        @Query("status") status: String?,
        @Query("fromdate") fromdate: String?,
        @Query("todate") todate: String?,
        @Query("type") type: String?,
        @Query("start") start: Int
    ): AEPSFundReportResponse

    @GET("api/android/transaction")
    suspend fun fetchRechargeReport(
        @Query("user_id") user_id: String?,
        @Query("apptoken") apptoken: String?,
        @Query("type") type: String?,
        @Query("searchtext") searchtext: String?,
        @Query("status") status: String?,
        @Query("fromdate") fromdate: String?,
        @Query("todate") todate: String?,
        @Query("start") start: Int
    ): RechargeReportResponse

    @POST("api/android/userbank")
    fun requestFundUserBank(@Body data: RequestBody): LiveData<ApiResponse<FundUserBankResponse>>

    @POST("api/android/fundrequest")
    fun requestFundUserRequest(@Body data: RequestBody): LiveData<ApiResponse<FundRequestResponse>>

    @POST("api/android/fundrequest")
    fun requestFundBank(@Body data: RequestBody): LiveData<ApiResponse<FundBankResponse>>

    @POST("api/android/auth/password/change")
    fun requestChangePassword(@Body data: RequestBody): LiveData<ApiResponse<ChangePasswordResponse>>

    @POST("api/android/tpin/getotp")
    fun requestGetTOtp(@Body data: RequestBody): LiveData<ApiResponse<GetTPinResponse>>

    @POST("api/android/tpin/generate")
    fun requestGenerateTPin(@Body data: RequestBody): LiveData<ApiResponse<GenerateTPinResponse>>

    @POST("api/android/auth/logout")
    fun requestLogOut(@Body data: RequestBody): LiveData<ApiResponse<LogoutResponse>>






















}