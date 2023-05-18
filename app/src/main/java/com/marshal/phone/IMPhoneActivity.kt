package com.marshal.phone

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.marshal.IMPath.PHONE_PAGE
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

        mediaPlayer?.setOnCompletionListener {
            Log.d("TAG", "player 播放完成")
            it.reset()
            it.release()
        }

        mediaPlayer?.setOnErrorListener { mp, what, extra ->
            Log.d("TAG", "player 播放错误")

            false
        }

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
        conn = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                Log.d("TAG", "service -- onServiceConnected")
                val binder: RecorderService.RecorderBinder =
                    service as RecorderService.RecorderBinder
                recorderService = binder.getService()
                getRecorderFileLocal()
                try {
                    mediaPlayer?.setDataSource(recorderFilePath?.absolutePath)
                } catch (e: Exception) {
                    Log.e("TAG", "onServiceConnected play:${e.message}")
                }
                binding?.tvShowRecorderFile?.text = "文件:${recorderFilePath?.name}"
                Log.d("TAG", "activity path:${recorderFilePath?.name}")
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

    private fun getRecorderFileLocal() {
        val folder = File(FileUtils.getRecordFilePath())
        if (folder.exists()) {
            val files: Array<File> = folder.listFiles() as Array<File>
            val filesList = files.filter { it.length() > 0 } as ArrayList
            var fileShow: File? = null
            val size = filesList.size
            Log.d("TAG", "length:$size")
            for ((index, file) in filesList.withIndex()) {

                val nextIndex = if (index + 1 < size) {
                    index + 1
                } else {
                    index
                }

                if (index != nextIndex) {
                    fileShow = if (filesList[index].length() >= filesList[nextIndex].length()) {
                        file
                    } else {
                        filesList[nextIndex]
                    }
                }
            }

            Log.d("TAG", "最终显示的file:${fileShow?.absolutePath}")
            recorderFilePath = fileShow

            if (recorderFilePath?.absolutePath?.isNotEmpty() == true) {
                binding?.tvShowRecorderFile?.text = "文件:${recorderFilePath?.name}"
            }
        }
    }


    override fun onStop() {
        super.onStop()
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


}