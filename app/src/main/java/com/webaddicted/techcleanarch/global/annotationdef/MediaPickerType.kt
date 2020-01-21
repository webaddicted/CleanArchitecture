package com.webaddicted.techcleanarch.global.annotationdef

import androidx.annotation.IntDef
/**
 * Created by Deepak Sharma(webaddicted) on 15/01/20.
 */
class MediaPickerType {
    @IntDef(CAPTURE_IMAGE, SELECT_IMAGE, SCAN_DL)
    @Retention(AnnotationRetention.SOURCE)
    annotation class MediaType

    companion object {
        const val CAPTURE_IMAGE = 500
        const val SELECT_IMAGE = 501
        const val SCAN_DL = 502
    }
}