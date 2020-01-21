package com.webaddicted.data.repo

import com.webaddicted.data.sharedPref.PreferenceMgr
import com.webaddicted.database.dao.UserInfoDao
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * Created by Deepak Sharma(webaddicted) on 15/01/20.
 */
open class BaseRepository : KoinComponent {
    protected val preferenceMgr: PreferenceMgr by inject()
    protected val userInfoDao: UserInfoDao by inject()

}