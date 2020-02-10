package com.webaddicted.techcleanarch.viewmodel

import androidx.lifecycle.MutableLiveData
import com.webaddicted.data.repo.NewsRepository
import com.webaddicted.model.news.NewsChanelRespo
import com.webaddicted.network.utils.ApiResponse
import com.webaddicted.network.utils.Coroutines

/**
 * Created by Deepak Sharma(webaddicted) on 15/01/20.
 */
class NewsViewModel( private val projectRepository: NewsRepository) : BaseViewModel(){
    var newsRespo= MutableLiveData<ApiResponse<NewsChanelRespo>>()

    fun getNewsChannelList(strUrl: String) {
        Coroutines.main {
            newsRespo.postValue(ApiResponse.loading())
            newsRespo.postValue(projectRepository.getNewsChannelList(strUrl))
        }
    }


}