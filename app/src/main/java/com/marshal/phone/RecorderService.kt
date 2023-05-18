package com.marshal.phone

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.MediaRecorder
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.marshal.MApplication
import com.marshal.utils.FileUtils
import java.text.SimpleDateFormat
import java.util.Date


class RecorderService : Service() {

    private var recorderBinder: RecorderBinder? = null
    private var telephoneManager: TelephonyManager? = null
    private var recorder: MediaRecorder? = null
    private var outPutFilePath = ""

    private var recorderThreadRun: RecorderThread? = null

    fun getOutPutFilePath() = outPutFilePath

    //使用内部类访问外部类时，需要使用 inner 关键字来声明内部类，否则无法访问外部类的成员变量和方法
    inner class RecorderBinder : Binder() {
        fun getService(): RecorderService {
            return this@RecorderService
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("TAG", "service -- onCreate")

        telephoneManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val filePath = createRecordFile()
        recorderThreadRun = RecorderThread(filePath)
        telephoneManager?.listen(
            MyListener(recorderThreadRun!!), PhoneStateListener.LISTEN_CALL_STATE
        )

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

    /*  private fun initRecordStatus() {
          if (recorder == null) {
              recorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                  MediaRecorder(MApplication.getInstance().applicationContext)
              } else {
                  MediaRecorder()
              }

              with(recorder ?: return) {
                  try {
                      setAudioSource(MediaRecorder.AudioSource.MIC)
                      //3gp
                      setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                      setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                      outPutFilePath = createRecordFile()
                      setOutputFile(outPutFilePath)
                      prepare()
                  } catch (e: IOException) {
                      Log.e("TAG", "报错:${e.message}")
                  }
              }
          }
      }*/

    private fun createRecordFile(): String {
        val recordPath = FileUtils.getRecordFilePath()
        return recordPath + "/${getRecordTime()}.m4a"
    }

    private fun getRecordTime(): String {
        val simpleFormat = SimpleDateFormat("yyyyMMdd-HHmmss")
        val date = Date()
        return simpleFormat.format(date)
    }


    class MyListener(recorderThread: RecorderThread) : PhoneStateListener() {

        private var runnable: RecorderThread? = recorderThread
        private var thread: Thread? = null

        //计数器
        private var recorderStart: Int = 0

        init {
            thread = Thread(runnable)
        }


        override fun onCallStateChanged(state: Int, phoneNumber: String?) {
            super.onCallStateChanged(state, phoneNumber)
            when (state) {
                TelephonyManager.CALL_STATE_IDLE -> {
                    Log.d("TAG", "空闲状态 开始录音的计数器:${recorderStart}")
                    if (recorderStart > 0) {
                        runnable?.stopRecording()
                        recorderStart = 0
                    }
                }

                TelephonyManager.CALL_STATE_RINGING -> {
                    //有来电时
                    recorderStart++
                    Log.d("TAG", "响铃状态 录音计数器:${recorderStart}")
                    if (recorderStart == 1) {
                        try {
                            thread?.start()

                        } catch (e: Exception) {
                            Log.e("TAG", "CALL_STATE_RINGING msg:${e.message}")
                        }
                    }
                }

                TelephonyManager.CALL_STATE_OFFHOOK -> {
                    recorderStart++
                    Log.d("TAG", "接听状态 录音计数器:${recorderStart}")
                    if (recorderStart == 1) {
                        try {
                            thread?.start()
                        } catch (e: Exception) {
                            Log.e("TAG", "CALL_STATE_OFFHOOK msg:${e.message}")
                        }
                    }
                }

                else -> {
                    Log.d("TAG", "state :${state}")
                }
            }
        }
    }

    class RecorderThread(recordFile: String) : Runnable {
        private var recorder: MediaRecorder? = null
        private var outPutFilePath = recordFile

        init {
            recorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                MediaRecorder(MApplication.getInstance().applicationContext)
            } else {
                MediaRecorder()
            }
        }

        override fun run() {
            synchronized(this){
                initRecordStatus()
            }
        }

        private fun initRecordStatus() {
            if (recorder != null) {
                with(recorder ?: return) {
                    try {
                        setAudioSource(MediaRecorder.AudioSource.MIC)
                        //3gp
                        setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                        setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                        setOutputFile(outPutFilePath)
                        prepare()
                        start()
                    } catch (e: Exception) {
                        Log.e("TAG", "运行 报错:${e.printStackTrace()}")
                    }
                }
            }
        }

        fun stopRecording() {
            try {
                with(recorder ?: return) {
                    stop()
                    reset()
                    release()
                    recorder = null
                }
            } catch (e: Exception) {
                Log.e("TAG", "CALL_STATE_IDLE msg:${e.message}")
            }
        }
    }
}