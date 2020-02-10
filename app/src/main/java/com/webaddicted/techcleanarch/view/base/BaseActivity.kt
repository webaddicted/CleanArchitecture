package com.webaddicted.techcleanarch.view.base

import android.Manifest
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.TransitionDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.webaddicted.techcleanarch.global.misc.Lg
import com.webaddicted.techcleanarch.R
import com.webaddicted.techcleanarch.global.common.NetworkChangeReceiver
import com.webaddicted.techcleanarch.global.misc.*
import org.koin.android.ext.android.inject

/**
 * Created by Deepak Sharma(webaddicted) on 15/01/20.
 */
abstract class BaseActivity : AppCompatActivity(), View.OnClickListener {
    private val mediaPicker: MediaPickerUtils by inject()

    companion object {
        val TAG = BaseActivity::class.java.simpleName
    }

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
                initUI(binding)
            } catch (e: Exception) {
                e.printStackTrace()
            }
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

    abstract fun getLayout(): Int

    abstract fun initUI(binding: ViewDataBinding)
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

   fun checkStoragePermission(): ArrayList<String> {
        val multiplePermission = ArrayList<String>()
        multiplePermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        multiplePermission.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        multiplePermission.add(Manifest.permission.CAMERA)
        return multiplePermission
    }

    fun checkLocationPermission(): ArrayList<String> {
        val multiplePermission = ArrayList<String>()
        multiplePermission.add(Manifest.permission.ACCESS_FINE_LOCATION)
        multiplePermission.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        return multiplePermission
    }

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

    /**
     * broadcast receiver for check internet connectivity
     *
     * @return
     */
    private fun getNetworkStateReceiver() {
        NetworkChangeReceiver.isInternetAvailable(object :
            NetworkChangeReceiver.ConnectivityReceiverListener {
            override fun onNetworkConnectionChanged(isConnected: Boolean) {
                try {
                    isNetworkConnected(isConnected)
                } catch (exception: Exception) {
                    Lg.d(TAG, "getNetworkStateReceiver : $exception")
                }
            }
        })
    }

    abstract fun isNetworkConnected(isConnected: Boolean)

    protected fun showInternetSnackbar(internetConnected: Boolean, txtNoInternet: TextView) {
        if (internetConnected) {
            txtNoInternet.text = getString(R.string.back_online)
            val color = arrayOf(
                ColorDrawable(ContextCompat.getColor(this,R.color.red_ff090b)),
                ColorDrawable(ContextCompat.getColor(this,R.color.green_00de4a))
            )
            val trans = TransitionDrawable(color)
            txtNoInternet.background = (trans)
            trans.startTransition(500)
            val handler = Handler()
            handler.postDelayed({ txtNoInternet.gone() }, 1300)
        } else {
            txtNoInternet.text = getString(R.string.no_internet_connection)
            txtNoInternet.setBackgroundResource(R.color.red_ff090b)
            txtNoInternet.visible()
        }
    }

}