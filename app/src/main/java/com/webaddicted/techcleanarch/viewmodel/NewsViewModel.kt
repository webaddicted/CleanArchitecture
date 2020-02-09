package com.webaddicted.techcleanarch.viewmodel

import androidx.lifecycle.MutableLiveData
import com.webaddicted.data.repo.NewsRepository
import com.webaddicted.model.news.NewsChanelRespo
import com.webaddicted.network.utils.ApiStatus
import com.webaddicted.network.utils.Coroutines

/**
 * Created by Deepak Sharma(webaddicted) on 15/01/20.
 */
class NewsViewModel( private val projectRepository: NewsRepository) : BaseViewModel(){
    var newsRespo: MutableLiveData<com.webaddicted.network.utils.ApiResponse<NewsChanelRespo>> = MutableLiveData<com.webaddicted.network.utils.ApiResponse<NewsChanelRespo>>()

    fun getNewsChannelList(strUrl: String) {
        Coroutines.main {
            newsRespo?.postValue(com.webaddicted.network.utils.ApiResponse(ApiStatus.LOADING,"", NewsChanelRespo(), 100))
            newsRespo?.postValue(projectRepository.getNewsChannelList(strUrl))
        }
    }


}