package com.kotlin.andi.fundamentalandroid.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.kotlin.andi.fundamentalandroid.db.UserDao
import com.kotlin.andi.fundamentalandroid.db.UserDatabase


class MyContentProvider : ContentProvider() {

    private lateinit var userDao: UserDao

    companion object {
        private const val USER = 1
        private const val TABLE_NAME = "user_table"
        private const val AUTHORITY = "com.kotlin.andi.fundamentalandroid"
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(
                AUTHORITY,
                TABLE_NAME,
                USER
            )
        }

    }


    override fun getType(uri: Uri): String? {
        throw UnsupportedOperationException()
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw UnsupportedOperationException()
    }

    override fun onCreate(): Boolean {
        userDao = UserDatabase.getDatabase(context as Context).userDao()
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when(uriMatcher.match(uri)) {
            USER -> userDao.cursorReadAllUser()
            else -> null
        }
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        throw UnsupportedOperationException()
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        throw UnsupportedOperationException()
    }

}
