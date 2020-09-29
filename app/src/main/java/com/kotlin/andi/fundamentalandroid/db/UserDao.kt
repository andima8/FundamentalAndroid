package com.kotlin.andi.fundamentalandroid.db

import android.database.Cursor
import android.widget.CursorAdapter
import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(userModel: UserDBModel)

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun readAllUser(): LiveData<List<UserDBModel>>

    //for content provider
    @Query("SELECT * FROM user_table")
    fun cursorReadAllUser(): Cursor

    @Query("SELECT * FROM user_table WHERE username =:userName")
    fun checkFavorite(userName: String):  LiveData<List<UserDBModel>>

    @Delete
    suspend fun deleteUser(userModel: UserDBModel)


}