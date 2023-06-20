package com.marshal.chat.chatadapter

import com.marshal.base_common.baseadapter.BaseRecyclerViewHolder
import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.marshal.R

class ChatOppoSideTextViewHolder(context: Context?, resId:Int,
                                 parent: ViewGroup): BaseRecyclerViewHolder(context, resId, parent) {

    var oppoSideProfile:ImageView? = null
    var tvTextContent: TextView? = null

    init {
        with(itemView) {
            oppoSideProfile = findViewById(R.id.oppo_side_profile)
            tvTextContent = findViewById(R.id.tvTextContent)

        }
    }

}