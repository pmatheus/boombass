package com.example.paulo.boombass

import android.content.Context
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.graphics.Color.parseColor
import android.util.Log
import com.bullhead.equalizer.EqualizerFragment
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.browse.MediaBrowser
import android.media.session.MediaSessionManager
import android.content.ComponentName
import android.content.Intent
import android.provider.Settings
import android.provider.Settings.Secure






class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        dumpAudioInfo()

        val sessionId = 1089
//        var mMediaPlayer = MediaPlayer.create(this, R.raw.sound_file_1)
//        val sessionId = mMediaPlayer.audioSessionId
//        mMediaPlayer.start()
//        notificationAccessPermission()



//        var media_sessions =  mMediaSessionManager.javaClass.getActiveSessions(this.componentName)

        val equalizerFragment = EqualizerFragment.newBuilder()
            .setAccentColor(parseColor("#4caf50"))
            .setAudioSessionId(sessionId)
            .build()
        supportFragmentManager.beginTransaction()
            .replace(R.id.eqFrame, equalizerFragment)
            .commit()
    }

    fun dumpAudioInfo(){
        try{
            val cmd = "dumpsys audio"
            var script = Runtime.getRuntime().exec(cmd)
            var input = BufferedReader( InputStreamReader(script.inputStream))

            var line = "a"

            while(line !=  null){
                line = input.readLine()
                Log.i("############", line)
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun notificationAccessPermission() {
        if (Settings.Secure.getString(this.contentResolver, "enabled_notification_listeners").contains(
                applicationContext.packageName
            )
        ) {
            //service is enabled do something
        } else {
            val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
            startActivity(intent)
        }
    }
}
