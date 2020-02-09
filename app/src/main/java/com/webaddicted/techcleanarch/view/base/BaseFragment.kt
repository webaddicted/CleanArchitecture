package com.webaddicted.techcleanarch.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.webaddicted.network.utils.ApiStatus
import com.webaddicted.techcleanarch.R
import com.webaddicted.techcleanarch.global.misc.GlobalUtility
import com.webaddicted.techcleanarch.global.misc.MediaPickerUtils
import com.webaddicted.techcleanarch.global.misc.ValidationHelper
import com.webaddicted.techcleanarch.global.misc.showToast
import com.webaddicted.techcleanarch.view.dialog.LoaderDialog
import org.koin.android.ext.android.inject

/**
 * Created by Deepak Sharma on 15/1/19.
 */
abstract class BaseFragment : Fragment(), View.OnClickListener {
    private lateinit var mBinding: ViewDataBinding
    private var loaderDialog: LoaderDialog? = null
    protected val mediaPicker: MediaPickerUtils by inject()
    abstract fun getLayout(): Int
    protected abstract fun onViewsInitialized(binding: ViewDataBinding?, view: View)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        onViewsInitialized(mBinding, view)
        super.onViewCreated(view, savedInstanceState)
        if (loaderDialog == null) {
            loaderDialog = LoaderDialog.dialog()
            loaderDialog?.isCancelable = false
        }
    }

    protected fun showApiLoader() {
        if (loaderDialog != null) {
            val fragment = fragmentManager?.findFragmentByTag(LoaderDialog.TAG)
            if (fragment != null) fragmentManager?.beginTransaction()?.remove(fragment)?.commit()
            fragmentManager?.let { loaderDialog?.show(it, LoaderDialog.TAG) }
        }
    }

    protected fun hideApiLoader() {
        if (loaderDialog != null && loaderDialog?.isVisible!!) loaderDialog?.dismiss()
    }

    protected fun <T> apiResponseHandler(view: View, response: ApiResponse<T>) {
        when (response.status) {
            ApiResponse.Status.LOADING -> {
                showApiLoader()
            }
            ApiResponse.Status.ERROR -> {
                hideApiLoader()
                if (response.errorMessage != null && response.errorMessage?.length!! > 0)
                    ValidationHelper.showSnackBar(view, response.errorMessage!!)
                else activity?.showToast(getString(R.string.something_went_wrong))
            }
        }
    }

    protected fun <T> apiResponseHandler(view: View, response: com.webaddicted.network.utils.ApiResponse<T>) {
        when (response.status) {
            ApiStatus.LOADING -> {
                showApiLoader()
            }
            ApiStatus.ERROR-> {
                hideApiLoader()
                if (response.message!= null && response.message?.length!! > 0)
                    ValidationHelper.showSnackBar(view, response.message!!)
                else activity?.showToast(getString(R.string.something_went_wrong))
            }
        }
    }



    override fun onResume() {
        super.onResume()
        activity?.let { GlobalUtility.hideKeyboard(it) }
    }

    protected fun navigateFragment(
        layoutContainer: Int,
        fragment: Fragment,
        isEnableBackStack: Boolean
    ) {
        if (activity != null) {
            (activity as BaseActivity).navigateFragment(
                layoutContainer,
                fragment,
                isEnableBackStack
            )
        }
    }

    protected fun navigateAddFragment(
        layoutContainer: Int,
        fragment: Fragment,
        isEnableBackStack: Boolean
    ) {
        if (activity != null) {
            (activity as BaseActivity).navigateAddFragment(
                layoutContainer,
                fragment,
                isEnableBackStack
            )
        }
    }

    protected fun navigateChildFragment(
        layoutContainer: Int,
        fragment: Fragment,
        isEnableBackStack: Boolean
    ) {
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(layoutContainer, fragment)
        if (isEnableBackStack)
            fragmentTransaction.addToBackStack(fragment.javaClass.simpleName)
        fragmentTransaction.commitAllowingStateLoss()
    }

    override fun onClick(v: View) {
    }


    fun checkStoragePermission(): ArrayList<String> {
        return (activity as BaseActivity).checkStoragePermission()
    }

    fun checkLocationPermission(): ArrayList<String> {
        return (activity as BaseActivity).checkLocationPermission()
    }

    fun getPlaceHolder(imageLoaderPos: Int): String {
        val imageLoader = resources.getStringArray(R.array.image_loader)
        return imageLoader[imageLoaderPos]
    }
}
