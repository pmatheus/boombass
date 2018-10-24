package com.example.paulo.boombass

import android.content.ComponentName
import android.content.Context
import android.media.session.MediaSessionManager
import android.service.notification.NotificationListenerService
import android.content.Intent
import android.media.session.MediaController
import android.media.session.MediaController.Callback
import java.nio.file.Files.size
import android.media.session.PlaybackState
import android.media.MediaMetadata






class NotificationListener : NotificationListenerService() {
    private val componentName : ComponentName = ComponentName("com.example.paulo.boombass","com.example.paulo.boombass.NotificationListener");
    private var mediaController: MediaController? = null
    private var mediaSessionManager: MediaSessionManager? = null
    private var meta: MediaMetadata? = null

    private fun pickController(controllers: List<MediaController>): MediaController? {
        for (i in controllers.indices) {
            val mc = controllers[i]
            if (mc != null && mc.playbackState != null &&
                mc.playbackState!!.state == PlaybackState.STATE_PLAYING
            ) {
                return mc
            }
        }
        return if (controllers.size > 0) controllers[0] else null
    }

    private val callback : Callback =
        object : MediaController.Callback() {
            override fun onSessionDestroyed() {
                super.onSessionDestroyed();
                mediaController = null;
                meta = null;
            }
        }

    var sessionListener: MediaSessionManager.OnActiveSessionsChangedListener =
        object : MediaSessionManager.OnActiveSessionsChangedListener {
            override fun onActiveSessionsChanged(controllers: List<MediaController>) {
                mediaController = pickController(controllers)
                if (mediaController == null) return
                mediaController!!.registerCallback(callback)
//                meta = mediaController!!.getMetadata()
//                updateMetadata()
//                sendBroadcast(Intent(StandardWidget.WIDGET_UPDATE))
            }
        }

    override fun onCreate(){
        mediaSessionManager =  getSystemService(Context.MEDIA_SESSION_SERVICE) as MediaSessionManager
        mediaSessionManager!!.addOnActiveSessionsChangedListener(sessionListener, componentName)
    }


}