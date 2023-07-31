package com.marshal.chat.chatadapter

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.marshal.R
import com.marshal.base_common.baseadapter.BaseRecyclerViewHolder

class ChatOppoSideTextViewHolder(context: Context?, resId:Int,
                                 parent: ViewGroup): BaseRecyclerViewHolder( resId, parent) {

    var oppoSideProfile:ImageView? = null
    var tvTextContent: TextView? = null

    init {
        with(itemView) {
            oppoSideProfile = findViewById(R.id.oppo_side_profile)
            tvTextContent = findViewById(R.id.tvTextContent)

        }
    }

}