package com.marshal.chat

import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.hyphenate.EMMessageListener
import com.hyphenate.EMValueCallBack
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMConversation
import com.hyphenate.chat.EMCursorResult
import com.hyphenate.chat.EMMessage
import com.marshal.AppRouterPath.CHAT_PATH
import com.marshal.base_common.baseview.BaseViewActivity
import com.marshal.base_common.store.getAppAppLoginUserAccount
import com.marshal.chat.chatadapter.ChatAdapter
import com.marshal.databinding.ActivityChatBinding

@Route(path = CHAT_PATH)
class ChatActivity : BaseViewActivity<ActivityChatBinding>() {

    private var layoutManager: LinearLayoutManager? = null
    private var chatAdapter: ChatAdapter? = null
    val currentUser = getAppAppLoginUserAccount()
    val sendObject = if (currentUser == "marshal") "bill" else "marshal"

    private val msgListener = EMMessageListener {
        Log.d("TAG", "size:${it.size}")
        it.forEach {
            Log.d("TAG", "EMMessageListener message:${it.msgId},${it.body}")
        }
    }

    override fun getResLayoutBinding(): View? {
        binding = ActivityChatBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding?.rvImList?.layoutManager = layoutManager
        chatAdapter = ChatAdapter()
        binding?.rvImList?.adapter = chatAdapter

        binding?.wrapperInputBox?.editMessage?.setOnEditorActionListener { v, actionId, event ->

            if (actionId == EditorInfo.IME_ACTION_SEND) {
                val editMessage = v.text?.toString()?.trim() ?: ""
                initHyChatAccount(editMessage)
                true
            }
            false
        }

        EMClient.getInstance().chatManager().addMessageListener(msgListener)

        var conversationId = ""
//        EMClient.getInstance().chatManager().asyncFetchConversationsFromServer(20,
//            null,
//            object : EMValueCallBack<EMCursorResult<EMConversation>> {
//
//                override fun onSuccess(value: EMCursorResult<EMConversation>?) {
//                    Log.d("TAG", "value.data:${value?.data}")
//                    conversationId = value?.data?.first()?.conversationId() ?: ""
//
//                }
//
//                override fun onError(error: Int, errorMsg: String?) {
//                    Log.e("TAG", "asyncFetchConversationsFromServer error:${error},msg:${errorMsg}")
//                }
//
//            })
        val conversation = EMClient.getInstance().chatManager().getConversation(sendObject)
        conversationId = conversation.conversationId()
        Log.d("TAG", "conversationId :${conversationId}")

        val conversationObject = EMClient.getInstance().chatManager()
            .getConversation(conversationId, EMConversation.EMConversationType.Chat)
        if (conversationObject != null) {
            Log.d("TAG", "conversationObject:${conversationObject.conversationId()}")

            EMClient.getInstance().chatManager()
                .asyncFetchHistoryMessage(conversationObject.conversationId(),
                    conversationObject.type,
                    50,
                    "",
                    object : EMValueCallBack<EMCursorResult<EMMessage>> {
                        override fun onSuccess(value: EMCursorResult<EMMessage>?) {
                            Log.d("TAG", "asyncFetchHistoryMessage value:${value?.data}")
                            //[msg{from:bill, to:marshal body:txt:"ffgg", msg{from:bill, to:marshal body:txt:"ffgg", msg{from:bill, to:marshal body:txt:"hello", msg{from:bill, to:marshal body:txt:"hello world"]
                            value?.data?.forEach {

                                Log.d("TAG","item:${it.body}")
                            }

                        }

                        override fun onError(error: Int, errorMsg: String?) {
                            Log.e("TAG", "asyncFetchHistoryMessage error:${error},msg:${errorMsg}")
                        }

                    })
        }


    }

    private fun initHyChatAccount(editMessage: String) {
        if (editMessage.isEmpty()) {
            showToast("发送消息不要为空")
            return
        }


        val message = EMMessage.createTextSendMessage(editMessage, sendObject)
        message.chatType = EMMessage.ChatType.Chat
        EMClient.getInstance().chatManager().sendMessage(message)
    }

    override fun onDestroy() {
        EMClient.getInstance().chatManager().removeMessageListener(msgListener)
        super.onDestroy()
    }


}