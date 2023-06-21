package com.marshal.mine

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.marshal.AppRouterPath.MINE_TO_BLUE_TOOTH
import com.marshal.base_common.baseadapter.AdapterItemOnClickListener
import com.marshal.base_common.baseview.BaseViewActivity
import com.marshal.databinding.ActivityBlueToothConnectBinding
import com.marshal.mine.adapter.BlueToothCustomAdapter
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.UUID


@Route(path = MINE_TO_BLUE_TOOTH)
class BlueToothConnectActivity : BaseViewActivity<ActivityBlueToothConnectBinding>() {

    private var blueToothAdapter: BluetoothAdapter? = null

    private var adapter: BlueToothCustomAdapter? = null

    @RequiresApi(Build.VERSION_CODES.S)
    private var requestPermissionsArr: Array<String> = arrayOf(
        Manifest.permission.BLUETOOTH_SCAN,
        Manifest.permission.BLUETOOTH_ADVERTISE,
        Manifest.permission.BLUETOOTH_CONNECT,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    // Unique UUID for this application
    private val MY_UUID_FIRST = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    private val MY_UUID_SECOND = UUID.fromString("0be27c05-cf42-4367-85ff-00f05f566536")


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

        if(blueToothAdapter!=null && blueToothAdapter?.isEnabled == true) {
            val scanner = blueToothAdapter?.bluetoothLeScanner
            if(scanner !=null ) {
                Log.d("TAG", "此设备支持ble")
            }else{
                Log.d("TAG", "此设备不支持ble")
            }
        }

        adapter?.setAdapterItemOnClickListener(object :
            AdapterItemOnClickListener<BluetoothDevice> {
            override fun onClick(view: View, bean: BluetoothDevice) {
                super.onClick(view, bean)
                Log.d("TAG", "连接蓝牙")
                if(blueToothAdapter?.isEnabled == false) {

                    return
                }

                Thread {
                    var blueServerSocket: BluetoothSocket? = null
                    var blueSocket: BluetoothSocket ? = null

                    var tmpIn: InputStream? = null
                    var tmpOut: OutputStream? = null

                    try {

                        if (ActivityCompat.checkSelfPermission(
                                context?:return@Thread,
                                Manifest.permission.BLUETOOTH_CONNECT
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                        }

                        Log.d("TAG","连接的设备 ${bean.name}")
                        blueServerSocket = bean.createRfcommSocketToServiceRecord(MY_UUID_SECOND)

                        try {
                            // This is a blocking call and will only return on a
                            // successful connection or an exception
                            blueServerSocket.connect()
                        } catch (e: IOException) {
                            Log.e("TAG","error: connect() failed  ${e.message}")

                            blueServerSocket.close()
                        }

                        if(blueServerSocket.isConnected) {
                            tmpIn = blueServerSocket?.inputStream
                            tmpOut = blueServerSocket?.outputStream

                            val outString = "hello,i'm outputStream"
                            val outByteData:ByteArray = outString.toByteArray()
                            tmpOut?.write(outByteData)

                            val buffer = ByteArray(1024)
                            var bytes: Int = 0

                            try {
                                // Read from the InputStream
                                bytes = tmpIn?.read(buffer) ?: 0
                                if(bytes != 0) {
                                    val data =  String(buffer)
                                    Log.d("TAG","输出: ${data}")
                                }

                                // Send the obtained bytes to the UI Activity

                            } catch (e: IOException) {
                                Log.e("TAG","读取失败")
                            }
                        }else {
                            Log.e("TAG","connect失败 ")
                        }




                        /*if (ActivityCompat.checkSelfPermission(
                                context?:return@Thread,
                                Manifest.permission.BLUETOOTH_CONNECT
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                        }
                        bluetoothSocket = bean.createRfcommSocketToServiceRecord(UUID.
                        fromString("00001101-0000-1000-8000-00805F9B34FB"))

                        if(bluetoothSocket !=null && !bluetoothSocket.isConnected) {
                            Log.d("TAG","name ${mainLooper.thread.name}")
                            Log.d("TAG","bluetoothSocket ${bluetoothSocket.isConnected}")

                            bluetoothSocket.connect()
                        }

                        if(bluetoothSocket !=null && bluetoothSocket.isConnected) {
                            Log.d("TAG","连接成功!!")
                        }*/

                    }catch (ex:IOException) {
                        Log.d("TAG","连接超时，失败!!")

                        ex.printStackTrace()
                    }finally {
                       // bluetoothSocket?.close()
                    }


                }.start()

            }

        })


    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun enableBlueTooth() {
        if (blueToothAdapter?.isEnabled == false) {

            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.BLUETOOTH_CONNECT
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    Log.d("TAG", "请求蓝牙")

                    ActivityCompat.requestPermissions(this, requestPermissionsArr, 12)
                }
            }else {
                //请求用户开启

                startActivityForResult(enableBtIntent, 22)
            }


        }

        getBlueToothDevices()

    }

    private fun getBlueToothDevices() {
        if (blueToothAdapter != null) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
            }
            val setDevices = blueToothAdapter?.bondedDevices
            if (setDevices?.isNotEmpty() == true) {
                for (item in setDevices) {

                    adapter?.itemList?.add(item)

                }
            }
            adapter?.notifyDataSetChanged()
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
            Log.d("TAG", "permissions: ${permissions.forEach {Log.d("TAG",it) }}")
            if (dataResults.isNotEmpty()) {
                Log.d("TAG", "蓝牙权限请求成功!!!!!")
            } else {
                Log.d("TAG", "dataResults is empty ")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 22) {
            if(resultCode == Activity.RESULT_OK) {
                Log.d("TAG","请求成功 ")
                getBlueToothDevices()
            }else {
                Log.d("TAG","请求失败 ")
                showToast("请打开蓝牙")
            }
        }
    }

}