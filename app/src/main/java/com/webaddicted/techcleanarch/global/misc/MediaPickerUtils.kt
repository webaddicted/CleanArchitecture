package com.webaddicted.techcleanarch.global.misc

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import com.webaddicted.kotlinproject.global.common.CompressImage
import com.webaddicted.kotlinproject.global.common.Lg
import com.webaddicted.techcleanarch.R
import com.webaddicted.techcleanarch.global.annotationdef.MediaPickerType
import com.webaddicted.techcleanarch.global.common.MediaPickerHelper
import java.io.File
import java.util.ArrayList
/**
 * Created by Deepak Sharma(webaddicted) on 15/01/20.
 */
class MediaPickerUtils {
    private var captureImageFile: File? = null
    private val TAG = MediaPickerUtils::class.java.simpleName
    val REQUEST_CAMERA_VIDEO = 5000
    val REQUEST_SELECT_FILE_FROM_GALLERY = 5002
    private var mImagePickerListener: ImagePickerListener? = null
    var mMimeTypes = arrayOf("image/jpeg", "image/png", "image/jpg")
    var mVideoMimeTypes = arrayOf("video/3gp", "video/mpeg", "video/avi", "video/mp4")
    var mImageMimeTypes = arrayOf("image/jpeg", "image/png", "image/jpg")
    private var mfilePath: File? = null

    /**
     * select media option
     * @param activity
     * @param fileType
     * @param imagePickerListener
     */
    fun selectMediaOption(
        activity: Activity,
        @MediaPickerType.MediaType fileType: Int,
        filePath: File,
        imagePickerListener: ImagePickerListener
    ) {
        mImagePickerListener = imagePickerListener
        mfilePath = filePath
        checkPermission(activity, fileType)
    }

    /**
     * check file storage permission
     *
     * @param activity
     * @param fileType
     */
    private fun checkPermission(activity: Activity, @MediaPickerType.MediaType fileType: Int) {
        val locationList = ArrayList<String>()
        locationList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        locationList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        locationList.add(Manifest.permission.CAMERA)
      PermissionHelper.requestMultiplePermission(
                activity,
                locationList,
                object : PermissionHelper.Companion.PermissionListener {
                    override fun onPermissionGranted(mCustomPermission: List<String>) {
                        FileUtils.createApplicationFolder()
                        selectMediaType(activity, fileType)
                    }

                    override fun onPermissionDenied(mCustomPermission: List<String>) {

                    }
                })
    }

    private fun selectMediaType(activity: Activity, @MediaPickerType.MediaType fileType: Int) {
        var intent: Intent? = null
        when (fileType) {
            //            capture image for native camera
            MediaPickerType.CAPTURE_IMAGE -> {
                captureImageFile = FileUtils.createNewCaptureFile()
                val intent = FileUtils.getCaptureImageIntent(activity, captureImageFile)
                activity.startActivityForResult(intent, REQUEST_CAMERA_VIDEO)
            }
            //            pick image from gallery
            MediaPickerType.SELECT_IMAGE -> {
                intent = Intent()
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mImageMimeTypes)
                intent.action = Intent.ACTION_GET_CONTENT
                activity.startActivityForResult(
                    Intent.createChooser(
                        intent,
                        activity.resources.getString(R.string.select_picture)
                    ), REQUEST_SELECT_FILE_FROM_GALLERY
                )
            }
            else -> throw IllegalStateException("Unexpected value: $fileType")
        }
    }

    fun onActivityResult(activity: Activity, requestCode: Int, resultCode: Int, data: Intent?) {
        var file: MutableList<File> = ArrayList()
        var compressedFiles: File? = null
        when (requestCode) {
            REQUEST_CAMERA_VIDEO -> {
                var bitmap: Bitmap? = null
                if (data?.extras != null) {
                    bitmap = data.extras!!.get("data") as Bitmap?
                    val originalFile = FileUtils.saveImage(bitmap, mfilePath)
                    file.add(originalFile)
                } else if (data?.data != null) {
                    // in case of record video
                    file = MediaPickerHelper().getData(activity, data)
                }
                if (file.size==0) file.add(captureImageFile!!)
                    mImagePickerListener!!.imagePath(file)
            }
            REQUEST_SELECT_FILE_FROM_GALLERY -> {
                val files = MediaPickerHelper().getData(activity, data)
                for (i in files.indices) {
                    val filePath = files.get(i).toString()
                    filePath.substring(filePath.lastIndexOf(".") + 1)
                    if (filePath.contains(mMimeTypes[0]) ||
                        filePath.contains(mMimeTypes[1]) ||
                        filePath.contains(mMimeTypes[2])
                    ) {
                        compressedFiles =
                            CompressImage.compressImage(activity, files.get(i).toString())
                        Lg.d(
                            TAG,
                            "onActivityResult: old Image - " + FileUtils.getFileSizeInMbTest(
                                files.get(i)
                            ) +
                                    "\n compress image - " + FileUtils.getFileSizeInMbTest(
                                compressedFiles
                            )
                        )
                        files.set(i, compressedFiles)
                    }
                }
                mImagePickerListener!!.imagePath(files)
            }
        }
    refreshMedia(activity)
    }
    private fun refreshMedia(activity: Activity) {
        if(captureImageFile!=null && captureImageFile!!.exists()) {
            val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            intent.data = Uri.fromFile(captureImageFile)
            activity.sendBroadcast(intent)
        }
    }
    interface ImagePickerListener {
        fun imagePath(filePath: List<File>)
    }

}