package com.webaddicted.techcleanarch.view.home

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.webaddicted.techcleanarch.R
import com.webaddicted.techcleanarch.databinding.FrmProfileBinding
import com.webaddicted.techcleanarch.global.annotationdef.MediaPickerType
import com.webaddicted.techcleanarch.global.misc.FileUtils
import com.webaddicted.techcleanarch.global.misc.MediaPickerUtils
import com.webaddicted.techcleanarch.global.misc.showImage
import com.webaddicted.techcleanarch.global.misc.visible
import com.webaddicted.techcleanarch.view.base.BaseFragment
import java.io.File
/**
 * Created by Deepak Sharma(webaddicted) on 15/01/20.
 */
class ProfileFrm : BaseFragment() {
    private lateinit var mBinding: FrmProfileBinding

    companion object {
        val TAG = ProfileFrm::class.java.simpleName
        fun getInstance(bundle: Bundle): ProfileFrm {
            val fragment = ProfileFrm()
            fragment.arguments = bundle
            return ProfileFrm()
        }
    }

    override fun getLayout(): Int {
        return R.layout.frm_profile
    }

    override fun initUI(binding: ViewDataBinding?, view: View) {
        mBinding = binding as FrmProfileBinding
        init()
        clickListener()
    }

    private fun init() {
        mBinding.toolbar.imgBack.visible()
        mBinding.toolbar.txtToolbarTitle.text = resources.getString(R.string.my_profile)
    }

    private fun clickListener() {
        mBinding.btnCaptureImage.setOnClickListener(this)
        mBinding.btnPickImage.setOnClickListener(this)
        mBinding.toolbar.imgBack.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.btn_capture_image -> requestCamera(MediaPickerType.CAPTURE_IMAGE)
            R.id.btn_pick_image -> requestCamera(MediaPickerType.SELECT_IMAGE)
            R.id.img_back -> activity?.onBackPressed()
        }
    }
    private fun requestCamera(@MediaPickerType.MediaType captureImage: Int) {
        mediaPicker.selectMediaOption(activity!!,
            captureImage,
            FileUtils.subFolder(),
            object : MediaPickerUtils.ImagePickerListener {
                override fun imagePath(filePath: List<File>) {
                    mBinding.imgProfile.showImage(filePath[0], getPlaceHolder(0))
                }
            })
    }

}

