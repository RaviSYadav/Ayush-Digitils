package com.payment.ayushdigitils.ui.fragments.report.aeps_report

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.payment.ayushdigitils.data.remote.AEPSReportData
import com.payment.ayushdigitils.network.PayApiServices
import java.io.IOException


private const val START_PAGE = 1

class AEPSReportPagingSource(
    private val apiService: PayApiServices,
    private val user_id: String?,
    private val apptoken: String,
    private val searchtext: String?,
    private val status: String?,
    private val fromdate: String?,
    private val todate: String?,
) : PagingSource<Int, AEPSReportData>() {


    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AEPSReportData> {
        val position = params.key ?: START_PAGE





        return try {
            val response = apiService.fetchAepsReport(
                user_id = user_id,
                apptoken = apptoken,
                type = "aepsstatement",
                searchtext = searchtext,
                status = status,
                fromdate = fromdate,
                todate = todate,
                start = position
            )
            val episode = response.data ?: emptyList<AEPSReportData>()

            var nextPageNumber: Int? = null

            nextPageNumber = response.pages


            PagingSource.LoadResult.Page(
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

    override fun getRefreshKey(state: PagingState<Int, AEPSReportData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}