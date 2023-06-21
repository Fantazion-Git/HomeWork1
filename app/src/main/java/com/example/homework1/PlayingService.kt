package com.example.homework1

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat


class PlayingService : Service() {
    companion object {
        private const val CHANNEL_ID = "MyChannelId1"
        private const val CHANNEL_NAME = "Player"
        private const val FOREGROUND_NOTIFICATION_ID = 1

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createPlayNotificationChannel()
        val notificationLayout = createNotificationLayout()
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setCustomContentView(notificationLayout)
            .setOngoing(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()
        startForeground(FOREGROUND_NOTIFICATION_ID, notification)
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createNotificationLayout(): RemoteViews {
        val intent = Intent(this, PlayerBroadcastReceiver::class.java)
        val notificationLayout = RemoteViews(packageName, R.layout.noification)
        notificationLayout.setOnClickListener(
            R.id.previous,
            intent.setAction(PlayerBroadcastReceiver.PREVIOUS)
        )
        notificationLayout.setOnClickListener(
            R.id.play,
            intent.setAction(PlayerBroadcastReceiver.PLAY_PAUSE)
        )
        notificationLayout.setOnClickListener(
            R.id.next,
            intent.setAction(PlayerBroadcastReceiver.NEXT)
        )
        notificationLayout.setOnClickListener(
            R.id.stop,
            intent.setAction(PlayerBroadcastReceiver.STOP)
        )

        return notificationLayout
    }

    private fun RemoteViews.setOnClickListener(id: Int, intent: Intent) {
        setOnClickPendingIntent(
            id, PendingIntent.getBroadcast(
                this@PlayingService,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    private fun createPlayNotificationChannel() {
        val chan = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME, NotificationManager.IMPORTANCE_NONE
        )
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
    }

    override fun onDestroy() {
        MediaPlayerHelper.stopMusic()
        super.onDestroy()
    }
}