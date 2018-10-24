package com.example.paulo.boombass

import android.content.ComponentName
import android.content.Context
import android.media.session.MediaSessionManager
import android.service.notification.NotificationListenerService


class NotificationListener : NotificationListenerService() {

    override fun onCreate(){
    val mMediaSessionManager : MediaSessionManager =  getSystemService(Context.MEDIA_SESSION_SERVICE) as MediaSessionManager
        val componentName = ComponentName(this, this.javaClass)
        mMediaSessionManager.addOnActiveSessionsChangedListener(this, componentName)

    }
}