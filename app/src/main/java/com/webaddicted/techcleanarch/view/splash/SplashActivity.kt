package com.webaddicted.techcleanarch.view.splash

import android.os.Handler
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.webaddicted.techcleanarch.R
import com.webaddicted.techcleanarch.databinding.ActivitySplashBinding
import com.webaddicted.techcleanarch.global.misc.AppConstant
import com.webaddicted.techcleanarch.view.base.BaseActivity
import com.webaddicted.techcleanarch.view.home.HomeActivity

/**
 * Created by Deepak Sharma(webaddicted) on 15/01/20.
 */
class SplashActivity : BaseActivity() {
    val TAG: String = SplashActivity::class.java.simpleName
    private lateinit var mBinding: ActivitySplashBinding
    override fun getLayout(): Int {
        return R.layout.activity_splash
    }

    override fun initUI(binding: ViewDataBinding) {
        mBinding = binding as ActivitySplashBinding
        init()
        setNavigationColor(ContextCompat.getColor(this, R.color.app_color))
    }

    private fun init() {
        navigateToNext()
    }

    /**
     * navigate to welcome activity after Splash timer Delay
     */
    private fun navigateToNext() {
        Handler().postDelayed({
            HomeActivity.newIntent(this)
            finish()
        }, AppConstant.SPLASH_DELAY)
    }
}