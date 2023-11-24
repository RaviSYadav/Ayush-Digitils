package com.payment.ayushdigitils.di


import com.payment.ayushdigitils.repository.AepsFundReportsRepository
import com.payment.ayushdigitils.repository.AepsReportsRepository
import com.payment.ayushdigitils.repository.BillReportsRepository
import com.payment.ayushdigitils.repository.ContactRepository
import com.payment.ayushdigitils.repository.DMTReportsRepository
import com.payment.ayushdigitils.repository.LICReportsRepository
import com.payment.ayushdigitils.repository.MATMFundReportsRepository
import com.payment.ayushdigitils.repository.MATMReportsRepository
import com.payment.ayushdigitils.repository.NSDLReportsRepository
import com.payment.ayushdigitils.repository.PayRepository
import com.payment.ayushdigitils.repository.RechargeReportsRepository
import com.payment.ayushdigitils.repository.WalletReportsRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Created by Rinav Gangar <rinav.dev> on 6/10/20.
 * Agrahyah Technologies Pvt Ltd
 * rinav4all@gmail.com
 */
val repoModule = module {

    single { PayRepository(network = get(qualifier = named("default"))) }
    single { AepsFundReportsRepository(apiService = get(qualifier = named("default"))) }
    single { AepsReportsRepository(apiService = get(qualifier = named("default"))) }
    single { BillReportsRepository(apiService = get(qualifier = named("default"))) }
    single { DMTReportsRepository(apiService = get(qualifier = named("default"))) }
    single { LICReportsRepository(apiService = get(qualifier = named("default"))) }
    single { MATMFundReportsRepository(apiService = get(qualifier = named("default"))) }
    single { MATMReportsRepository(apiService = get(qualifier = named("default"))) }
    single { NSDLReportsRepository(apiService = get(qualifier = named("default"))) }
    single { RechargeReportsRepository(apiService = get(qualifier = named("default"))) }
    single { WalletReportsRepository(apiService = get(qualifier = named("default"))) }
    single { ContactRepository(application = androidApplication()) }




}