package com.marshal.mine.adapter

import android.Manifest
import android.bluetooth.BluetoothDevice
import android.content.pm.PackageManager
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.marshal.R
import com.marshal.baseadapter.BaseRecyclerAdapter

class BlueToothCustomAdapter:BaseRecyclerAdapter<BlueToothCustomViewHolder,BluetoothDevice>() {
    override fun onViewHolder(parent: ViewGroup, viewType: Int): BlueToothCustomViewHolder {
        return BlueToothCustomViewHolder(mContext, R.layout.item_string,parent)
    }

    override fun bindViewHolderData(holder: BlueToothCustomViewHolder, position: Int) {
        val device = itemList[position]
        if (ActivityCompat.checkSelfPermission(
                mContext?:return,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
        }

        holder.tvStr?.text = device.name ?:""
    }
}