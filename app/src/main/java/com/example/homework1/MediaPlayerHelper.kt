package com.example.homework1

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import java.lang.reflect.Field

object MediaPlayerHelper {
    private val mediaPlayer = MediaPlayer()
    private val musicList = createMusicList(R.raw::class.java.fields)
    private var currentMusicIndex = 0
    private var contextProvider: (() -> Context)? = null

    private fun createMusicList(fields: Array<out Field>): List<Int> {
        val musicList = mutableListOf<Int>()
        for (i in fields.indices) {
            try {
                musicList.add(fields[i].getInt(fields[i]))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return musicList
    }

    fun initialize(contextProvider: () -> Context) {
        val applicationContext = contextProvider().applicationContext
        this.contextProvider = { applicationContext }
        val playingServiceIntent = Intent(contextProvider(), PlayingService::class.java)
        applicationContext.startForegroundService(playingServiceIntent)
    }

    fun stopMusic() {
        mediaPlayer.reset()
        mediaPlayer.stop()
    }

    fun playOrPauseMusic() = when {
        mediaPlayer.isPlaying -> mediaPlayer.pause()
        mediaPlayer.currentPosition > 0 -> mediaPlayer.start()
        else -> playMusic(currentMusicIndex)
    }

    fun playNextMusic() {
        currentMusicIndex++
        if (currentMusicIndex >= musicList.size) currentMusicIndex = 0
        playMusic(currentMusicIndex)
    }

    fun playPrevMusic() {
        currentMusicIndex--
        if (currentMusicIndex < 0) currentMusicIndex = musicList.lastIndex
        playMusic(currentMusicIndex)
    }


    private fun playMusic(musicIndex: Int) {
        val resources = contextProvider?.invoke()?.resources ?: return
        val afd = resources.openRawResourceFd(musicList[musicIndex])
        mediaPlayer.reset()
        mediaPlayer.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
        mediaPlayer.prepare()
        mediaPlayer.start()
    }

}
