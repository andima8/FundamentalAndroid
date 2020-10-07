package com.kotlin.andi.fundamentalandroid.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotlin.andi.fundamentalandroid.BuildConfig
import com.kotlin.andi.fundamentalandroid.model.User
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MainViewModel: ViewModel() {

    val listUsers = MutableLiveData<ArrayList<User>>()
    val listUser = ArrayList<User>()

    fun setUsers(username: String?){
        val client =  AsyncHttpClient()
        client.addHeader("Authorization","token " + BuildConfig.GITHUB_TOKEN)
        client.addHeader("User-Agent", "request")
        val url = "https://api.github.com/search/users?q=${username}"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
              try {
                  listUser.clear()
                  val result = String(responseBody)
                  val responseObject = JSONObject(result)
                  val list = responseObject.getJSONArray("items")
                  Log.d("Result", result)
                  for(i in 0 until list.length()){
                      val user = list.getJSONObject(i)
                      val userItems = User()
                      userItems.id = user.getInt("id")
                      userItems.avatar = user.getString("avatar_url")
                      userItems.username = user.getString("login")
                      listUser.add(userItems)
                  }
                  //set data ke adapter
                  Log.d("Total", "${listUser.size}")
                  listUsers.postValue(listUser)
              }  catch (e: Exception){

                  Log.d("Exception", e.message.toString())
              }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("Exception", error.message.toString())
            }

        })

    }
    fun getUsers(): LiveData<ArrayList<User>>{
        return listUsers
    }

}