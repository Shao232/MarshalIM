package com.marshal.mine

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.marshal.IMPath.MINE_TO_BLUE_TOOTH
import com.marshal.baseadapter.AdapterItemOnClickListener
import com.marshal.baseview.BaseViewActivity
import com.marshal.databinding.ActivityBlueToothConnectBinding
import com.marshal.mine.adapter.BlueToothCustomAdapter
import java.io.IOException
import java.util.UUID


@Route(path = MINE_TO_BLUE_TOOTH)
class BlueToothConnectActivity : BaseViewActivity<ActivityBlueToothConnectBinding>() {

    private var blueToothAdapter: BluetoothAdapter? = null

    private var adapter: BlueToothCustomAdapter? = null

    @RequiresApi(Build.VERSION_CODES.S)
    private var requestPermissionsArr: Array<String> = arrayOf(
        Manifest.permission.BLUETOOTH_SCAN,
        Manifest.permission.BLUETOOTH_ADVERTISE,
        Manifest.permission.BLUETOOTH_CONNECT
    )


    override fun getResLayoutBinding(): View? {
        binding = ActivityBlueToothConnectBinding.inflate(layoutInflater)
        return binding?.root
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun initView() {

        //已经弃用直接从BlueAdapter获取getDefaultAdapter,可以使用通过蓝牙管理者获取
        val bluetoothManager = baseContext.getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
        blueToothAdapter = bluetoothManager.adapter

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding?.recyclerList?.layoutManager = layoutManager

        adapter = BlueToothCustomAdapter()

        binding?.recyclerList?.adapter = adapter

        if (blueToothAdapter == null) {
            showToast("这台设备不支持蓝牙")
        } else {
            enableBlueTooth()
        }



        adapter?.setAdapterItemOnClickListener(object : AdapterItemOnClickListener<BluetoothDevice> {
            override fun onClick(view: View, bean: BluetoothDevice) {
                super.onClick(view, bean)
                Log.d("TAG", "连接蓝牙")
                Thread {
                    var bluetoothSocket:BluetoothSocket? = null
                    try {

                        if (ActivityCompat.checkSelfPermission(
                                context?:return@Thread,
                                Manifest.permission.BLUETOOTH_CONNECT
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                        }
                        bluetoothSocket = bean.createRfcommSocketToServiceRecord(UUID.
                        fromString("00001101-0000-1000-8000-00805F9B34FB"))

                        if(bluetoothSocket !=null && !bluetoothSocket.isConnected) {
                            bluetoothSocket.connect()
                        }
                    }catch (ex:IOException) {
                        ex.printStackTrace()
                    }finally {
                        bluetoothSocket?.close()
                    }


                }.start()

            }

        })


    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun enableBlueTooth() {
        if (blueToothAdapter?.isEnabled == false) {

            //请求用户开启

            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH_SCAN
                ) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH_ADVERTISE
                ) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Log.d("TAG", "请求蓝牙")

                ActivityCompat.requestPermissions(this, requestPermissionsArr, 12)
            }


        } else {
            if (blueToothAdapter != null) {
                val setDevices = blueToothAdapter?.bondedDevices
                if (setDevices?.isNotEmpty() == true) {
                    for (item in setDevices) {

                        adapter?.itemList?.add(item)

                    }
                }

                adapter?.notifyDataSetChanged()


            }

        }

    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 12) {
            val dataResults = grantResults.filter { it == PackageManager.PERMISSION_GRANTED }
            Log.d("TAG", "permissions: ${permissions}")
            if (dataResults.isNotEmpty()) {
                Log.d("TAG", "蓝牙权限请求成功!!!!!")


            } else {
                showToast("蓝牙没有开启!!，请打开蓝牙")

            }

        }
    }

}