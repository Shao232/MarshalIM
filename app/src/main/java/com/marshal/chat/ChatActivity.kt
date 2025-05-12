package com.marshal.chat

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.marshal.AppRouterPath.CHAT_PATH
import com.marshal.base_common.baseview.BaseViewActivity
import com.marshal.chat.chatadapter.ChatAdapter
import com.marshal.databinding.ActivityChatBinding

/**
 * 聊天页面
 */
@Route(path = CHAT_PATH)
class ChatActivity : BaseViewActivity<ActivityChatBinding>() {

    private var layoutManager: LinearLayoutManager? = null
    private var chatAdapter: ChatAdapter? = null



    override fun getResLayoutBinding(): View? {
        binding = ActivityChatBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding?.rvImList?.layoutManager = layoutManager
        chatAdapter = ChatAdapter()
        binding?.rvImList?.adapter = chatAdapter

    }


    override fun onDestroy() {
        super.onDestroy()
    }


}