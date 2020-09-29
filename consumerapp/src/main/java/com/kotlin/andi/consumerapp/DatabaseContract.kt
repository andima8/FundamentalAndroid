package com.kotlin.andi.consumerapp

import android.net.Uri
import android.provider.BaseColumns
import com.kotlin.andi.consumerapp.DatabaseContract.UserColumns.Companion.TABLE_NAME

object DatabaseContract {
    private const val AUTHORITY = "com.kotlin.andi.fundamentalandroid"
    private val SCHEME = "content"

    class UserColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "user_table"
            const val ID = "id"
            const val AVATAR = "avatar"
            const val COMPANY = "company"
            const val FOLLOWER = "follower"
            const val FOLLOWING = "following"
            const val LOCATION = "location"
            const val NAME = "name"
            const val REPOSITORY = "repository"
            const val GISTS = "gists"
            const val USERNAME = "username"
        }
    }

    val CONTENT_URI : Uri = Uri.Builder().scheme(SCHEME)
        .authority(AUTHORITY)
        .appendPath(TABLE_NAME)
        .build()
}