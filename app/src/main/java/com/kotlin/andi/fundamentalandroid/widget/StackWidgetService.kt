package com.kotlin.andi.fundamentalandroid.widget

import android.content.Intent
import android.widget.RemoteViewsService
import com.kotlin.andi.fundamentalandroid.widget.StackRemoteViewsFactory

class StackWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory =
        StackRemoteViewsFactory(this.applicationContext)
}
