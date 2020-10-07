package com.kotlin.andi.fundamentalandroid.widget

import android.content.Context
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.kotlin.andi.fundamentalandroid.R
import com.kotlin.andi.fundamentalandroid.model.UserDBModel
import com.kotlin.andi.fundamentalandroid.db.UserDao
import com.kotlin.andi.fundamentalandroid.db.UserDatabase


internal class StackRemoteViewsFactory(private val mContext: Context) :
    RemoteViewsService.RemoteViewsFactory {

    private lateinit var uData: List<UserDBModel>
    private lateinit var userDao: UserDao

    override fun onCreate() {
        userDao = UserDatabase.getDatabase(mContext).userDao()
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(position: Int): Long = 0

    override fun onDataSetChanged() {
        val identityToken = Binder.clearCallingIdentity()
        uData = userDao.getUserWidget()
        Binder.restoreCallingIdentity(identityToken)
    }

    override fun hasStableIds(): Boolean = false

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName,
            R.layout.widget_item
        )
        rv.setTextViewText(R.id.profile_name, uData[position].username)
        return rv
    }

    override fun getCount(): Int = uData.size

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {

    }

}