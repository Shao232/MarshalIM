package com.marshal.chat.chatadapter

import android.view.ViewGroup
import android.widget.ImageView
import com.marshal.R
import com.marshal.base_common.baseadapter.BaseRecyclerViewHolder

class ChatOneSelfImgViewHolder(resId: Int, parent: ViewGroup): BaseRecyclerViewHolder(resId, parent) {

    var oneSelfProfile:ImageView? = null
    var oneSelfSendImg:ImageView? = null

    init {
        with(itemView) {
            oneSelfProfile = findViewById(R.id.one_self_profile)
            oneSelfSendImg = findViewById(R.id.sdvImg)
        }
    }
}