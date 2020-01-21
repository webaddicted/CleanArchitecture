package com.webaddicted.techcleanarch.global.misc

import android.R.attr.data
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.view.WindowManager
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import java.io.*
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Deepak Sharma(webaddicted) on 15/01/20.
 */
class FileUtils {

    /**
     * Method to get size of file in kb
     *
     * @param file
     * @return
     */
    fun getFileSizeInKb(file: File): Long {
        return file.length() / 1024
    }

    companion object {
        private val APP_FOLDER = "kotlinProject"
        private val SUB_PROFILE = "/profile"
        private val SEPARATOR = "/"
        private val JPEG = ".jpeg"
        private val PNG = ".png"

        /**
         * This method is used to create application specific folder on filesystem
         */
        fun createApplicationFolder() {
            var f = File(Environment.getExternalStorageDirectory().toString(), File.separator + APP_FOLDER)
            f.mkdirs()
            f = File(Environment.getExternalStorageDirectory().toString(), File.separator + APP_FOLDER + SUB_PROFILE)
            f.mkdirs()
        }

        /**
         * Method to return file object
         *
         * @return File object
         */
        fun appFolder(): File {
            return File(Environment.getExternalStorageDirectory().toString(), File.separator + APP_FOLDER)
        }

        /**
         * Method to return file from sub folder
         *
         * @return File object
         */
        fun subFolder(): File {
            return File(Environment.getExternalStorageDirectory().toString(), File.separator + APP_FOLDER + SUB_PROFILE)
        }


        fun thumbFolder(): File {
            return File(
                Environment.getExternalStorageDirectory().toString(),
                File.separator + APP_FOLDER + SUB_PROFILE
            )
        }


        /**
         * Method to get filename from url
         *
         * @param url
         * @return
         */
        fun getFileNameFromUrl(url: URL): String {
            val urlString = url.file
            return urlString.substring(urlString.lastIndexOf('/') + 1).split("\\?".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0].split(
                "#".toRegex()
            ).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
        }

        /**
         * Method to get path from Uri
         *
         * @param contentUri
         * @return
         */
        fun getPathFromUri(context: Context, contentUri: Uri): File {
            var res: String? = null
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = context.getContentResolver().query(contentUri, proj, null, null, null)
            if (cursor?.moveToFirst()!!) {
                val column_index = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                res = cursor?.getString(column_index)
            }
            cursor?.close()

            return File(res)
        }

        /**
         * Method to save thumbnail of game image
         *
         * @param bitmap
         * @return
         */
        fun saveGameThumb(bitmap: Bitmap): File {
            val filename = System.currentTimeMillis().toString() + JPEG
            val sd = FileUtils.appFolder()
            val dest = File(sd, filename)
            try {
                val out = FileOutputStream(dest)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
                out.flush()
                out.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return dest
        }

        /**
         * Method to save bitmap
         *
         * @param bitmap
         * @return
         */
        fun saveBitmapImage(bitmap: Bitmap): File {
            val filename = System.currentTimeMillis().toString() + JPEG
            val dest = File(subFolder(), filename)
            try {
                val out = FileOutputStream(dest)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out)
                out.flush()
                out.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return dest
        }

        fun saveBitmapImage(bitmap: Bitmap, fileLocation: File, fileName: String): File {
            val filename = fileName + "_" + System.currentTimeMillis() + JPEG
            val dest = File(fileLocation, filename)
            try {
                val out = FileOutputStream(dest)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out)
                out.flush()
                out.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return dest
        }

        /**
         * Method to save captured pic in SD card of device
         *
         * @param bitmap
         * @param currentDate
         * @return
         */
        fun storeCameraPhotoInSDCard(bitmap: Bitmap, currentDate: String): File {
            val outputFile = File(Environment.getExternalStorageDirectory(), "photo_$currentDate.jpg")
            try {
                val fileOutputStream = FileOutputStream(outputFile)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
                fileOutputStream.flush()
                fileOutputStream.close()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return outputFile
        }

        /**
         * Method to save bitmap
         *
         * @param bmp
         * @return
         */
        fun savebitmap(bmp: Bitmap): File? {
            val extStorageDirectory = Environment.getExternalStorageDirectory().toString() + "/criiio/profile"
            var outStream: OutputStream? = null

            // String temp = null;
            val file = File(extStorageDirectory, System.currentTimeMillis().toString() + "_img.png")

            try {
                outStream = FileOutputStream(file)
                bmp.compress(Bitmap.CompressFormat.PNG, 100, outStream)
                outStream.flush()
                outStream.close()

            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }

            return file
        }

        /**
         * Method to save bitmap
         *
         * @param bitmap
         * @param fileName
         * @return
         */
        fun saveBitmapImg(bitmap: Bitmap, fileName: String): File {
            var filename = System.currentTimeMillis().toString()
            if (fileName.endsWith(".png"))
                filename = filename + PNG
            else
                filename = filename + JPEG
            val dest = File(appFolder(), filename)
            try {
                val out = FileOutputStream(dest)
                if (fileName.endsWith(".png"))
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, out)
                else
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)

                out.flush()
                out.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return dest
        }


        /**
         * Method to return Uri from file
         *
         * @param file
         * @return
         */
        fun fileIntoUri(file: File): Uri {
            return Uri.fromFile(file)
        }

        /**
         * Method to get Mimetype from url
         *
         * @param url
         * @return
         */
        fun getMimeType(url: String): String? {
            var type: String? = null
            val extension = MimeTypeMap.getFileExtensionFromUrl(url)
            if (extension != null) {
                type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
            }
            if (type == null) {
                type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension?.toLowerCase())
            }
            return type
        }

        fun getFileSizeInMb(file: File): Long {
            // Get length of file in bytes
            val fileSizeInBytes = file.length()
            // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
            val fileSizeInKB = fileSizeInBytes / 1024
            // Convert the KB to MegaBytes (1 MB = 1024 KBytes)
            return fileSizeInKB / 1024
        }

        /**
         * Method to get size of file in mb
         *
         * @param file
         * @return
         */
        fun getFileSizeInMbTest(file: File): String {
            // Get length of file in bytes
            val fileSizeInBytes = file.length()
            // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
            val fileSizeInKB = fileSizeInBytes / 1024
            // Convert the KB to MegaBytes (1 MB = 1024 KBytes)
            val fileSizeInMB = fileSizeInKB / 1024
            return if (fileSizeInMB > 0)
                "$fileSizeInMB MB "
            else
                "$fileSizeInKB KB "
        }

        /**
         * Method to get filename from file
         *
         * @param file
         * @return
         */
        fun getFileName(file: File): String {
            return file.path.substring(file.path.lastIndexOf("/") + 1)
        }
        //    {START CAPTURE IMAGE PROCESS}
        /**
         * Method to create new file of captured image
         *
         * @return
         * @throws IOException
         */
        fun createNewCaptureFile(): File {
            val mFile = File(
                Environment.getExternalStorageDirectory().toString(),
                File.separator + APP_FOLDER + SUB_PROFILE + File.separator + "IMG_"+System.currentTimeMillis() + ".jpg"
            )
            mFile.createNewFile()
            return mFile
        }
        /**
         *
         * Method to get Intent
         *
         * @param activity
         * @param photoFile
         * @return
         */
        fun getCaptureImageIntent(activity: Activity, photoFile: File?): Intent {
            var takePictureIntent: Intent? = null
            takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val photoURI = FileProvider.getUriForFile(activity, activity.packageName+ ".provider", photoFile!!)
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            return takePictureIntent
        }

        /**
         * Method to create temporary image file
         *
         * @param context
         * @return
         * @throws IOException
         */
        @Throws(IOException::class)
        fun createTempImageFile(context: Context): File {
            val imageFileName = "Img_" + System.currentTimeMillis() + "_"
            val storageDir = context.externalCacheDir
            return File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir      /* directory */
            )
        }


        /**
         * Method for sampling image
         *
         * @param context
         * @param imagePath
         * @return
         */
        internal fun resamplePic(context: Context, imagePath: String): Bitmap {

            // Get device screen size information
            val metrics = DisplayMetrics()
            val manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            manager.defaultDisplay.getMetrics(metrics)

            val targetH = metrics.heightPixels
            val targetW = metrics.widthPixels

            // Get the dimensions of the original bitmap
            val bmOptions = BitmapFactory.Options()
            bmOptions.inJustDecodeBounds = true
            BitmapFactory.decodeFile(imagePath, bmOptions)
            val photoW = bmOptions.outWidth
            val photoH = bmOptions.outHeight

            // Determine how much to scale down the image
            val scaleFactor = Math.min(photoW / targetW, photoH / targetH)

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false
            bmOptions.inSampleSize = scaleFactor

            return BitmapFactory.decodeFile(imagePath)
        }

        /**
         * Helper method for saving the image.
         *
         * @param context The application context.
         * @param image   The image to be saved.
         * @return The path of the saved image.
         */
        internal fun saveImage(context: Context, image: Bitmap): File {
            var imageFile: File? = null
            var savedImagePath: String? = null
            val imageFileName = "Img" + System.currentTimeMillis() + ".jpg"
            imageFile = File(subFolder(), imageFileName)
            savedImagePath = imageFile.absolutePath
            try {
                val fOut = FileOutputStream(imageFile)
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
                fOut.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            updateGallery(context, savedImagePath)
            return imageFile
        }
        fun saveImage(image: Bitmap?, folderPath: File?): File {
            var savedImagePath: String? = null
            val timeStamp = SimpleDateFormat(
                "yyyyMMdd_HHmmss",
                Locale.getDefault()
            ).format(Date())
            val imageFileName = "JPEG_$timeStamp.jpg"
            val imageFile = File(folderPath, imageFileName)
            savedImagePath = imageFile.absolutePath
            try {
                val fOut = FileOutputStream(imageFile)
                image?.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
                fOut.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return imageFile
        }
        /**
         * Method to update phone gallery after capturing file
         *
         * @param context
         * @param imagePath
         */
        fun updateGallery(context: Context, imagePath: String?) {
            val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            val f = File(imagePath)
            val contentUri = Uri.fromFile(f)
            mediaScanIntent.data = contentUri
            context.sendBroadcast(mediaScanIntent)
        }

        fun getBitmapFromFile(image: File): Bitmap {
            val bmOptions = BitmapFactory.Options()
            return BitmapFactory.decodeFile(image.absolutePath, bmOptions)
        }
    }

    //    {END CAPTURE IMAGE PROCESS}
}