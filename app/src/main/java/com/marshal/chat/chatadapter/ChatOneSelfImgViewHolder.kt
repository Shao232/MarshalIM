package com.marshal.chat.chatadapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.marshal.R
import com.marshal.baseadapter.BaseRecyclerViewHolder

class ChatOneSelfImgViewHolder(context: Context?, resId:Int,
                               parent: ViewGroup
):BaseRecyclerViewHolder(context, resId, parent) {

    var oneSelfProfile:ImageView? = null
    var oneSelfSendImg:ImageView? = null

    init {
        with(itemView) {
            oneSelfProfile = findViewById(R.id.one_self_profile)
            oneSelfSendImg = findViewById(R.id.sdvImg)


        }
    }

}