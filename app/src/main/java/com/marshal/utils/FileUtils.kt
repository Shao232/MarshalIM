package com.marshal.utils

import android.util.Log
import com.marshal.MApplication
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date


object FileUtils {

    /**
     * 应用内部缓存文件夹
     */
    val app_cacheDir_path =  "${MApplication.getInstance().applicationContext.externalCacheDir}/MarshalIM/"

    fun copyAssetsResFile(assetName:String,  savepath:String,  savename:String):File{
        val dir = File(savepath)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        val dbf = File(savepath + savename)
        if (dbf.exists()) {
            dbf.delete()
        }
        val outFileName: String = savepath + savename
        val myOutput: OutputStream = FileOutputStream(outFileName)
        val myInput: InputStream = MApplication.getInstance().applicationContext.assets.open(assetName)
        val buffer = ByteArray(1024)
        var length: Int
        while (myInput.read(buffer).also { length = it } > 0) {
            myOutput.write(buffer, 0, length)
        }
        myOutput.flush()
        myInput.close()
        myOutput.close()

        if(!dbf.exists()) {
            Log.e("TAG","文件没有创建成功,请检查")
            return dbf
        }
        Log.d("TAG","文件大小:${dbf.length()}")
        Log.d("TAG","文件创建成功")
        return dbf
    }

    /**
     * 在应用cache中生成录音文件夹
     */
    fun getTempPath():String{
        val temp = "${app_cacheDir_path}recorder/temp"
        val file = File(temp)
        if (!file.exists()) {
            file.mkdirs()
        }
        return file.absolutePath
    }

    fun getRecordFilePath():String{
        val recordPath = "${app_cacheDir_path}recorder"
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
        val simpleFormat = SimpleDateFormat("yyyyMMddHHmmss")
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