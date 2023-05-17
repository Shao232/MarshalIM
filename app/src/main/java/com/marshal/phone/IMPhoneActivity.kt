package com.marshal.phone

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.marshal.IMPath.PHONE_PAGE
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

    override fun getResLayoutBinding(): View? {
        binding = ActivityImphoneBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {

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
            }

        //createFile()

        val intent = Intent(this@IMPhoneActivity, RecorderService::class.java)
        startService(intent)

        binding?.btnCallPhone?.setOnClickListener {

            val phoneNumber = binding?.editPhoneNumber?.text.toString()
            if(PhoneUtils.checkPhone(phoneNumber)) {
                val phoneStr = "tel:$phoneNumber"
                val dialIntent = Intent(Intent.ACTION_CALL)
                dialIntent.data = Uri.parse(phoneStr)
                startActivity(dialIntent)
            }
        }

    }

 /*   private fun createFile() {
        val path = createRecordFile()
        val file = File(path)
        if(file.exists()) {
            Log.d("TAG","文件创建成功")
        }
        Log.d("TAG","$path")
    }

    private fun createRecordFile():String {
        //val filePath = Environment.getExternalStorageDirectory().absolutePath
        val filePath = context?.externalCacheDir
        val recordPath = "${filePath}/MarshalIM/recorder"
        val file = File(recordPath)
        if (!file.exists()) {
            //mkdirs可以创建多级目录
            file.mkdirs()
        }

        Log.d("TAG","$file")
        return recordPath + "/${getRecordTime()}.3gp"
    }

    private fun getRecordTime(): String {
        val simpleFormat = SimpleDateFormat("yyyy-MM-dd-HHmmss")
        val date = Date()
        return simpleFormat.format(date)
    }*/

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
        }

    }


}