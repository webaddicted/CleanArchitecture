package com.webaddicted.database.dao

import androidx.room.*
import com.webaddicted.database.entity.UserInfoEntity
/**
 * Created by Deepak Sharma(webaddicted) on 15/01/20.
 */
@Dao
public interface UserInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertUser(userInfo: UserInfoEntity)

    @Query("SELECT * FROM user_info")
    abstract fun getUserInfo(): List<UserInfoEntity>

    @Query("SELECT * FROM user_info WHERE  email >= :emailId")
    abstract fun getCouponsBySize(emailId: String): UserInfoEntity

    @Delete
    abstract fun deleteUser(userInfo: UserInfoEntity)

    @Query("DELETE FROM user_info")
    abstract fun cleatTable()
}