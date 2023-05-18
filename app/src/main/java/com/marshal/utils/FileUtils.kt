package com.marshal.utils

import com.marshal.MApplication
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

object FileUtils {

    /**
     * 在应用cache中生成录音文件夹
     */
    fun getRecordFilePath():String{
        val context = MApplication.getInstance().applicationContext
        val filePath = context.externalCacheDir
        val recordPath = "${filePath}/MarshalIM/recorder"
        val file = File(recordPath)
        if (!file.exists()) {
            file.mkdirs()
        }
        return file.absolutePath
    }

    fun createRecordFile(): String {
        val recordPath = getRecordFilePath()
        return recordPath + "/${getRecordTime()}.m4a"
    }

    private fun getRecordTime(): String {
        val simpleFormat = SimpleDateFormat("yyyyMMdd-HHmmss")
        val date = Date()
        return simpleFormat.format(date)
    }

    /**
     * 清空文件夹-子文件，但是不会删除文件夹本身
     */
    fun clearFolder(folder: File) {
        val files = folder.listFiles()
        if (files != null) { // 如果目录下存在文件
            for (file in files) {
                if (file.isDirectory) {
                    clearFolder(file) // 递归删除子目录
                } else {
                    file.delete() // 删除子文件
                }
            }
        }
    }

}