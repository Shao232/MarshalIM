package com.marshal.chat.chatadapter

import android.view.ViewGroup
import android.widget.ImageView
import com.marshal.R
import com.marshal.base_common.baseadapter.BaseRecyclerViewHolder
import com.marshal.widget.EmojiTextView

class ChatOneSelfTextViewHolder(resId: Int, parent: ViewGroup): BaseRecyclerViewHolder(resId, parent) {

    var oneSelfProfile:ImageView? = null
    var tvTextContentSend: EmojiTextView? = null

    init {
        with(itemView) {
            oneSelfProfile = findViewById(R.id.one_self_profile)
            tvTextContentSend = findViewById(R.id.tvTextContent_send)
        }
    }

}