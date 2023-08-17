package com.marshal.chat.chatadapter

import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.marshal.R
import com.marshal.base_common.baseadapter.BaseRecyclerAdapter
import com.marshal.base_common.baseadapter.BaseRecyclerViewHolder
import com.marshal.base_common.baseadapter.EmptyViewHolder
import com.marshal.pojo.ChatUser

class ChatAdapter : BaseRecyclerAdapter<BaseRecyclerViewHolder, ChatUser>() {

    companion object {
        const val MESSAGE_TYPE_TEXT = 1
        const val MESSAGE_TYPE_IMAGE = 2

        //展示ui的viewtype分类
        const val SHOW_ONE_SELF_TEXT = 1
        const val SHOW_ONE_SELF_IMG = 2
        const val SHOW_OPPO_SIDE_TEXT = 3
        const val SHOW_OPPO_SIDE_IMG = 4
    }

    override fun getItemViewType(position: Int): Int {
        val bean = itemList[position]
        return if(bean.userId == "1") {
           when(bean.messageType) {
               MESSAGE_TYPE_TEXT -> SHOW_ONE_SELF_TEXT
               MESSAGE_TYPE_IMAGE -> SHOW_ONE_SELF_IMG
               else -> 0
           }
        }else {
            when(bean.messageType) {
                MESSAGE_TYPE_TEXT -> SHOW_OPPO_SIDE_TEXT
                MESSAGE_TYPE_IMAGE -> SHOW_OPPO_SIDE_IMG
                else -> 0
            }
        }
    }

    override fun onViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder {
        return when(viewType) {
            SHOW_ONE_SELF_TEXT -> {
                //自己 文字消息
                ChatOneSelfTextViewHolder(R.layout.im_chat_recycler_send_text_item, parent)
            }
            SHOW_ONE_SELF_IMG -> {
                //自己 图片消息
                ChatOneSelfImgViewHolder(R.layout.im_chat_recycler_send_img_item, parent)
            }
            SHOW_OPPO_SIDE_TEXT -> {
                //对方 文字消息
                ChatOppoSideTextViewHolder(R.layout.im_chat_recycler_receive_text_item, parent)
            }
            SHOW_OPPO_SIDE_IMG -> {
                //对方 图片数据
                ChatOppoSideImgViewHolder(R.layout.im_chat_recycler_receive_img_item, parent)
            }
            else -> EmptyViewHolder(parent = parent)
        }
    }

    override fun bindViewHolderData(holder: BaseRecyclerViewHolder, position: Int) {
        val bean = itemList[position]

        when (holder) {
            is ChatOneSelfTextViewHolder -> {
                holder.oneSelfProfile?.setImageResource(bean.userProfileRes)
                holder.tvTextContentSend?.text = bean.messageText
            }

            is ChatOneSelfImgViewHolder -> {
                holder.oneSelfProfile?.setImageResource(bean.userProfileRes)
                Glide.with(mContext?:return).asBitmap().load(bean.messageImg)
                    .into(holder.oneSelfSendImg?:return)

            }

            is ChatOppoSideTextViewHolder -> {
                holder.oppoSideProfile?.setImageResource(bean.userProfileRes)
                holder.tvTextContentRecycler?.text = bean.messageText

            }

            is ChatOppoSideImgViewHolder -> {
                holder.oppoSideProfile?.setImageResource(bean.userProfileRes)

                Glide.with(mContext?:return).asBitmap().load(bean.messageImg)
                    .into(holder.oppoSideSendImg?:return)
            }
        }
    }
}