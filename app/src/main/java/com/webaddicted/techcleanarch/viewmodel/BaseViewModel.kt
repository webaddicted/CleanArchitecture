package com.webaddicted.techcleanarch.viewmodel

import androidx.lifecycle.ViewModel
import com.webaddicted.data.sharedpref.PreferenceMgr
import com.webaddicted.database.dao.UserInfoDao
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * Created by Deepak Sharma(webaddicted) on 15/01/20.
 */
open class BaseViewModel :ViewModel(), KoinComponent {
}