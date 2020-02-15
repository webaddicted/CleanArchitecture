package com.webaddicted.techcleanarch.view.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.webaddicted.network.utils.ApiResponse
import com.webaddicted.network.utils.ApiStatus
import com.webaddicted.techcleanarch.R
import com.webaddicted.techcleanarch.global.misc.GlobalUtility
import com.webaddicted.techcleanarch.global.misc.MediaPickerUtils
import com.webaddicted.techcleanarch.global.misc.ValidationHelper
import com.webaddicted.techcleanarch.global.misc.showToast
import com.webaddicted.techcleanarch.view.base.BaseActivity.Companion.TAG
import com.webaddicted.techcleanarch.view.dialog.LoaderDialog
import org.koin.android.ext.android.inject

/**
 * Created by Deepak Sharma on 15/1/19.
 */
abstract class BaseFragment : Fragment(), View.OnClickListener {
    private lateinit var mBinding: ViewDataBinding

    protected val mediaPicker: MediaPickerUtils by inject()
    abstract fun getLayout(): Int
    protected abstract fun initUI(binding: ViewDataBinding?, view: View)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUI(mBinding, view)
        super.onViewCreated(view, savedInstanceState)
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
    fun <T> callApiResponse(lifecycleOwner: LifecycleOwner, respo: MutableLiveData<T>, apiChangeListener: BaseActivity.ApiChangeListener) {
        (activity as BaseActivity).callApiResponse(lifecycleOwner,respo,apiChangeListener)
    }

    protected fun hideApiLoader() = (activity as BaseActivity).hideApiLoader()

    protected fun showApiLoader() = (activity as BaseActivity).showApiLoader()

    fun getPlaceHolder(imageLoaderPos: Int): String {
        val imageLoader = resources.getStringArray(R.array.image_loader)
        return imageLoader[imageLoaderPos]
    }
}
