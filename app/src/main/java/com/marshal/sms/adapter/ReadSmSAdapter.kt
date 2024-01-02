package com.marshal.sms.adapter

import AppUtils
import android.view.ViewGroup
import com.marshal.base_common.baseadapter.BaseRecyclerAdapter
import com.marshal.base_common.utils.ToastUtil
import com.marshal.pojo.ReadSmSBean

class ReadSmSAdapter : BaseRecyclerAdapter<ReadSmsViewHolder, ReadSmSBean>() {
    override fun onViewHolder(parent: ViewGroup, viewType: Int): ReadSmsViewHolder {
        return ReadSmsViewHolder(parent)
    }

    override fun bindViewHolderData(holder: ReadSmsViewHolder, position: Int) {
        val bean = itemList[position]
        holder.binding?.tvReadSmsTitle?.text = "号码: ${bean.phoneNumber}"
        holder.binding?.tvReadSmsContent?.text = bean.smsContent
        holder.itemView.setOnLongClickListener {
            val content =  holder.binding?.tvReadSmsContent?.text.toString()
            val successCopy = mContext?.let { it1 -> AppUtils.copyContent(it1,content) }
            if(successCopy == true) {
                ToastUtil.showToast(mContext,"复制成功")
            }

            true
        }
    }
}