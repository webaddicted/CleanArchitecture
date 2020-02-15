package com.webaddicted.techcleanarch.view.base

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.webaddicted.network.utils.ApiResponse
import com.webaddicted.network.utils.ApiStatus
import com.webaddicted.techcleanarch.R
import com.webaddicted.techcleanarch.global.common.NetworkChangeReceiver
import com.webaddicted.techcleanarch.global.misc.*
import com.webaddicted.techcleanarch.view.dialog.LoaderDialog
import com.webaddicted.techcleanarch.view.splash.SplashActivity
import org.koin.android.ext.android.inject

/**
 * Created by Deepak Sharma(webaddicted) on 15/01/20.
 */
abstract class BaseActivity : AppCompatActivity(), View.OnClickListener {
    private val mediaPicker: MediaPickerUtils by inject()
    private var loaderDialog: LoaderDialog? = null

    companion object {
        val TAG = BaseActivity::class.java.simpleName
    }

    abstract fun getLayout(): Int

    abstract fun initUI(binding: ViewDataBinding)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out)
        supportActionBar?.hide()
        fullScreen()
        GlobalUtility.hideKeyboard(this)
        val layoutResId = getLayout()
        val binding: ViewDataBinding?
        if (layoutResId != 0) {
            try {
                binding = DataBindingUtil.setContentView(this, layoutResId)
                binding?.let { initUI(it) }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
        if (loaderDialog == null) {
            loaderDialog = LoaderDialog.dialog()
            loaderDialog?.isCancelable = false
        }
        getNetworkStateReceiver()
    }

    private fun fullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            if (window != null) {
                window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = Color.TRANSPARENT
            }
        }
    }

    protected fun setNavigationColor(color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window?.navigationBarColor = color
        }
    }

    /**
     * broadcast receiver for check internet connectivity
     *
     * @return
     */
    private fun getNetworkStateReceiver() {
        NetworkChangeReceiver.isInternetAvailable(object :
            NetworkChangeReceiver.ConnectivityReceiverListener {
            override fun onNetworkConnectionChanged(networkConnected: Boolean) {
                try {
                    if(!(this@BaseActivity is SplashActivity))
                    GlobalUtility.initSnackBar(this@BaseActivity, networkConnected)
                } catch (exception: Exception) {
                    exception.printStackTrace()
                    Lg.d(TAG, "getNetworkStateReceiver : $exception")
                }
            }
        })
    }

    /**
     * placeholder type for image
     *
     * @param placeholderType position of string array placeholder
     * @return
     */
    protected fun getPlaceHolder(placeholderType: Int): String {
        val placeholderArray = resources.getStringArray(R.array.image_loader)
        return placeholderArray[placeholderType]
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out)
    }

    override fun onClick(v: View) {}

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        PermissionHelper.onRequestPermissionsResult(this, requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == mediaPicker.REQUEST_CAMERA_VIDEO || requestCode == mediaPicker.REQUEST_SELECT_FILE_FROM_GALLERY) {
                mediaPicker.onActivityResult(this, requestCode, resultCode, data)
            }
        }
    }

    fun navigateFragment(layoutContainer: Int, fragment: Fragment, isEnableBackStack: Boolean) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.setCustomAnimations(R.anim.trans_left_in, R.anim.trans_left_out, R.anim.trans_right_in, R.anim.trans_right_out)
        fragmentTransaction.replace(layoutContainer, fragment)
        if (isEnableBackStack)
            fragmentTransaction.addToBackStack(fragment.javaClass.simpleName)
        fragmentTransaction.commitAllowingStateLoss()
    }

    fun navigateAddFragment(layoutContainer: Int, fragment: Fragment, isEnableBackStack: Boolean) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.setCustomAnimations(R.anim.trans_left_in, R.anim.trans_left_out, R.anim.trans_right_in, R.anim.trans_right_out)
        fragmentTransaction.add(layoutContainer, fragment)
        if (isEnableBackStack)
            fragmentTransaction.addToBackStack(fragment.javaClass.simpleName)
        fragmentTransaction.commitAllowingStateLoss()
    }

    fun showApiLoader() {
        try {
            if (loaderDialog != null) {
                val fragment = supportFragmentManager.findFragmentByTag(LoaderDialog.TAG)
                if (fragment != null) supportFragmentManager.beginTransaction().remove(fragment).commit()
                supportFragmentManager.let { loaderDialog?.show(it, LoaderDialog.TAG) }
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
            Log.d(TAG, "ok $exception")
        }
    }

    fun hideApiLoader() {
        try {
            if (loaderDialog != null && loaderDialog?.isVisible!!) loaderDialog?.dismiss()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    fun <T> callApiResponse(
        lifecycleOwner: LifecycleOwner,
        respo: MutableLiveData<T>,
        apiChangeListener: ApiChangeListener
    ) {
        respo.removeObservers(lifecycleOwner)
        respo.observe(lifecycleOwner, object : Observer<T> {
            override fun onChanged(t: T) {
                val dataResult = t as ApiResponse<*>
                when (dataResult.status) {
                    ApiStatus.LOADING -> {
                        showApiLoader()
                        return
                    }
                    ApiStatus.ERROR -> {
                        hideApiLoader()
                        if (dataResult.message != null && dataResult.message?.length!! > 0)
                            ValidationHelper.showSnackBar(
                                findViewById(android.R.id.content),
                                dataResult.message!!
                            )
                        else showToast(getString(R.string.something_went_wrong))
                    }
                }
                apiChangeListener.onChange(t)
                respo.removeObserver(this)
            }
        })
    }

    interface ApiChangeListener {
        fun <T> onChange(t: T?)
    }
}