package com.marshal.mine

import NewWorkUtils
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.marshal.AppRouterPath.MINE_TO_CONNECT_UDP
import com.marshal.base_common.baseview.BaseViewActivity
import com.marshal.databinding.ActivityUdpconnectctivityBinding
import java.lang.ref.WeakReference
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress


@Route(path = MINE_TO_CONNECT_UDP)
class UDPConnectActivity : BaseViewActivity<ActivityUdpconnectctivityBinding>() {

    //192.168.5.229
    //ip地址： 192.168.5.68

    private var ipAddress:String? = ""

    private val miIPAddress ="192.168.5.68"
    private val huaWeiIPAddress ="192.168.5.229"

    private var port:Int = 8888

    private var handler:MyHandler? = null

    private var socketSend:DatagramSocket? = null
    private var socketReceive:DatagramSocket? = null

    override fun getResLayoutBinding(): View? {
        binding = ActivityUdpconnectctivityBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {

        ipAddress = NewWorkUtils.getLocalIpAddress()
        Log.d("TAG","ip地址： $ipAddress")

        handler = MyHandler(Looper.getMainLooper(),this)

        var index = 0

        binding?.btnSend?.setOnClickListener {
            val sendMessage = binding?.editSendData?.text?.toString()
            index ++
            binding?.tvSendData?.text = "发送者: 第${index}发送数据:${sendMessage}"
            createSend(sendMessage?:"")
        }

        createReceive()

    }

    override fun onDestroy() {
        super.onDestroy()
        if(socketSend!= null && socketSend?.isClosed == false) {
            socketSend?.close()
            socketSend = null
        }

        if(socketReceive != null && socketReceive?.isClosed == false) {
            socketReceive?.close()
            socketReceive = null
        }
    }

    private fun createSend(messageString:String) {
        Thread {
            try {
                if (socketSend == null || socketSend?.isClosed == true) {
                    socketSend = DatagramSocket()
                }

                val address = InetAddress.getByName(if(ipAddress == miIPAddress) huaWeiIPAddress else miIPAddress)
                val data: ByteArray = messageString.toByteArray()
                val packet = DatagramPacket(data, data.size, address, port)
                socketSend?.send(packet)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }

    private fun createReceive() {
        Thread{
            Looper.prepare()

            val buffer = ByteArray(1024)
            if (socketReceive == null || socketReceive?.isClosed == true) {
                socketReceive = DatagramSocket(port)
            }

            val packet = DatagramPacket(buffer, buffer.size)

            while (true) {
                if(socketReceive?.isConnected == true) {
                    socketReceive?.receive(packet)
                    val message = String(packet.data, 0, packet.length)
                    // 处理接收到的消息
                    Log.d("TAG","receive: $message")
                    val handlerMessage = handler?.obtainMessage()
                    val data = Bundle()
                    data.putString("receiveMsg",message)
                    handlerMessage?.data = data
                    handler?.sendMessage(handlerMessage?:return@Thread)
                }

            }

            Looper.loop()

        }.start()
    }

    private class MyHandler(looper: Looper,activity: UDPConnectActivity): Handler(looper){

        var weakReference:WeakReference<UDPConnectActivity>? = WeakReference<UDPConnectActivity>(activity)
        var activity:UDPConnectActivity? = weakReference?.get()

        var index = 0

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            val d = msg.data
            val receiveData = d.getString("receiveMsg")
            index ++

            activity?.binding?.tvReceiveData?.text = "接收者: 第${index}次接收的数据: $receiveData"
        }

    }


}