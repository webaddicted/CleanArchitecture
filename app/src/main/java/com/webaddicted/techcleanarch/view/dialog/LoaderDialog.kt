package com.webaddicted.techcleanarch.view.dialog

import android.view.View
import androidx.databinding.ViewDataBinding
import com.webaddicted.techcleanarch.R
import com.webaddicted.techcleanarch.databinding.DialogLoaderBinding
import com.webaddicted.techcleanarch.global.misc.DialogUtil
import com.webaddicted.techcleanarch.view.base.BaseDialog
/**
 * Created by Deepak Sharma(webaddicted) on 15/01/20.
 */
class LoaderDialog : BaseDialog() {
    private lateinit var mBinding: DialogLoaderBinding
    companion object {
        val TAG = LoaderDialog::class.java.simpleName
        fun dialog(): LoaderDialog {
            val dialog= LoaderDialog()
            return dialog
        }
    }

    override fun getLayout(): Int {
        return R.layout.dialog_loader
    }

    override fun onViewsInitialized(binding: ViewDataBinding?, view: View) {
        mBinding = binding as DialogLoaderBinding
    }

    override fun onResume() {
        super.onResume()
        DialogUtil.modifyDialogBounds(activity, dialog)
    }

}
