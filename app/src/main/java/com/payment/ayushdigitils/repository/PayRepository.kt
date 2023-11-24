package com.payment.ayushdigitils.repository

import androidx.lifecycle.LiveData
import com.payment.ayushdigitils.app.AppExecutors
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
import com.payment.ayushdigitils.network.ApiResponse
import com.payment.ayushdigitils.network.ApiSuccessResponse
import com.payment.ayushdigitils.network.PayApiServices
import com.payment.ayushdigitils.utils.AbsentLiveData
import com.payment.ayushdigitils.vo.Resource
import okhttp3.RequestBody
import org.koin.java.KoinJavaComponent.inject


class PayRepository(private val network: PayApiServices) : ServiceManager {

    private val appExecutors: AppExecutors by inject(AppExecutors::class.java)
    override fun requestRegister(data: RequestBody): LiveData<Resource<SignupResponse>> {
        return object : NetworkBoundResource<SignupResponse, SignupResponse>(appExecutors) {

            private var responseData: SignupResponse? = null

            override fun saveCallResult(item: SignupResponse) {
                responseData = item
            }

            override fun shouldFetch(data: SignupResponse?) = true

            override fun loadFromDb(): LiveData<SignupResponse> {
                return if (responseData == null) {
                    AbsentLiveData.create()
                } else {
                    object : LiveData<SignupResponse>() {
                        override fun onActive() {
                            super.onActive()
                            value = responseData
                        }
                    }
                }
            }

            override fun createCall(): LiveData<ApiResponse<SignupResponse>> {
                return network.requestRegister(data)
            }

            override fun processResponse(response: ApiSuccessResponse<SignupResponse>): SignupResponse {
                return response.body
            }

        }.asLiveData()
    }

    override fun requestLogin(data: RequestBody): LiveData<Resource<LoginResponse>> {
        return object : NetworkBoundResource<LoginResponse, LoginResponse>(appExecutors) {

            private var responseData: LoginResponse? = null

            override fun saveCallResult(item: LoginResponse) {
                responseData = item
            }

            override fun shouldFetch(data: LoginResponse?) = true

            override fun loadFromDb(): LiveData<LoginResponse> {
                return if (responseData == null) {
                    AbsentLiveData.create()
                } else {
                    object : LiveData<LoginResponse>() {
                        override fun onActive() {
                            super.onActive()
                            value = responseData
                        }
                    }
                }
            }

            override fun createCall(): LiveData<ApiResponse<LoginResponse>> {
                return network.requestLogin(data)
            }

            override fun processResponse(response: ApiSuccessResponse<LoginResponse>): LoginResponse {
                return response.body
            }

        }.asLiveData()
    }

    override fun requestLogOut(data: RequestBody): LiveData<Resource<LogoutResponse>> {
        return object : NetworkBoundResource<LogoutResponse, LogoutResponse>(appExecutors) {

            private var responseData: LogoutResponse? = null

            override fun saveCallResult(item: LogoutResponse) {
                responseData = item
            }

            override fun shouldFetch(data: LogoutResponse?) = true

            override fun loadFromDb(): LiveData<LogoutResponse> {
                return if (responseData == null) {
                    AbsentLiveData.create()
                } else {
                    object : LiveData<LogoutResponse>() {
                        override fun onActive() {
                            super.onActive()
                            value = responseData
                        }
                    }
                }
            }

            override fun createCall(): LiveData<ApiResponse<LogoutResponse>> {
                return network.requestLogOut(data)
            }

            override fun processResponse(response: ApiSuccessResponse<LogoutResponse>): LogoutResponse {
                return response.body
            }

        }.asLiveData()
    }

    override fun requestGetBalance(data: RequestBody): LiveData<Resource<BalanceResponse>> {
        return object : NetworkBoundResource<BalanceResponse, BalanceResponse>(appExecutors) {

            private var responseData: BalanceResponse? = null

            override fun saveCallResult(item: BalanceResponse) {
                responseData = item
            }

            override fun shouldFetch(data: BalanceResponse?) = true

            override fun loadFromDb(): LiveData<BalanceResponse> {
                return if (responseData == null) {
                    AbsentLiveData.create()
                } else {
                    object : LiveData<BalanceResponse>() {
                        override fun onActive() {
                            super.onActive()
                            value = responseData
                        }
                    }
                }
            }

            override fun createCall(): LiveData<ApiResponse<BalanceResponse>> {
                return network.requestGetBalance(data)
            }

            override fun processResponse(response: ApiSuccessResponse<BalanceResponse>): BalanceResponse {
                return response.body
            }

        }.asLiveData()
    }

    override fun requestSearchPaySprintDmtVerify(data: RequestBody): LiveData<Resource<PaySprintDmtVerifyResponse>> {
        return object : NetworkBoundResource<PaySprintDmtVerifyResponse, PaySprintDmtVerifyResponse>(appExecutors) {

            private var responseData: PaySprintDmtVerifyResponse? = null

            override fun saveCallResult(item: PaySprintDmtVerifyResponse) {
                responseData = item
            }

            override fun shouldFetch(data: PaySprintDmtVerifyResponse?) = true

            override fun loadFromDb(): LiveData<PaySprintDmtVerifyResponse> {
                return if (responseData == null) {
                    AbsentLiveData.create()
                } else {
                    object : LiveData<PaySprintDmtVerifyResponse>() {
                        override fun onActive() {
                            super.onActive()
                            value = responseData
                        }
                    }
                }
            }

            override fun createCall(): LiveData<ApiResponse<PaySprintDmtVerifyResponse>> {
                return network.requestSearchPaySprintDmtVerify(data)
            }

            override fun processResponse(response: ApiSuccessResponse<PaySprintDmtVerifyResponse>): PaySprintDmtVerifyResponse {
                return response.body
            }

        }.asLiveData()
    }

    override fun requestSearchPaySprintBeneficiaryDelete(data: RequestBody): LiveData<Resource<PaySprintDmtVerifyResponse>> {
        return object : NetworkBoundResource<PaySprintDmtVerifyResponse, PaySprintDmtVerifyResponse>(appExecutors) {

            private var responseData: PaySprintDmtVerifyResponse? = null

            override fun saveCallResult(item: PaySprintDmtVerifyResponse) {
                responseData = item
            }

            override fun shouldFetch(data: PaySprintDmtVerifyResponse?) = true

            override fun loadFromDb(): LiveData<PaySprintDmtVerifyResponse> {
                return if (responseData == null) {
                    AbsentLiveData.create()
                } else {
                    object : LiveData<PaySprintDmtVerifyResponse>() {
                        override fun onActive() {
                            super.onActive()
                            value = responseData
                        }
                    }
                }
            }

            override fun createCall(): LiveData<ApiResponse<PaySprintDmtVerifyResponse>> {
                return network.requestSearchPaySprintBeneficiaryDelete(data)
            }

            override fun processResponse(response: ApiSuccessResponse<PaySprintDmtVerifyResponse>): PaySprintDmtVerifyResponse {
                return response.body
            }

        }.asLiveData()
    }

    override fun requestPaySprintDmtRegistration(data: RequestBody): LiveData<Resource<PaySprintDmtRegistrationResponse>> {
        return object : NetworkBoundResource<PaySprintDmtRegistrationResponse, PaySprintDmtRegistrationResponse>(appExecutors) {

            private var responseData: PaySprintDmtRegistrationResponse? = null

            override fun saveCallResult(item: PaySprintDmtRegistrationResponse) {
                responseData = item
            }

            override fun shouldFetch(data: PaySprintDmtRegistrationResponse?) = true

            override fun loadFromDb(): LiveData<PaySprintDmtRegistrationResponse> {
                return if (responseData == null) {
                    AbsentLiveData.create()
                } else {
                    object : LiveData<PaySprintDmtRegistrationResponse>() {
                        override fun onActive() {
                            super.onActive()
                            value = responseData
                        }
                    }
                }
            }

            override fun createCall(): LiveData<ApiResponse<PaySprintDmtRegistrationResponse>> {
                return network.requestPaySprintDmtRegistration(data)
            }

            override fun processResponse(response: ApiSuccessResponse<PaySprintDmtRegistrationResponse>): PaySprintDmtRegistrationResponse {
                return response.body
            }

        }.asLiveData()
    }


    override fun requestPaySprintDmtGetBank(type: String): LiveData<Resource<PaySprintDmtBanksResponse>> {
        return object : NetworkBoundResource<PaySprintDmtBanksResponse, PaySprintDmtBanksResponse>(appExecutors) {

            private var responseData: PaySprintDmtBanksResponse? = null

            override fun saveCallResult(item: PaySprintDmtBanksResponse) {
                responseData = item
            }

            override fun shouldFetch(data: PaySprintDmtBanksResponse?) = true

            override fun loadFromDb(): LiveData<PaySprintDmtBanksResponse> {
                return if (responseData == null) {
                    AbsentLiveData.create()
                } else {
                    object : LiveData<PaySprintDmtBanksResponse>() {
                        override fun onActive() {
                            super.onActive()
                            value = responseData
                        }
                    }
                }
            }

            override fun createCall(): LiveData<ApiResponse<PaySprintDmtBanksResponse>> {
                return network.requestPaySprintDmtGetBank(type)
            }

            override fun processResponse(response: ApiSuccessResponse<PaySprintDmtBanksResponse>): PaySprintDmtBanksResponse {
                return response.body
            }

        }.asLiveData()
    }

    override fun requestPaySprintDmtAddVerifyBeneficiary(data: RequestBody): LiveData<Resource<PaySprintDmtBeneficiaryVerifyResponse>> {
        return object : NetworkBoundResource<PaySprintDmtBeneficiaryVerifyResponse, PaySprintDmtBeneficiaryVerifyResponse>(appExecutors) {

            private var responseData: PaySprintDmtBeneficiaryVerifyResponse? = null

            override fun saveCallResult(item: PaySprintDmtBeneficiaryVerifyResponse) {
                responseData = item
            }

            override fun shouldFetch(data: PaySprintDmtBeneficiaryVerifyResponse?) = true

            override fun loadFromDb(): LiveData<PaySprintDmtBeneficiaryVerifyResponse> {
                return if (responseData == null) {
                    AbsentLiveData.create()
                } else {
                    object : LiveData<PaySprintDmtBeneficiaryVerifyResponse>() {
                        override fun onActive() {
                            super.onActive()
                            value = responseData
                        }
                    }
                }
            }

            override fun createCall(): LiveData<ApiResponse<PaySprintDmtBeneficiaryVerifyResponse>> {
                return network.requestPaySprintDmtAddVerifyBeneficiary(data)
            }

            override fun processResponse(response: ApiSuccessResponse<PaySprintDmtBeneficiaryVerifyResponse>): PaySprintDmtBeneficiaryVerifyResponse {
                return response.body
            }

        }.asLiveData()
    }

    override fun requestPaySprintDmtMoneyTransaction(data: RequestBody): LiveData<Resource<PaySprintMoneyTransactionResponse>> {
        return object : NetworkBoundResource<PaySprintMoneyTransactionResponse, PaySprintMoneyTransactionResponse>(appExecutors) {

            private var responseData: PaySprintMoneyTransactionResponse? = null

            override fun saveCallResult(item: PaySprintMoneyTransactionResponse) {
                responseData = item
            }

            override fun shouldFetch(data: PaySprintMoneyTransactionResponse?) = true

            override fun loadFromDb(): LiveData<PaySprintMoneyTransactionResponse> {
                return if (responseData == null) {
                    AbsentLiveData.create()
                } else {
                    object : LiveData<PaySprintMoneyTransactionResponse>() {
                        override fun onActive() {
                            super.onActive()
                            value = responseData
                        }
                    }
                }
            }

            override fun createCall(): LiveData<ApiResponse<PaySprintMoneyTransactionResponse>> {
                return network.requestPaySprintDmtMoneyTransaction(data)
            }

            override fun processResponse(response: ApiSuccessResponse<PaySprintMoneyTransactionResponse>): PaySprintMoneyTransactionResponse {
                return response.body
            }

        }.asLiveData()
    }

    override fun requestPaySprintMAtmInitiate(data: RequestBody): LiveData<Resource<MAtmInitiateResponse>> {
        return object : NetworkBoundResource<MAtmInitiateResponse, MAtmInitiateResponse>(appExecutors) {

            private var responseData: MAtmInitiateResponse? = null

            override fun saveCallResult(item: MAtmInitiateResponse) {
                responseData = item
            }

            override fun shouldFetch(data: MAtmInitiateResponse?) = true

            override fun loadFromDb(): LiveData<MAtmInitiateResponse> {
                return if (responseData == null) {
                    AbsentLiveData.create()
                } else {
                    object : LiveData<MAtmInitiateResponse>() {
                        override fun onActive() {
                            super.onActive()
                            value = responseData
                        }
                    }
                }
            }

            override fun createCall(): LiveData<ApiResponse<MAtmInitiateResponse>> {
                return network.requestPaySprintMAtmInitiate(data)
            }

            override fun processResponse(response: ApiSuccessResponse<MAtmInitiateResponse>): MAtmInitiateResponse {
                return response.body
            }

        }.asLiveData()

    }

    override fun requestSecureMAtmInitiate(data: RequestBody): LiveData<Resource<MAtmInitiateResponse>> {
        return object : NetworkBoundResource<MAtmInitiateResponse, MAtmInitiateResponse>(appExecutors) {

            private var responseData: MAtmInitiateResponse? = null

            override fun saveCallResult(item: MAtmInitiateResponse) {
                responseData = item
            }

            override fun shouldFetch(data: MAtmInitiateResponse?) = true

            override fun loadFromDb(): LiveData<MAtmInitiateResponse> {
                return if (responseData == null) {
                    AbsentLiveData.create()
                } else {
                    object : LiveData<MAtmInitiateResponse>() {
                        override fun onActive() {
                            super.onActive()
                            value = responseData
                        }
                    }
                }
            }

            override fun createCall(): LiveData<ApiResponse<MAtmInitiateResponse>> {
                return network.requestSecureMAtmInitiate(data)
            }

            override fun processResponse(response: ApiSuccessResponse<MAtmInitiateResponse>): MAtmInitiateResponse {
                return response.body
            }

        }.asLiveData()
    }

    override fun requestRechargeProviders(data: RequestBody): LiveData<Resource<PrepaidProvidersResponse>> {
        return object : NetworkBoundResource<PrepaidProvidersResponse, PrepaidProvidersResponse>(appExecutors) {

            private var responseData: PrepaidProvidersResponse? = null

            override fun saveCallResult(item: PrepaidProvidersResponse) {
                responseData = item
            }

            override fun shouldFetch(data: PrepaidProvidersResponse?) = true

            override fun loadFromDb(): LiveData<PrepaidProvidersResponse> {
                return if (responseData == null) {
                    AbsentLiveData.create()
                } else {
                    object : LiveData<PrepaidProvidersResponse>() {
                        override fun onActive() {
                            super.onActive()
                            value = responseData
                        }
                    }
                }
            }

            override fun createCall(): LiveData<ApiResponse<PrepaidProvidersResponse>> {
                return network.requestRechargeProviders(data)
            }

            override fun processResponse(response: ApiSuccessResponse<PrepaidProvidersResponse>): PrepaidProvidersResponse {
                return response.body
            }

        }.asLiveData()
    }

    override fun requestRechargePay(data: RequestBody): LiveData<Resource<RechargePayResponse>> {
        return object : NetworkBoundResource<RechargePayResponse, RechargePayResponse>(appExecutors) {

            private var responseData: RechargePayResponse? = null

            override fun saveCallResult(item: RechargePayResponse) {
                responseData = item
            }

            override fun shouldFetch(data: RechargePayResponse?) = true

            override fun loadFromDb(): LiveData<RechargePayResponse> {
                return if (responseData == null) {
                    AbsentLiveData.create()
                } else {
                    object : LiveData<RechargePayResponse>() {
                        override fun onActive() {
                            super.onActive()
                            value = responseData
                        }
                    }
                }
            }

            override fun createCall(): LiveData<ApiResponse<RechargePayResponse>> {
                return network.requestRechargePay(data)
            }

            override fun processResponse(response: ApiSuccessResponse<RechargePayResponse>): RechargePayResponse {
                return response.body
            }

        }.asLiveData()
    }

    override fun requestBillParams(data: RequestBody): LiveData<Resource<BillParamsResponse>> {
        return object : NetworkBoundResource<BillParamsResponse, BillParamsResponse>(appExecutors) {

            private var responseData: BillParamsResponse? = null

            override fun saveCallResult(item: BillParamsResponse) {
                responseData = item
            }

            override fun shouldFetch(data: BillParamsResponse?) = true

            override fun loadFromDb(): LiveData<BillParamsResponse> {
                return if (responseData == null) {
                    AbsentLiveData.create()
                } else {
                    object : LiveData<BillParamsResponse>() {
                        override fun onActive() {
                            super.onActive()
                            value = responseData
                        }
                    }
                }
            }

            override fun createCall(): LiveData<ApiResponse<BillParamsResponse>> {
                return network.requestBillParams(data)
            }

            override fun processResponse(response: ApiSuccessResponse<BillParamsResponse>): BillParamsResponse {
                return response.body
            }

        }.asLiveData()
    }

    override fun requestBillValidate(data: RequestBody): LiveData<Resource<BillValidateResponse>> {
        return object : NetworkBoundResource<BillValidateResponse, BillValidateResponse>(appExecutors) {

            private var responseData: BillValidateResponse? = null

            override fun saveCallResult(item: BillValidateResponse) {
                responseData = item
            }

            override fun shouldFetch(data: BillValidateResponse?) = true

            override fun loadFromDb(): LiveData<BillValidateResponse> {
                return if (responseData == null) {
                    AbsentLiveData.create()
                } else {
                    object : LiveData<BillValidateResponse>() {
                        override fun onActive() {
                            super.onActive()
                            value = responseData
                        }
                    }
                }
            }

            override fun createCall(): LiveData<ApiResponse<BillValidateResponse>> {
                return network.requestBillValidate(data)
            }

            override fun processResponse(response: ApiSuccessResponse<BillValidateResponse>): BillValidateResponse {
                return response.body
            }

        }.asLiveData()

    }

    override fun requestBillPayment(data: RequestBody): LiveData<Resource<BillPaymentResponse>> {
        return object : NetworkBoundResource<BillPaymentResponse, BillPaymentResponse>(appExecutors) {

            private var responseData: BillPaymentResponse? = null

            override fun saveCallResult(item: BillPaymentResponse) {
                responseData = item
            }

            override fun shouldFetch(data: BillPaymentResponse?) = true

            override fun loadFromDb(): LiveData<BillPaymentResponse> {
                return if (responseData == null) {
                    AbsentLiveData.create()
                } else {
                    object : LiveData<BillPaymentResponse>() {
                        override fun onActive() {
                            super.onActive()
                            value = responseData
                        }
                    }
                }
            }

            override fun createCall(): LiveData<ApiResponse<BillPaymentResponse>> {
                return network.requestBillPayment(data)
            }

            override fun processResponse(response: ApiSuccessResponse<BillPaymentResponse>): BillPaymentResponse {
                return response.body
            }

        }.asLiveData()
    }

    override fun requestOnBoarding(data: RequestBody): LiveData<Resource<SignupResponse>> {
        return object : NetworkBoundResource<SignupResponse, SignupResponse>(appExecutors) {

            private var responseData: SignupResponse? = null

            override fun saveCallResult(item: SignupResponse) {
                responseData = item
            }

            override fun shouldFetch(data: SignupResponse?) = true

            override fun loadFromDb(): LiveData<SignupResponse> {
                return if (responseData == null) {
                    AbsentLiveData.create()
                } else {
                    object : LiveData<SignupResponse>() {
                        override fun onActive() {
                            super.onActive()
                            value = responseData
                        }
                    }
                }
            }

            override fun createCall(): LiveData<ApiResponse<SignupResponse>> {
                return network.requestOnBoarding(data)
            }

            override fun processResponse(response: ApiSuccessResponse<SignupResponse>): SignupResponse {
                return response.body
            }

        }.asLiveData()
    }

    override fun requestAadhaar2Fa(data: RequestBody): LiveData<Resource<Aadhaar2FaResponse>> {
        return object : NetworkBoundResource<Aadhaar2FaResponse, Aadhaar2FaResponse>(appExecutors) {

            private var responseData: Aadhaar2FaResponse? = null

            override fun saveCallResult(item: Aadhaar2FaResponse) {
                responseData = item
            }

            override fun shouldFetch(data: Aadhaar2FaResponse?) = true

            override fun loadFromDb(): LiveData<Aadhaar2FaResponse> {
                return if (responseData == null) {
                    AbsentLiveData.create()
                } else {
                    object : LiveData<Aadhaar2FaResponse>() {
                        override fun onActive() {
                            super.onActive()
                            value = responseData
                        }
                    }
                }
            }

            override fun createCall(): LiveData<ApiResponse<Aadhaar2FaResponse>> {
                return network.requestAadhaar2Fa(data)
            }

            override fun processResponse(response: ApiSuccessResponse<Aadhaar2FaResponse>): Aadhaar2FaResponse {
                return response.body
            }

        }.asLiveData()
    }

    override fun requestFundUserBank(data: RequestBody): LiveData<Resource<FundUserBankResponse>> {
        return object : NetworkBoundResource<FundUserBankResponse, FundUserBankResponse>(appExecutors) {

            private var responseData: FundUserBankResponse? = null

            override fun saveCallResult(item: FundUserBankResponse) {
                responseData = item
            }

            override fun shouldFetch(data: FundUserBankResponse?) = true

            override fun loadFromDb(): LiveData<FundUserBankResponse> {
                return if (responseData == null) {
                    AbsentLiveData.create()
                } else {
                    object : LiveData<FundUserBankResponse>() {
                        override fun onActive() {
                            super.onActive()
                            value = responseData
                        }
                    }
                }
            }

            override fun createCall(): LiveData<ApiResponse<FundUserBankResponse>> {
                return network.requestFundUserBank(data)
            }

            override fun processResponse(response: ApiSuccessResponse<FundUserBankResponse>): FundUserBankResponse {
                return response.body
            }

        }.asLiveData()
    }

    override fun requestFundUserRequest(data: RequestBody): LiveData<Resource<FundRequestResponse>> {
        return object : NetworkBoundResource<FundRequestResponse, FundRequestResponse>(appExecutors) {

            private var responseData: FundRequestResponse? = null

            override fun saveCallResult(item: FundRequestResponse) {
                responseData = item
            }

            override fun shouldFetch(data: FundRequestResponse?) = true

            override fun loadFromDb(): LiveData<FundRequestResponse> {
                return if (responseData == null) {
                    AbsentLiveData.create()
                } else {
                    object : LiveData<FundRequestResponse>() {
                        override fun onActive() {
                            super.onActive()
                            value = responseData
                        }
                    }
                }
            }

            override fun createCall(): LiveData<ApiResponse<FundRequestResponse>> {
                return network.requestFundUserRequest(data)
            }

            override fun processResponse(response: ApiSuccessResponse<FundRequestResponse>): FundRequestResponse {
                return response.body
            }

        }.asLiveData()
    }

    override fun requestFundBank(data: RequestBody): LiveData<Resource<FundBankResponse>> {
        return object : NetworkBoundResource<FundBankResponse, FundBankResponse>(appExecutors) {

            private var responseData: FundBankResponse? = null

            override fun saveCallResult(item: FundBankResponse) {
                responseData = item
            }

            override fun shouldFetch(data: FundBankResponse?) = true

            override fun loadFromDb(): LiveData<FundBankResponse> {
                return if (responseData == null) {
                    AbsentLiveData.create()
                } else {
                    object : LiveData<FundBankResponse>() {
                        override fun onActive() {
                            super.onActive()
                            value = responseData
                        }
                    }
                }
            }

            override fun createCall(): LiveData<ApiResponse<FundBankResponse>> {
                return network.requestFundBank(data)
            }

            override fun processResponse(response: ApiSuccessResponse<FundBankResponse>): FundBankResponse {
                return response.body
            }

        }.asLiveData()
    }

    override fun fetchAepsReport(data: RequestBody): LiveData<Resource<AEPSReportResponse>> {

        return object : NetworkBoundResource<AEPSReportResponse, AEPSReportResponse>(appExecutors) {

            private var responseData: AEPSReportResponse? = null

            override fun saveCallResult(item: AEPSReportResponse) {
                responseData = item
            }

            override fun shouldFetch(data: AEPSReportResponse?) = true

            override fun loadFromDb(): LiveData<AEPSReportResponse> {
                return if (responseData == null) {
                    AbsentLiveData.create()
                } else {
                    object : LiveData<AEPSReportResponse>() {
                        override fun onActive() {
                            super.onActive()
                            value = responseData
                        }
                    }
                }
            }

            override fun createCall(): LiveData<ApiResponse<AEPSReportResponse>> {
                return network.fetchAepsReport(data)
            }

            override fun processResponse(response: ApiSuccessResponse<AEPSReportResponse>): AEPSReportResponse {
                return response.body
            }

        }.asLiveData()
    }

    override fun fetchRechargeReport(data: RequestBody): LiveData<Resource<RechargeReportResponse>> {
        return object : NetworkBoundResource<RechargeReportResponse, RechargeReportResponse>(appExecutors) {

            private var responseData: RechargeReportResponse? = null

            override fun saveCallResult(item: RechargeReportResponse) {
                responseData = item
            }

            override fun shouldFetch(data: RechargeReportResponse?) = true

            override fun loadFromDb(): LiveData<RechargeReportResponse> {
                return if (responseData == null) {
                    AbsentLiveData.create()
                } else {
                    object : LiveData<RechargeReportResponse>() {
                        override fun onActive() {
                            super.onActive()
                            value = responseData
                        }
                    }
                }
            }

            override fun createCall(): LiveData<ApiResponse<RechargeReportResponse>> {
                return network.fetchRechargeReport(data)
            }

            override fun processResponse(response: ApiSuccessResponse<RechargeReportResponse>): RechargeReportResponse {
                return response.body
            }

        }.asLiveData()
    }

    override fun fetchAepsFundReport(data: RequestBody): LiveData<Resource<AEPSFundReportResponse>> {
        return object : NetworkBoundResource<AEPSFundReportResponse, AEPSFundReportResponse>(appExecutors) {

            private var responseData: AEPSFundReportResponse? = null

            override fun saveCallResult(item: AEPSFundReportResponse) {
                responseData = item
            }

            override fun shouldFetch(data: AEPSFundReportResponse?) = true

            override fun loadFromDb(): LiveData<AEPSFundReportResponse> {
                return if (responseData == null) {
                    AbsentLiveData.create()
                } else {
                    object : LiveData<AEPSFundReportResponse>() {
                        override fun onActive() {
                            super.onActive()
                            value = responseData
                        }
                    }
                }
            }

            override fun createCall(): LiveData<ApiResponse<AEPSFundReportResponse>> {
                return network.fetchAepsFundReport(data)
            }

            override fun processResponse(response: ApiSuccessResponse<AEPSFundReportResponse>): AEPSFundReportResponse {
                return response.body
            }

        }.asLiveData()
    }

    override fun fetchRechargePlans(data: RequestBody): LiveData<Resource<RechargePlansResponse>> {
        return object : NetworkBoundResource<RechargePlansResponse, RechargePlansResponse>(appExecutors) {

            private var responseData: RechargePlansResponse? = null

            override fun saveCallResult(item: RechargePlansResponse) {
                responseData = item
            }

            override fun shouldFetch(data: RechargePlansResponse?) = true

            override fun loadFromDb(): LiveData<RechargePlansResponse> {
                return if (responseData == null) {
                    AbsentLiveData.create()
                } else {
                    object : LiveData<RechargePlansResponse>() {
                        override fun onActive() {
                            super.onActive()
                            value = responseData
                        }
                    }
                }
            }

            override fun createCall(): LiveData<ApiResponse<RechargePlansResponse>> {
                return network.fetchRechargePlans(data)
            }

            override fun processResponse(response: ApiSuccessResponse<RechargePlansResponse>): RechargePlansResponse {
                return response.body
            }

        }.asLiveData()
    }

    override fun requestChangePassword(data: RequestBody): LiveData<Resource<ChangePasswordResponse>> {
        return object : NetworkBoundResource<ChangePasswordResponse, ChangePasswordResponse>(appExecutors) {

            private var responseData: ChangePasswordResponse? = null

            override fun saveCallResult(item: ChangePasswordResponse) {
                responseData = item
            }

            override fun shouldFetch(data: ChangePasswordResponse?) = true

            override fun loadFromDb(): LiveData<ChangePasswordResponse> {
                return if (responseData == null) {
                    AbsentLiveData.create()
                } else {
                    object : LiveData<ChangePasswordResponse>() {
                        override fun onActive() {
                            super.onActive()
                            value = responseData
                        }
                    }
                }
            }

            override fun createCall(): LiveData<ApiResponse<ChangePasswordResponse>> {
                return network.requestChangePassword(data)
            }

            override fun processResponse(response: ApiSuccessResponse<ChangePasswordResponse>): ChangePasswordResponse {
                return response.body
            }

        }.asLiveData()
    }

    override fun requestGetTOtp(data: RequestBody): LiveData<Resource<GetTPinResponse>> {
        return object : NetworkBoundResource<GetTPinResponse, GetTPinResponse>(appExecutors) {

            private var responseData: GetTPinResponse? = null

            override fun saveCallResult(item: GetTPinResponse) {
                responseData = item
            }

            override fun shouldFetch(data: GetTPinResponse?) = true

            override fun loadFromDb(): LiveData<GetTPinResponse> {
                return if (responseData == null) {
                    AbsentLiveData.create()
                } else {
                    object : LiveData<GetTPinResponse>() {
                        override fun onActive() {
                            super.onActive()
                            value = responseData
                        }
                    }
                }
            }

            override fun createCall(): LiveData<ApiResponse<GetTPinResponse>> {
                return network.requestGetTOtp(data)
            }

            override fun processResponse(response: ApiSuccessResponse<GetTPinResponse>): GetTPinResponse {
                return response.body
            }

        }.asLiveData()
    }

    override fun requestGenerateTPin(data: RequestBody): LiveData<Resource<GenerateTPinResponse>> {
        return object : NetworkBoundResource<GenerateTPinResponse, GenerateTPinResponse>(appExecutors) {

            private var responseData: GenerateTPinResponse? = null

            override fun saveCallResult(item: GenerateTPinResponse) {
                responseData = item
            }

            override fun shouldFetch(data: GenerateTPinResponse?) = true

            override fun loadFromDb(): LiveData<GenerateTPinResponse> {
                return if (responseData == null) {
                    AbsentLiveData.create()
                } else {
                    object : LiveData<GenerateTPinResponse>() {
                        override fun onActive() {
                            super.onActive()
                            value = responseData
                        }
                    }
                }
            }

            override fun createCall(): LiveData<ApiResponse<GenerateTPinResponse>> {
                return network.requestGenerateTPin(data)
            }

            override fun processResponse(response: ApiSuccessResponse<GenerateTPinResponse>): GenerateTPinResponse {
                return response.body
            }

        }.asLiveData()
    }


}