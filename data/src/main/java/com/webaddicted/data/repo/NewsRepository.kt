package com.webaddicted.data.repo

import androidx.lifecycle.MutableLiveData
import com.webaddicted.model.news.NewsChanelRespo
import com.webaddicted.network.apiutils.ApiResponse
import com.webaddicted.network.apiutils.ApiServices
import com.webaddicted.network.apiutils.DataFetchCall
import kotlinx.coroutines.Deferred
import retrofit2.Response
/**
 * Created by Deepak Sharma(webaddicted) on 15/01/20.
 */
class NewsRepository constructor(private val apiServices: ApiServices) : BaseRepository() {
    fun getNewsChannel(strUrl: String, loginResponse: MutableLiveData<ApiResponse<NewsChanelRespo>>) {
        object : DataFetchCall<NewsChanelRespo>(loginResponse) {
            override fun createCallAsync(): Deferred<Response<NewsChanelRespo>> {
                return apiServices.getChannelListName(strUrl)
            }

            override fun shouldFetchFromDB(): Boolean {
                return false
            }
        }.execute()
    }
}