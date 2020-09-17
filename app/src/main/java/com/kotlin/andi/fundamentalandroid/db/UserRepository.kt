package com.kotlin.andi.fundamentalandroid.db

import androidx.lifecycle.LiveData

class UserRepository(private  val userDao: UserDao){

    val readAllUser: LiveData<List<UserDBModel>> = userDao.readAllUser()
    fun checkUser(username: String):  LiveData<List<UserDBModel>> = userDao.checkFavorite(username)

    suspend fun addUser(userDBModel: UserDBModel){
        userDao.addUser(userDBModel)
    }

    suspend fun deleteUser(userDBModel: UserDBModel){
         userDao.deleteUser(userDBModel)
    }

}