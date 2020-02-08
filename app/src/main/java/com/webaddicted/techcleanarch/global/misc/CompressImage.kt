package com.webaddicted.techcleanarch.global.misc

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.*
import android.media.ExifInterface
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import kotlin.math.roundToInt

/**
 * Created by Deepak Sharma(webaddicted) on 15/01/20.
 */
class CompressImage {
    companion object {
        private val TAG = CompressImage::class.java.simpleName
        private var mContext: Context? = null
        private val imagePath = Environment.getExternalStorageDirectory().toString() + "/comp/"

        fun compressImage(context: Activity, imageUri: String): File {
            mContext = context
            //        String filePath = getRealPathFromURI(imageUri);
            var scaledBitmap: Bitmap? = null
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            var bmp = BitmapFactory.decodeFile(imageUri, options)
            var actualHeight = options.outHeight
            var actualWidth = options.outWidth
            val maxHeight = 816.0f
            val maxWidth = 612.0f
            var imgRatio = (actualWidth / actualHeight).toFloat()
            val maxRatio = maxWidth / maxHeight

            if (actualHeight > maxHeight || actualWidth > maxWidth) {
                when {
                    imgRatio < maxRatio -> {
                        imgRatio = maxHeight / actualHeight
                        actualWidth = (imgRatio * actualWidth).toInt()
                        actualHeight = maxHeight.toInt()
                    }
                    imgRatio > maxRatio -> {
                        imgRatio = maxWidth / actualWidth
                        actualHeight = (imgRatio * actualHeight).toInt()
                        actualWidth = maxWidth.toInt()
                    }
                    else -> {
                        actualHeight = maxHeight.toInt()
                        actualWidth = maxWidth.toInt()
                    }
                }
            }

            options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight)
            options.inJustDecodeBounds = false
            options.inDither = false
            options.inPurgeable = true
            options.inInputShareable = true
            options.inTempStorage = ByteArray(16 * 1024)

            try {
                bmp = BitmapFactory.decodeFile(imageUri, options)
            } catch (exception: OutOfMemoryError) {
                exception.printStackTrace()

            }

            try {
                scaledBitmap =
                    Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888)
            } catch (exception: OutOfMemoryError) {
                exception.printStackTrace()
            }

            val ratioX = actualWidth / options.outWidth.toFloat()
            val ratioY = actualHeight / options.outHeight.toFloat()
            val middleX = actualWidth / 2.0f
            val middleY = actualHeight / 2.0f

            val scaleMatrix = Matrix()
            scaleMatrix.setScale(ratioX, ratioY, middleX, middleY)

            val canvas = Canvas(scaledBitmap!!)
            canvas.setMatrix(scaleMatrix)
            canvas.drawBitmap(
                bmp,
                middleX - bmp.width / 2,
                middleY - bmp.height / 2,
                Paint(Paint.FILTER_BITMAP_FLAG)
            )

            val exif: ExifInterface
            try {
                exif = ExifInterface(imageUri)

                val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0)
                Lg.d(TAG, "Exif: $orientation")
                val matrix = Matrix()
                when (orientation) {
                    6 -> {
                        matrix.postRotate(90f)
                        Lg.d(TAG, "Exif: $orientation")
                    }
                    3 -> {
                        matrix.postRotate(180f)
                        Lg.d(TAG, "Exif: $orientation")
                    }
                    8 -> {
                        matrix.postRotate(270f)
                        Lg.d(TAG, "Exif: $orientation")
                    }
                }
                scaledBitmap = Bitmap.createBitmap(
                    scaledBitmap,
                    0,
                    0,
                    scaledBitmap.width,
                    scaledBitmap.height,
                    matrix,
                    true
                )
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return saveImage(scaledBitmap!!)
        }

        @SuppressLint("Recycle")
        private fun getRealPathFromURI(contentURI: String): String? {
            val contentUri = Uri.parse(contentURI)
            val cursor = mContext!!.contentResolver.query(contentUri, null, null, null, null)
            return if (cursor == null) {
                contentUri.path
            } else {
                cursor.moveToFirst()
                val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                cursor.getString(idx)
            }
        }

        private fun calculateInSampleSize(
            options: BitmapFactory.Options,
            reqWidth: Int,
            reqHeight: Int
        ): Int {
            val height = options.outHeight
            val width = options.outWidth
            var inSampleSize = 1

            if (height > reqHeight || width > reqWidth) {
                val heightRatio = (height.toFloat() / reqHeight.toFloat()).roundToInt()
                val widthRatio = (width.toFloat() / reqWidth.toFloat()).roundToInt()
                inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
            }
            val totalPixels = (width * height).toFloat()
            val totalReqPixelsCap = (reqWidth * reqHeight * 2).toFloat()

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++
            }

            return inSampleSize
        }

        private fun saveImage(finalBitmap: Bitmap): File {
            Lg.d(TAG, "SaveImage: " + finalBitmap.toString().length)
            val dirFile: File = if (imagePath != null) {
                //            dirFile = new File(Environment.getExternalStorageDirectory() + imagePath);
                FileUtils.subFolder()
            } else
                File(mContext!!.cacheDir.toString())
            if (!dirFile.exists()) {
                dirFile.mkdirs()
            }
            val files = File(dirFile, System.currentTimeMillis().toString() + ".jpg")
            val fileOutputStream: FileOutputStream?
            try {
                files.createNewFile()
                fileOutputStream = FileOutputStream(files)
                finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream)
                fileOutputStream.flush()
                fileOutputStream.close()
            } catch (fileNotFoundException: FileNotFoundException) {
                Log.e(TAG, "File not found!", fileNotFoundException)
            } catch (ioException: IOException) {
                Lg.e(TAG, "Unable to write to file!", ioException)
            }

            return files
        }
    }
}
