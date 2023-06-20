package com.marshal.chat.chatadapter

import com.marshal.base_common.baseadapter.BaseRecyclerViewHolder
import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import com.marshal.R

class ChatOneSelfImgViewHolder(context: Context?, resId:Int,
                               parent: ViewGroup
): BaseRecyclerViewHolder(context, resId, parent) {

    var oneSelfProfile:ImageView? = null
    var oneSelfSendImg:ImageView? = null

    init {
        with(itemView) {
            oneSelfProfile = findViewById(R.id.one_self_profile)
            oneSelfSendImg = findViewById(R.id.sdvImg)


        }
    }

}