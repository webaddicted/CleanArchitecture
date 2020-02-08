package com.webaddicted.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.webaddicted.database.utils.DbConstant

/**
 * Created by Deepak Sharma(webaddicted) on 15/01/20.
 */
@Entity(tableName = DbConstant.USER_INFO_TABLE)
class UserInfoEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var name: String? = null
    var nickname: String? = null
    var mobileno: String? = null
    var email: String? = null
    var password: String? = null
}