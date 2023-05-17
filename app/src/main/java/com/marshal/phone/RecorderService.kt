package com.marshal.phone

import android.Manifest
import android.app.Activity
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Build
import android.os.IBinder
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.marshal.MApplication
import java.io.File
import java.io.IOException
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date

class RecorderService : Service() {

    private var telephoneManager:TelephonyManager? = null
    private var recorder: MediaRecorder? = null

    private var permissionArray:Array<String> = arrayOf(
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.RECORD_AUDIO,
    )

    override fun onCreate() {
        super.onCreate()
        Log.d("TAG","service -- onCreate")
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("TAG", "请求录音的权限")

            ActivityCompat.requestPermissions(MApplication.getInstance().applicationContext as Activity,
                permissionArray, 12)
        }

        telephoneManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        initRecordStatus()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("TAG","service -- onStartCommand")
        if(recorder !=null) {
            telephoneManager?.listen(MyListener(recorder!!), PhoneStateListener.LISTEN_CALL_STATE)
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d("TAG","service -- onBind")
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TAG","service -- onDestroy")
    }

    private fun initRecordStatus(){
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
                    setOutputFile(createRecordFile())
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
        Log.d("TAG",recordPath + "/${getRecordTime()}.m4a")
        return recordPath + "/${getRecordTime()}.m4a"
    }

    private fun getRecordTime(): String {
        val simpleFormat = SimpleDateFormat("yyyy-MM-dd-HHmmss")
        val date = Date()
        return simpleFormat.format(date)
    }


    class MyListener(recorder:MediaRecorder) : PhoneStateListener() {

        private var recorder:MediaRecorder? = recorder

        //计数器
        private var recorderStart:Int =0

        override fun onCallStateChanged(state: Int, phoneNumber: String?) {
            super.onCallStateChanged(state, phoneNumber)
            when (state) {
                TelephonyManager.CALL_STATE_IDLE -> {
                    Log.d("TAG", "空闲状态")
                    if(recorderStart > 0) {
                        with(recorder ?: return) {
                            try {
                                stop()
                                reset()
                                release()
                                recorder = null
                            }catch (e: Exception) {
                                Log.e("TAG","CALL_STATE_IDLE msg:${e.message}")
                            }

                        }
                        recorderStart = 0
                    }
                }

                TelephonyManager.CALL_STATE_RINGING -> {
                    //有来电时
                    Log.d("TAG", "响铃状态")
                    recorderStart++
                    if(recorderStart == 1) {
                        try {
                            recorder?.start()
                        }catch (e:Exception) {
                            Log.e("TAG","CALL_STATE_RINGING msg:${e.message}")
                        }
                    }
                }

                TelephonyManager.CALL_STATE_OFFHOOK -> {
                    Log.d("TAG", "接听状态")
                    recorderStart++
                    if(recorderStart == 1) {
                        try {
                            recorder?.start()
                        }catch (e:Exception) {
                            Log.e("TAG","CALL_STATE_OFFHOOK msg:${e.message}")
                        }
                    }
                }

                else -> {
                    Log.d("TAG", "state :${state}")
                }
            }
        }
    }

}