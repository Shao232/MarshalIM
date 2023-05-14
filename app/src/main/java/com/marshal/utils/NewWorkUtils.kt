package com.marshal.utils

import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.Collections


object NewWorkUtils {

    fun getLocalIpAddress(): String? {
        try {
            // 获取所有网络接口
            val interfaces: List<NetworkInterface> =
                Collections.list(NetworkInterface.getNetworkInterfaces())

            // 遍历所有网络接口
            for (intf in interfaces) {
                // 获取该接口的所有IP地址
                val addrs: List<InetAddress> = Collections.list(intf.inetAddresses)
                for (addr in addrs) {
                    // 判断是否为IPv4地址和非回路地址
                    if (!addr.isLoopbackAddress && addr is Inet4Address) {
                        return addr.getHostAddress()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

}