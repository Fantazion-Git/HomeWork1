package com.example.homework1

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class PlayerBroadcastReceiver : BroadcastReceiver() {
    companion object {
        const val PREVIOUS = "Previous"
        const val PLAY_PAUSE = "Play pause"
        const val NEXT = "Next"
        const val STOP = "Stop"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            PREVIOUS -> MediaPlayerHelper.playPrevMusic()
            PLAY_PAUSE -> MediaPlayerHelper.playOrPauseMusic()
            NEXT -> MediaPlayerHelper.playNextMusic()
            STOP -> MediaPlayerHelper.stopMusic()
            else -> Unit
        }
    }

}