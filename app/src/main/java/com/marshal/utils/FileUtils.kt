package com.marshal.utils

import android.util.Log
import com.marshal.MApplication
import java.io.File

object FileUtils {

    fun getRecordFilePath():String{
        val context = MApplication.getInstance().applicationContext
        val filePath = context.externalCacheDir
        val recordPath = "${filePath}/MarshalIM/recorder"
        val file = File(recordPath)
        if (!file.exists()) {
            file.mkdirs()
        }
        Log.d("TAG","file: ${file.absolutePath}")
        return file.absolutePath
    }

}