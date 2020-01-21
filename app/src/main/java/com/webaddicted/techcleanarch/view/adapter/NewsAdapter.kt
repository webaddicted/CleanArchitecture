package com.webaddicted.techcleanarch.view.adapter

import androidx.databinding.ViewDataBinding
import com.webaddicted.model.news.NewsChanelRespo
import com.webaddicted.kotlinproject.view.base.BaseAdapter
import com.webaddicted.techcleanarch.R
import com.webaddicted.techcleanarch.databinding.RowChannelListBinding
import com.webaddicted.techcleanarch.global.misc.showImage

/**
 * Created by Deepak Sharma(webaddicted) on 15/01/20.
 */
class NewsAdapter(private var newsList: ArrayList<NewsChanelRespo.Source>?) : BaseAdapter() {
    override fun getListSize(): Int {
        if (newsList == null) return 0
        return newsList?.size!!
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.row_channel_list
    }

    override fun onBindTo(rowBinding: ViewDataBinding, position: Int) {
        if (rowBinding is RowChannelListBinding) {
            val mRowBinding = rowBinding as RowChannelListBinding
            var source = newsList?.get(position)

            mRowBinding.txtChannelName.text = source?.name
            mRowBinding.txtChannelDesc.text = source?.description
            val stringBuilder = "https://besticon-demo.herokuapp.com/icon?url=" + source?.url + "&size=64..64..120"
            mRowBinding.imgChannelImg.showImage(stringBuilder,  getPlaceHolder(0))
        }
    }

    fun notifyAdapter(newsBeanList: ArrayList<NewsChanelRespo.Source>) {
        newsList = newsBeanList
        notifyDataSetChanged()
    }
}