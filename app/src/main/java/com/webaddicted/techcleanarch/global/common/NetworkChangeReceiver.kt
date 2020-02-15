package com.webaddicted.techcleanarch.global.common

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.webaddicted.techcleanarch.global.misc.GlobalUtility
import com.webaddicted.techcleanarch.global.misc.isNetworkAvailable

/**
 * The type Network change receiver.
 * Instantiates a new Network change receiver.
 */
class NetworkChangeReceiver : BroadcastReceiver() {
    companion object {
        /**
         * The constant connectivityReceiverListener.
         */
        var connectivityReceiverListener: ConnectivityReceiverListener? = null
        fun isInternetAvailable(connectivityReceiverListener: ConnectivityReceiverListener?){
            this.connectivityReceiverListener = connectivityReceiverListener
        }

    }
    override fun onReceive(context: Context, intent: Intent) {
        if (context.isNetworkAvailable()) {
            if (connectivityReceiverListener != null)
                connectivityReceiverListener?.onNetworkConnectionChanged(true)
        } else {
            if (connectivityReceiverListener != null)
                connectivityReceiverListener?.onNetworkConnectionChanged(false)
        }
    }


    /**
     * The interface Connectivity receiver listener.
     */
    interface ConnectivityReceiverListener {
        /**
         * This method is invoked bu receiver when internet connection enables or disables.
         *
         * @param networkConnected network connectivity status.
         */
        fun onNetworkConnectionChanged(networkConnected: Boolean)
    }
}
