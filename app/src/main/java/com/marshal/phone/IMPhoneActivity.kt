package com.marshal.phone

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.marshal.IMPath.PHONE_PAGE
import com.marshal.MApplication
import com.marshal.baseview.BaseViewActivity
import com.marshal.databinding.ActivityImphoneBinding
import com.marshal.utils.FileUtils
import com.marshal.utils.PhoneUtils
import java.io.File


@Route(path = PHONE_PAGE)
class IMPhoneActivity : BaseViewActivity<ActivityImphoneBinding>() {

    private var permissionArray: Array<String> = arrayOf(
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CALL_PHONE
    )

    private var toRecorderServiceIntent: Intent? = null
    private var conn: ServiceConnection? = null
    private var recorderService: RecorderService? = null
    private var recorderFilePath: File? = null

    private var mediaPlayer: MediaPlayer? = null

    private var telephoneManager: TelephonyManager? = null
    private var audioManager:AudioManager? = null

    override fun getResLayoutBinding(): View? {
        binding = ActivityImphoneBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {
        Log.d("TAG", "initView")
        mediaPlayer = MediaPlayer()

        binding?.btnCallPhone?.setOnClickListener {
            val phoneNumber = binding?.editPhoneNumber?.text.toString()
            if (PhoneUtils.checkPhone(phoneNumber)) {
                val phoneStr = "tel:$phoneNumber"
                val dialIntent = Intent(Intent.ACTION_CALL)
                dialIntent.data = Uri.parse(phoneStr)
                startActivity(dialIntent)
            }
        }

        binding?.btnRecorderPlay?.setOnClickListener {
            if (recorderFilePath?.absolutePath?.isNotEmpty() == true) {
                try {
                    Log.d("TAG","播放前展示数据:${recorderFilePath?.absolutePath}")
                    mediaPlayer = MediaPlayer.create(this,Uri.fromFile(recorderFilePath))
                    mediaPlayer?.start()
                } catch (e: IllegalStateException) {
                    Log.e("TAG", "点击播放录音  error:${e.printStackTrace()}")
                }

            } else {
                showToast("请先录音")
            }
        }

        binding?.btnRecorderClean?.setOnClickListener {
            val folder = File(FileUtils.getRecordFilePath())
            FileUtils.clearFolder(folder)
        }

        mediaPlayer?.setOnCompletionListener {
            Log.d("TAG", "player 播放完成")
            it.reset()
            it.release()
            mediaPlayer = null
        }

        mediaPlayer?.setOnErrorListener { mp, what, extra ->
            Log.d("TAG", "player 播放错误")

            false
        }

        telephoneManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager




        getRecorderFileLocal()

    }

    override fun onResume() {
        super.onResume()
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                this, Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("TAG", "请求打电话的权限")

            ActivityCompat.requestPermissions(this, permissionArray, 12)
        } else {
            startRecorderService()
        }
    }

    private fun startRecorderService() {
        val filePath = FileUtils.createRecordFile()
        telephoneManager?.listen(MyListener(filePath), PhoneStateListener.LISTEN_CALL_STATE)

        conn = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                Log.d("TAG", "service -- onServiceConnected")
                val binder: RecorderService.RecorderBinder =
                    service as RecorderService.RecorderBinder
                recorderService = binder.getService()
                getRecorderFileLocal()
                binding?.tvShowRecorderFile?.text = "文件:${recorderFilePath?.name}"
            }

            override fun onServiceDisconnected(name: ComponentName?) {

                recorderService = null
            }
        }

        toRecorderServiceIntent =
            Intent().setClass(this@IMPhoneActivity, RecorderService::class.java)
        bindService(toRecorderServiceIntent, conn as ServiceConnection, Context.BIND_AUTO_CREATE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(toRecorderServiceIntent)
        } else {
            startService(toRecorderServiceIntent);
        }
    }

    private fun stopRecorderService() {
        conn?.let { unbindService(it) }
        stopService(toRecorderServiceIntent)
    }

    /**
     * 获取录音文件
     */
    private fun getRecorderFileLocal() {
        val folder = File(FileUtils.getRecordFilePath())
        if (folder.exists()) {
            val files: Array<File> = folder.listFiles() as Array<File>
            if(files.isNullOrEmpty()) {
                return
            }

            val filesList = files.filter { it.length() > 0 } as ArrayList
            var fileShow: File? = filesList[0]
            val size = filesList.size
            for ((index, file) in filesList.withIndex()) {
                val nextIndex = if (index + 1 < size) index + 1 else index

                if (index != nextIndex) {
                    fileShow = if (filesList[index].length() >= filesList[nextIndex].length()) {
                        file
                    } else {
                        filesList[nextIndex]
                    }
                }
            }

            recorderFilePath = fileShow
            if (recorderFilePath?.absolutePath?.isNotEmpty() == true) {
                binding?.tvShowRecorderFile?.text = "文件:${recorderFilePath?.name}"
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopRecorderService()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 12) {
            val dataResults = grantResults.filter { it == PackageManager.PERMISSION_GRANTED }
            Log.d("TAG", "permissions: ${permissions.forEach { Log.d("TAG", it) }}")
            if (dataResults.isNotEmpty()) {
                Log.d("TAG", "权限请求成功!!!!!")
            } else {
                Log.d("TAG", "dataResults is empty ")
            }
            startRecorderService()
        }

    }

    private inner class MyListener(recordFile: String) : PhoneStateListener() {

        private var recorder: MediaRecorder? = null
        private var outPutFilePath = recordFile
        private var recorderRunning:Boolean = false
        //计数器
        private var recorderStart: Int = 0

        init {
            recorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                MediaRecorder(MApplication.getInstance().applicationContext)
            } else {
                MediaRecorder()
            }
        }

        override fun onCallStateChanged(state: Int, phoneNumber: String?) {
            super.onCallStateChanged(state, phoneNumber)
            when (state) {
                TelephonyManager.CALL_STATE_IDLE -> {
                    Log.d("TAG", "空闲状态 开始录音的计数器:${recorderStart}")
                    if (recorderStart > 0) {
                        recorderStart = 0
                        stopRecording()
                    }
                }

                TelephonyManager.CALL_STATE_RINGING -> {
                    //有来电时
                    Log.d("TAG", "响铃状态 录音计数器:${recorderStart}")
                }

                TelephonyManager.CALL_STATE_OFFHOOK -> {
                    recorderStart++
                    Log.d("TAG", "接听状态 录音计数器:${recorderStart}")
                    if (recorderStart == 1) {
                        try {
                            if(!recorderRunning) {
                                Log.d("TAG","接听状态  准备录音:${recorderRunning}")
                                initRecordStatus()
                            }

//                            if (runnable?.getRecorderRunning() == false) {
//                                Log.d("TAG", "接听状态 线程中是正在录音:${runnable?.getRecorderRunning()}")
//                                thread?.start()
//                            }
                        } catch (e: Exception) {
                            Log.e("TAG", "CALL_STATE_OFFHOOK error:${e.printStackTrace()}")
                        }
                    }
                }

                else -> {
                    Log.d("TAG", "state :${state}")
                }
            }
        }

        private fun initRecordStatus() {
            if (recorder != null && !recorderRunning) {
                // 将音频模式设置为通信模式，关闭扬声器
                audioManager?.mode = AudioManager.MODE_IN_CALL
                audioManager?.isSpeakerphoneOn = false

                with(recorder ?: return) {
                    try {
                        Log.d("TAG","running 开始准备录音 ~~~")
                        recorderRunning = true
                        setAudioSource(MediaRecorder.AudioSource.MIC)
                        //3gp
                        setOutputFormat(MediaRecorder.OutputFormat.AMR_NB)
                        setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                        //比特率为16Kbps，采样率为8kHz
                        setAudioEncodingBitRate(16 * 1000)
                        setAudioSamplingRate(8000)
                        setOutputFile(outPutFilePath)
                        prepare()
                        start()
                        Log.d("TAG","start 开始录音 ~~~")
                    } catch (e: Exception) {
                        Log.e("TAG", "运行 报错:${e.printStackTrace()}")
                    }
                }
            }
        }

        private fun stopRecording() {
            try {
                recorderRunning = false
                // 重置音频模式，打开扬声器
                audioManager?.mode = AudioManager.MODE_NORMAL
                audioManager?.isSpeakerphoneOn = true
                with(recorder ?: return) {
                    stop()
                    release()
                    recorder = null
                }
            } catch (e: Exception) {
                Log.e("TAG", "CALL_STATE_IDLE msg:${e.message}")
            }
        }
    }



}