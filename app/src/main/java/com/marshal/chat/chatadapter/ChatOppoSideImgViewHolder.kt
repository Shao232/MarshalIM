package com.marshal.chat.chatadapter

import android.view.ViewGroup
import android.widget.ImageView
import com.marshal.R
import com.marshal.base_common.baseadapter.BaseRecyclerViewHolder

class ChatOppoSideImgViewHolder(resId: Int, parent: ViewGroup): BaseRecyclerViewHolder(resId, parent) {

    var oppoSideProfile:ImageView? = null
    var oppoSideSendImg:ImageView? = null

    init {
        with(itemView) {
            oppoSideProfile = findViewById(R.id.oppo_side_profile)
            oppoSideSendImg = findViewById(R.id.oppo_side_show_img)
        }
    }

}