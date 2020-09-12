package com.kotlin.andi.fundamentalandroid.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addUser(userModel: UserDBModel)

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun readAllUser(): LiveData<List<UserDBModel>>

}