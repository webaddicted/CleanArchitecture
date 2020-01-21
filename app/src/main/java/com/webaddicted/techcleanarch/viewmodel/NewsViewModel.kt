package com.webaddicted.techcleanarch.viewmodel

import androidx.lifecycle.MutableLiveData
import com.webaddicted.data.repo.NewsRepository
import com.webaddicted.model.news.NewsChanelRespo
import com.webaddicted.network.apiutils.ApiResponse

/**
 * Created by Deepak Sharma(webaddicted) on 15/01/20.
 */
class NewsViewModel( private val projectRepository: NewsRepository) : BaseViewModel(){
    private var channelResponse = MutableLiveData<ApiResponse<NewsChanelRespo>>()

    fun getNewsChannelLiveData(): MutableLiveData<ApiResponse<NewsChanelRespo>> {
        return channelResponse
    }

    fun newsChannelApi(strUrl: String) {
        projectRepository.getNewsChannel(strUrl, channelResponse)
    }

}