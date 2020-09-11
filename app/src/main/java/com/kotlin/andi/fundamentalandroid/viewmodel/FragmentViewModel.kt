package com.kotlin.andi.fundamentalandroid.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotlin.andi.fundamentalandroid.model.User
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class FragmentViewModel: ViewModel() {

    val listUsers = MutableLiveData<ArrayList<User>>()

    fun setFollowers(username: String) {
        val listUser = ArrayList<User>()
        val client =  AsyncHttpClient()
        client.addHeader("Authorization","token b260b208c163274d887182f921b43c13450dd61f")
        client.addHeader("User-Agent", "request")
        val url = "https://api.github.com/users/${username}/followers"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                val result = responseBody?.let { String(it) }
                try {
                    val jsonArray = JSONArray(result)
                    Log.d("Hasil", "${result}")
                    for(i in 0 until jsonArray.length()){
                        val user = jsonArray.getJSONObject(i)
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

    fun getUsers(): LiveData<ArrayList<User>> {
        return listUsers
    }

    fun setFollowing(username: String) {
        val listUser = ArrayList<User>()
        val client =  AsyncHttpClient()
        client.addHeader("Authorization","token b260b208c163274d887182f921b43c13450dd61f")
        client.addHeader("User-Agent", "request")
        val url = "https://api.github.com/users/${username}/following"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                val result = responseBody?.let { String(it) }
                try {
                    val jsonArray = JSONArray(result)
                    Log.d("Hasil", "${result}")
                    for(i in 0 until jsonArray.length()){
                        val user = jsonArray.getJSONObject(i)
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
}