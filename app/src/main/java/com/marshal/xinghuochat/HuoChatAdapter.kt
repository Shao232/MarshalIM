package com.marshal.xinghuochat

import android.view.ViewGroup
import com.marshal.R
import com.marshal.base_common.baseadapter.BaseRecyclerAdapter
import com.marshal.base_common.baseadapter.BaseRecyclerViewHolder
import com.marshal.base_common.baseadapter.EmptyViewHolder
import com.marshal.chat.chatadapter.ChatAdapter
import com.marshal.chat.chatadapter.ChatOneSelfTextViewHolder
import com.marshal.chat.chatadapter.ChatOppoSideTextViewHolder
import com.marshal.pojo.ChatUser

class HuoChatAdapter:BaseRecyclerAdapter<BaseRecyclerViewHolder, ChatUser>() {

    override fun getItemViewType(position: Int): Int {
        val bean = itemList[position]
        return if(bean.userId == "1") {
            ChatAdapter.SHOW_ONE_SELF_TEXT
        }else {
            ChatAdapter.SHOW_OPPO_SIDE_TEXT
        }
    }

    override fun onViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder {
        return when(viewType) {
            ChatAdapter.SHOW_ONE_SELF_TEXT -> {
                //自己 文字消息
                ChatOneSelfTextViewHolder(R.layout.im_chat_recycler_send_text_item, parent)
            }
            ChatAdapter.SHOW_OPPO_SIDE_TEXT -> {
                //对方 文字消息
                ChatOppoSideTextViewHolder(R.layout.im_chat_recycler_receive_text_item, parent)
            }
            else -> EmptyViewHolder(parent = parent)
        }
    }

    override fun bindViewHolderData(holder: BaseRecyclerViewHolder, position: Int) {
        if (holder is ChatOneSelfTextViewHolder) {
            bindOneSelfHolderUI(holder, position)
        }

        if(holder is ChatOppoSideTextViewHolder) {
            bindOppoSideHolderUI(holder,position)
        }
    }

    private fun bindOneSelfHolderUI(holder: ChatOneSelfTextViewHolder, position: Int){
        val bean = itemList[position]
        holder.tvTextContentSend?.text = bean.messageText
        holder.oneSelfProfile?.setImageResource(R.drawable.img_default_profile)
    }

    private fun bindOppoSideHolderUI(holder: ChatOppoSideTextViewHolder, position: Int){
        val bean = itemList[position]
        holder.tvTextContentRecycler?.text = bean.messageText
        holder.oppoSideProfile?.setImageResource(R.drawable.img_default_profile)
    }

}