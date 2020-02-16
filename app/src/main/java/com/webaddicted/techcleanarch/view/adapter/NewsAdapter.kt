package com.webaddicted.techcleanarch.view.adapter

import androidx.databinding.ViewDataBinding
import com.webaddicted.model.news.NewsChanelRespo
import com.webaddicted.techcleanarch.view.base.BaseAdapter
import com.webaddicted.techcleanarch.R
import com.webaddicted.techcleanarch.databinding.RowChannelListBinding
import com.webaddicted.techcleanarch.global.misc.showImage

/**
 * Created by Deepak Sharma(webaddicted) on 15/01/20.
 */
class NewsAdapter(private var dataResultList: ArrayList<NewsChanelRespo.Source>?) : BaseAdapter() {
    override fun getListSize(): Int {
        if (dataResultList == null) return 0
        return dataResultList?.size!!
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.row_channel_list
    }

    override fun onBindTo(rowBinding: ViewDataBinding, position: Int) {
        if (rowBinding is RowChannelListBinding) {
            val source = dataResultList?.get(position)

            rowBinding.txtChannelName.text = source?.name
            rowBinding.txtChannelDesc.text = source?.description
            val stringBuilder =
                "https://besticon-demo.herokuapp.com/icon?url=" + source?.url + "&size=64..64..120"
            rowBinding.imgChannelImg.showImage(stringBuilder, getPlaceHolder(0))
            onClickListener(rowBinding, rowBinding.cardview, position)
        }
    }

    fun notifyAdapter(beanList: ArrayList<NewsChanelRespo.Source>) {
        dataResultList = beanList
        notifyDataSetChanged()
    }
}