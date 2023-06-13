package com.marshal.utils

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build


/**
 * 与应用相关的参数类
 * 版本号
 * 版本名
 *
 */
object AppUtils {

    /**
     * 获取自己应用内部的版本号
     * versionCode 定义的数字
     */
    fun getVersionCode(context: Context): Int {
        val manager = context.packageManager
        var code = -1
        var longCode = -1L
        try {
            val info = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                manager.getPackageInfo(context.packageName, PackageManager.PackageInfoFlags.of(0))
            } else {
                manager.getPackageInfo(context.packageName, 0)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                longCode = info.longVersionCode
            }else {
                code = info.versionCode
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return if(longCode != -1L) longCode.toInt() else code
    }

    /**
     * 获取自己应用内部的版本名
     * versionName 定义的展示版本号
     */
    fun getVersionName(context: Context): String? {
        val manager = context.packageManager
        var name: String? = null
        try {
            val info = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                manager.getPackageInfo(context.packageName, PackageManager.PackageInfoFlags.of(0))
            } else {
                manager.getPackageInfo(context.packageName, 0)
            }
            name = info.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return name
    }

}