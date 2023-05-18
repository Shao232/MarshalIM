package com.marshal.phone

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.WorkerParameters
import com.alibaba.android.arouter.facade.annotation.Route
import com.marshal.IMPath.PHONE_PAGE
import com.marshal.MApplication
import com.marshal.baseview.BaseViewActivity
import com.marshal.databinding.ActivityImphoneBinding
import com.marshal.utils.PhoneUtils


@Route(path = PHONE_PAGE)
class IMPhoneActivity : BaseViewActivity<ActivityImphoneBinding>() {

    private var permissionArray:Array<String> = arrayOf(
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CALL_PHONE
    )

    private var toRecorderServiceIntent:Intent? = null
    private var conn: ServiceConnection? = null
    private var recorderService:RecorderService? = null


    override fun getResLayoutBinding(): View? {
        binding = ActivityImphoneBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {

        binding?.btnCallPhone?.setOnClickListener {
            val phoneNumber = binding?.editPhoneNumber?.text.toString()
            if(PhoneUtils.checkPhone(phoneNumber)) {
                val phoneStr = "tel:$phoneNumber"
                val dialIntent = Intent(Intent.ACTION_CALL)
                dialIntent.data = Uri.parse(phoneStr)
                startActivity(dialIntent)
            }
        }

        binding?.btnRecorderStart?.setOnClickListener {

        }

        binding?.btnRecorderEnd?.setOnClickListener {

        }




    }

    override fun onResume() {
        super.onResume()
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("TAG", "请求打电话的权限")

            ActivityCompat.requestPermissions(this, permissionArray, 12)
        }else {
            startRecorderService()
        }
    }

    private fun startRecorderService() {
        conn = object:ServiceConnection{
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                val binder:RecorderService.RecorderBinder = service as RecorderService.RecorderBinder
                recorderService = binder.getService()
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                recorderService = null
            }
        }

        toRecorderServiceIntent = Intent().setClass(this@IMPhoneActivity, RecorderService::class.java)
        bindService(toRecorderServiceIntent, conn as ServiceConnection,Context.BIND_AUTO_CREATE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(toRecorderServiceIntent)
        } else {
            startService(toRecorderServiceIntent);
        }
    }

    private fun stopRecorderService(){
        conn?.let { unbindService(it) }
        stopService(toRecorderServiceIntent)
    }

    override fun onStop() {
        super.onStop()
        stopRecorderService()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 12) {
            val dataResults = grantResults.filter { it == PackageManager.PERMISSION_GRANTED }
            Log.d("TAG", "permissions: ${permissions.forEach {Log.d("TAG",it) }}")
            if (dataResults.isNotEmpty()) {
                Log.d("TAG", "权限请求成功!!!!!")
            } else {
                Log.d("TAG", "dataResults is empty ")
            }
            startRecorderService()
        }

    }


}