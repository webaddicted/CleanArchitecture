package com.webaddicted.data.repo

import com.webaddicted.data.sharedpref.PreferenceMgr
import com.webaddicted.database.dao.UserInfoDao
import com.webaddicted.network.utils.SafeApiRequest
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * Created by Deepak Sharma(webaddicted) on 15/01/20.
 */
open class BaseRepository : SafeApiRequest(),KoinComponent {
    protected val preferenceMgr: PreferenceMgr by inject()
    protected val userInfoDao: UserInfoDao by inject()

}