package com.example.maya

import android.media.MediaPlayer

class MyMediaPlayer {
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
}