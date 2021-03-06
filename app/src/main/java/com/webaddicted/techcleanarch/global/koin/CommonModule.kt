package com.webaddicted.techcleanarch.global.koin


import com.webaddicted.data.sharedpref.PreferenceMgr
import com.webaddicted.data.sharedpref.PreferenceUtils
import com.webaddicted.techcleanarch.global.misc.MediaPickerUtils
import org.koin.dsl.module

/**
 * Created by Deepak Sharma(webaddicted) on 15/01/20.
 */
val commonModelModule = module {
    single { PreferenceUtils() }
    single { PreferenceMgr(get()) }
    single { MediaPickerUtils() }

}