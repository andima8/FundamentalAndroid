package com.kotlin.andi.fundamentalandroid.db

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(userModel: UserDBModel)

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun readAllUser(): LiveData<List<UserDBModel>>

    @Query("SELECT * FROM user_table WHERE username =:userName")
    fun checkFavorite(userName: String):  LiveData<List<UserDBModel>>

    @Delete
    suspend fun deleteUser(userModel: UserDBModel)


}