package com.webaddicted.techcleanarch

import android.app.Application
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import com.webaddicted.data.sharedPref.PreferenceUtils
import com.webaddicted.techcleanarch.global.common.NetworkChangeReceiver
import com.webaddicted.techcleanarch.global.koin.*
import com.webaddicted.techcleanarch.global.misc.FileUtils
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.Module

/**
 * Created by Deepak Sharma(webaddicted) on 15/01/20.
 */
class AppApplication : Application() {
    private val mNetworkReceiver = NetworkChangeReceiver()
    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        FileUtils.createApplicationFolder()
        PreferenceUtils.getInstance(this)
        startKoin {
            androidLogger()
            androidContext(this@AppApplication)
            modules(getModule())
        }
        checkInternetConnection()
    }


    private fun getModule(): Iterable<Module> {
        return listOf(
            appModule,
            viewModelModule,
            repoModule,
            commonModelModule,
            dbModule
        )
    }
    private fun checkInternetConnection() {
        registerReceiver(mNetworkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }
}