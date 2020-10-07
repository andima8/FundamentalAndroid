package com.kotlin.andi.consumerapp.data

import android.content.ContentResolver
import com.kotlin.andi.consumerapp.data.DatabaseContract.CONTENT_URI
import com.kotlin.andi.consumerapp.model.UserDBModel

class UserDataSource(private val contentResolver: ContentResolver) {

    fun getUsers(): List<UserDBModel> {
        val result: MutableList<UserDBModel> = mutableListOf()

        val cursor = contentResolver.query(
            CONTENT_URI,
            null,
            null,
            null
            , null
        )

        cursor?.apply {
            while (moveToNext()) {
                result.add(
                    UserDBModel(
                        getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns.ID)),
                        getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.AVATAR)),
                        getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.COMPANY)),
                        getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.FOLLOWER)),
                        getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.FOLLOWING)),
                        getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.LOCATION)),
                        getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.NAME)),
                        getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.REPOSITORY)),
                        getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.GISTS)),
                        getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.USERNAME))
                    )
                )
            }
            close()
        }
        return result.toList()
    }

}