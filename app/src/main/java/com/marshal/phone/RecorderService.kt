package com.marshal.phone

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaRecorder
import android.os.Build
import android.os.IBinder
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.util.Log
import com.marshal.MApplication
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

class RecorderService : Service() {


    override fun onCreate() {
        super.onCreate()

        val telephoneManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        telephoneManager.listen(MyListener(), PhoneStateListener.LISTEN_CALL_STATE)

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {


        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


    class MyListener : PhoneStateListener() {

        private var recorder: MediaRecorder? = null

        override fun onCallStateChanged(state: Int, phoneNumber: String?) {
            super.onCallStateChanged(state, phoneNumber)
            when (state) {
                TelephonyManager.CALL_STATE_IDLE -> {
                    Log.d("TAG", "空闲状态")
                    with(recorder ?: return) {
                        stop()
                        release()
                        recorder = null
                    }
                }

                TelephonyManager.CALL_STATE_RINGING -> {
                    //有来电时
                    Log.d("TAG", "响铃状态")
                    if (recorder == null) {
                        initRecordStatus()
                    }
                }

                TelephonyManager.CALL_STATE_OFFHOOK -> {
                    Log.d("TAG", "接听状态")
                    if (recorder == null) {
                        initRecordStatus()
                    }
                    with(recorder ?: return) {
                        start()
                    }
                }

                else -> {
                    Log.d("TAG", "state :${state}")
                }

            }
        }

        private fun initRecordStatus(){
            if (recorder == null) {
                recorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    MediaRecorder(MApplication.getInstance().applicationContext)
                } else {
                    MediaRecorder()
                }

                with(recorder ?: return) {
                    setAudioSource(MediaRecorder.AudioSource.MIC)
                    //3gp
                    setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                    setOutputFile(createRecordFile())
                    setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                    try {
                        prepare()
                    } catch (e: IOException) {
                        Log.e("TAG", "报错:${e.message}")
                    }
                }
            }
        }

        private fun createRecordFile():String {
            val context = MApplication.getInstance().applicationContext
            val filePath = context.externalCacheDir
            val recordPath = "${filePath}/MarshalIM/recorder"
            val file = File(recordPath)
            if (!file.exists()) {
                file.mkdirs()
            }
            Log.d("TAG",recordPath + "/${getRecordTime()}.3gp")
            return recordPath + "/${getRecordTime()}.3gp"
        }

        private fun getRecordTime(): String {
            val simpleFormat = SimpleDateFormat("yyyy-MM-dd-HHmmss")
            val date = Date()
            return simpleFormat.format(date)
        }


    }

}