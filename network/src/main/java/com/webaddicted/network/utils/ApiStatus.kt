package com.webaddicted.network.utils

import androidx.annotation.IntDef
/**
 * Created by Deepak Sharma(webaddicted) on 15/01/20.
 */
class ApiStatus {
    @IntDef(LOADING, SUCCESS, ERROR)
    @Retention(AnnotationRetention.SOURCE)
    annotation class ApiStatus

    companion object {
        const val LOADING = 800
        const val SUCCESS = 801
        const val ERROR = 802
    }
}