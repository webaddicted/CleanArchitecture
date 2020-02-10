package com.webaddicted.techcleanarch.view.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.webaddicted.techcleanarch.R
import com.webaddicted.techcleanarch.databinding.ActivityCommonBinding
import com.webaddicted.techcleanarch.view.base.BaseActivity

/**
 * Created by Deepak Sharma(webaddicted) on 15/01/20.
 */
class HomeActivity : BaseActivity() {

    private lateinit var mBinding: ActivityCommonBinding

    companion object {
        val TAG: String = HomeActivity::class.java.simpleName
        fun newIntent(activity: Activity) {
            activity.startActivity(Intent(activity, HomeActivity::class.java))
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_common
    }

    override fun isNetworkConnected(
        isInternetConnected: Boolean) {
        showInternetSnackbar(isInternetConnected, mBinding.txtNoInternet)
    }

    override fun initUI(binding: ViewDataBinding) {
        mBinding = binding as ActivityCommonBinding
        navigateScreen(NewsFrm.TAG)
    }

    /**
     * navigate on fragment
     * @param tag represent navigation activity
     */
    private fun navigateScreen(tag: String) {
        var frm: Fragment? = null
        when (tag) {
            NewsFrm.TAG -> frm = NewsFrm.getInstance(Bundle())
        }
        if (frm != null) navigateFragment(R.id.container, frm, false)
    }
}