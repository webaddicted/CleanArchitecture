package com.webaddicted.network.apiutils

import com.webaddicted.model.news.NewsChanelRespo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Created by Deepak Sharma(webaddicted) on 15/01/20.
 */
interface ApiServices {

    @GET
    suspend fun getChannelList(@Url strUrl: String): Response<NewsChanelRespo>

}