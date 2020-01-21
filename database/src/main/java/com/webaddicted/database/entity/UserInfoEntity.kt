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
    public var id: Int = 0
   public var name: String? = null
   public  var nickname: String? = null
   public  var mobileno: String? = null
   public  var email: String? = null
   public  var password: String? = null
}