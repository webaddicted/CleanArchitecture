package com.webaddicted.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.webaddicted.database.dao.UserInfoDao
import com.webaddicted.database.entity.UserInfoEntity
import com.webaddicted.database.utils.DbConstant
/**
 * Created by Deepak Sharma(webaddicted) on 15/01/20.
 */
@Database(entities = arrayOf(UserInfoEntity::class), version = DbConstant.DB_VERSION)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userInfoDao(): UserInfoDao
}