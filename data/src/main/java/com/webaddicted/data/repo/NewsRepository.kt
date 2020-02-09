package com.webaddicted.data.repo

import com.webaddicted.model.news.NewsChanelRespo
import com.webaddicted.network.apiutils.ApiServices

/**
 * Created by Deepak Sharma(webaddicted) on 15/01/20.
 */
class NewsRepository constructor(private val apiServices: ApiServices) : BaseRepository() {
    suspend fun getNewsChannelList(strUrl: String): com.webaddicted.network.utils.ApiResponse<NewsChanelRespo>? {
        return apiRequest { apiServices.getChannelList(strUrl) }

    }
}