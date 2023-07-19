package com.marshal.chat.chatadapter

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.marshal.R
import com.marshal.base_common.baseadapter.BaseRecyclerViewHolder

class ChatOneSelfTextViewHolder(context: Context?, resId:Int,
                                parent: ViewGroup): BaseRecyclerViewHolder(context, resId, parent) {

    var oneSelfProfile:ImageView? = null
    var tvTextContent:TextView? = null

    init {
        with(itemView) {
            oneSelfProfile = findViewById(R.id.one_self_profile)
            tvTextContent = findViewById(R.id.tvTextContent)
        }
    }

}