package com.marshal.chat.chatadapter

import com.marshal.base_common.baseadapter.BaseRecyclerViewHolder
import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import com.marshal.R

class ChatOppoSideImgViewHolder(context: Context?, resId:Int,
                                parent: ViewGroup): BaseRecyclerViewHolder(context, resId, parent) {

    var oppoSideProfile:ImageView? = null
    var oppoSideSendImg:ImageView? = null

    init {
        with(itemView) {
            oppoSideProfile = findViewById(R.id.oppo_side_profile)
            oppoSideSendImg = findViewById(R.id.oppo_side_show_img)

        }
    }

}