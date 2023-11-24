package com.payment.ayushdigitils.ui.fragments.report.aeps_fund_report

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.payment.ayushdigitils.data.remote.AEPSFundReportData
import com.payment.ayushdigitils.network.PayApiServices
import java.io.IOException

private const val START_PAGE = 1

class AEPSFundReportPagingSource(
    private val apiService: PayApiServices,
    private val user_id: String?,
    private val apptoken: String,
    private val searchtext: String?,
    private val status: String?,
    private val fromdate: String?,
    private val todate: String?,
) : PagingSource<Int, AEPSFundReportData>() {


    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AEPSFundReportData> {
        val position = params.key ?: START_PAGE





        return try {
            val response = apiService.fetchAepsFundReport(
                user_id = user_id,
                apptoken = apptoken,
                type = "aepsfundrequest",
                searchtext = searchtext,
                status = status,
                fromdate = fromdate,
                todate = todate,
                start = position
            )
            val episode = response.data ?: emptyList<AEPSFundReportData>()

            var nextPageNumber: Int? = null

            nextPageNumber = response.pages


            LoadResult.Page(
                data = episode,
                prevKey = if (position == START_PAGE) null else position - 1,
                /*nextKey =  nextPageNumber+1*/
                nextKey = position + 1
            )


        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: IllegalStateException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: com.squareup.moshi.JsonDataException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, AEPSFundReportData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}