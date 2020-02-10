package com.webaddicted.techcleanarch.view.home

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.webaddicted.model.news.NewsChanelRespo
import com.webaddicted.techcleanarch.view.base.ScrollListener
import com.webaddicted.network.utils.ApiStatus
import com.webaddicted.techcleanarch.R
import com.webaddicted.techcleanarch.databinding.FrmNewsBinding
import com.webaddicted.techcleanarch.global.misc.AppConstant
import com.webaddicted.techcleanarch.global.misc.gone
import com.webaddicted.techcleanarch.global.misc.visible
import com.webaddicted.techcleanarch.view.adapter.NewsAdapter
import com.webaddicted.techcleanarch.view.base.BaseFragment
import com.webaddicted.techcleanarch.viewmodel.NewsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Deepak Sharma(webaddicted) on 15/01/20.
 */
class NewsFrm : BaseFragment() {
    private var newsList: ArrayList<NewsChanelRespo.Source>? = null
    private lateinit var mBinding: FrmNewsBinding
    private lateinit var newsAdapter: NewsAdapter
    private val mViewModel: NewsViewModel by viewModel()
    private var mPageCount: Int = 1

    companion object {
        val TAG = NewsFrm::class.java.simpleName
        fun getInstance(bundle: Bundle): NewsFrm {
            val fragment = NewsFrm()
            fragment.arguments = bundle
            return NewsFrm()
        }
    }

    override fun getLayout(): Int {
        return R.layout.frm_news
    }

    override fun initUI(binding: ViewDataBinding?, view: View) {
        mBinding = binding as FrmNewsBinding
        init()
        clickListener()
        setAdapter()
    }

    private fun init() {
        mBinding.toolbar.imgProfile.visible()
        mBinding.toolbar.txtToolbarTitle.text = resources.getString(R.string.news_channel)
        mBinding.parent.setBackgroundColor(ContextCompat.getColor(activity!!,R.color.grey_light))
        callApi()
    }

    private fun clickListener() {
        mBinding.toolbar.imgProfile.setOnClickListener(this)
        mBinding.toolbar.imgBack.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.img_profile -> navigateScreen(ProfileFrm.TAG)
        }
    }

    private fun setAdapter() {
        newsAdapter = NewsAdapter(newsList)
        mBinding.rvNewsChannel.layoutManager = LinearLayoutManager(activity)
        mBinding.rvNewsChannel.addOnScrollListener(object :
            ScrollListener(mBinding.rvNewsChannel.layoutManager as LinearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                mPageCount++
                callApi()
            }
        })
        mBinding.rvNewsChannel.adapter = newsAdapter
    }

    private fun callApi() {
        mViewModel.newsRespo.observe(this, Observer { response ->
            apiResponseHandler(mBinding.parent, response)
            when (response.status) {
                ApiStatus.SUCCESS -> {
                    hideApiLoader()
                    if (newsList == null || newsList?.size == 0) newsList = response.data!!.sources
                    else newsList?.addAll(response.data!!.sources)
                    newsAdapter.notifyAdapter(newsList!!)
                    if (newsList == null || newsList?.size == 0)
                        mBinding.txtNoDataFound.visible()
                    else mBinding.txtNoDataFound.gone()
                }
            }
        })
        mViewModel.getNewsChannelList(
            "https://newsapi.org/v2/sources?language=en&page=" + mPageCount + "&pageSize=" + AppConstant.PAGINATION_SIZE + "&apiKey=" + getString(
                R.string.news_api_key
            )
        )
    }

    /**
     * navigate on fragment
     * @param tag represent navigation activity
     */
    private fun navigateScreen(tag: String) {
        var frm: Fragment? = null
        when (tag) {
            ProfileFrm.TAG -> frm = ProfileFrm.getInstance(Bundle())
        }
        if (frm != null) navigateAddFragment(R.id.container, frm, true)
    }
}


