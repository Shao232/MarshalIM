package com.marshal

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.marshal.IMPath.CHAT_PATH
import com.marshal.baseview.BaseViewActivity
import com.marshal.databinding.ActivityChatBinding

@Route(path = CHAT_PATH)
class ChatActivity : BaseViewActivity<ActivityChatBinding>() {

    override fun getResLayoutBinding(): View? {
        binding = ActivityChatBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {



    }



}