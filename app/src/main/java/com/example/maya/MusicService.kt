package com.example.maya

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder


class MusicService: Service() {
    private var myBinder=MyBinder()
   // private lateinit var mediaSession:MediaSessionCompat

//    private var context: Context? = null
//
//    override fun onCreate() {
//        super.onCreate()
//        context = applicationContext
//        // Use context.getContentResolver() for accessing resources
//    }
   // val songs=getSongs(this)
    companion object {
        private var instance: MediaPlayer? = null
        var pos = -1

        @Synchronized
        fun getInstance(): MediaPlayer {
            if (instance == null) {
                instance = MediaPlayer()
            }
            return instance!!
        }
    }
    override fun onBind(p0: Intent?): IBinder {
      //  mediaSession= MediaSessionCompat(baseContext,"My Music")
        return myBinder
    }
    inner class MyBinder:Binder(){
        fun currentServices(): MusicService {
            return this@MusicService
        }

    }
//    fun showNotificaton(){
//        val notification=NotificationCompat.Builder(this,ApplicationClass.CHANNEL_ID)
//            .setContentTitle(songs[pos].title)
//            .setContentText(songs[pos].title)
//            .setSmallIcon(R.drawable.playlist)
//            .setLargeIcon(BitmapFactory.decodeResource(resources,R.drawable.maya))
//            .setStyle(androidx.media.app.NotificationCompat.MediaStyle().setMediaSession(mediaSession.sessionToken))
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//            .setOnlyAlertOnce(true)
//            .addAction(R.drawable.previous,"Previosu",null)
//            .addAction(R.drawable.playbtn,"Play",null)
//            .addAction(R.drawable.baseline_skip_next_24,"Next",null)
//            .addAction(R.drawable.baseline_clear_24,"Exit",null)
//            .build()
//        startForeground(13,notification)
//    }
}