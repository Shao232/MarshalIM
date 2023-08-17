package com.marshal.chat.chatadapter

import android.view.ViewGroup
import android.widget.ImageView
import com.marshal.R
import com.marshal.base_common.baseadapter.BaseRecyclerViewHolder
import com.marshal.widget.EmojiTextView

class ChatOppoSideTextViewHolder(resId: Int, parent: ViewGroup): BaseRecyclerViewHolder( resId, parent) {

    var oppoSideProfile:ImageView? = null
    var tvTextContentRecycler: EmojiTextView? = null

    init {
        with(itemView) {
            oppoSideProfile = findViewById(R.id.oppo_side_profile)
            tvTextContentRecycler = findViewById(R.id.tvTextContent_recycler)
        }
    }

}