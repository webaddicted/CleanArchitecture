package com.webaddicted.data.common

import com.google.gson.annotations.SerializedName


/**
 * Model class for Notification Data
 */
class NotificationData {

    @SerializedName("id")
    var id: Long = 0
    @SerializedName("type")
    var type: String = ""
}

