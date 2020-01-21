package com.webaddicted.techcleanarch.global.koin


import androidx.room.Room
import com.webaddicted.database.database.AppDatabase
import com.webaddicted.database.utils.DbConstant
import com.webaddicted.techcleanarch.AppApplication
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
 * Created by Deepak Sharma(webaddicted) on 15/01/20.
 */
val dbModule = module(override = true) {

    single {
        Room.databaseBuilder(
            (androidApplication() as AppApplication),
            AppDatabase::class.java,
            DbConstant.DB_NAME
        ).allowMainThreadQueries().build()
    }

    single { (get() as AppDatabase).userInfoDao() }
}