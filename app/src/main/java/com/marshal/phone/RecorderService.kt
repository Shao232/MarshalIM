package com.marshal.phone

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat


class RecorderService : Service() {

    private var recorderBinder: RecorderBinder? = null


    //使用内部类访问外部类时，需要使用 inner 关键字来声明内部类，否则无法访问外部类的成员变量和方法
    inner class RecorderBinder : Binder() {
        fun getService(): RecorderService {
            return this@RecorderService
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("TAG", "service -- onCreate")


    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("TAG", "service -- onStartCommand")

        val notificationChannelId = "notification_channel_id_01"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            //用户可见的通道名称
            //用户可见的通道名称
            val channelName = "Foreground Service Notification"
            //通道的重要程度
            //通道的重要程度
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel =
                NotificationChannel(notificationChannelId, channelName, importance)
            notificationChannel.description = "Channel description"
            //LED灯
            //LED灯
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            //震动
            //震动
            notificationChannel.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
            notificationChannel.enableVibration(true)
            manager.createNotificationChannel(notificationChannel)
        }

        val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, notificationChannelId).setAutoCancel(true)
        notificationBuilder.setContentText("Text")
        notificationBuilder.setContentTitle("Title")

        notificationBuilder.setContentIntent(null)
        val notification = notificationBuilder.build()
        startForeground(222, notification)

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d("TAG", "service -- onBind")
        recorderBinder = RecorderBinder()
        return recorderBinder
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        Log.d("TAG", "service -- onRebind")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TAG", "service -- onDestroy")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            stopForeground(STOP_FOREGROUND_REMOVE)
        } else {
            stopForeground(true)
        }
    }

}