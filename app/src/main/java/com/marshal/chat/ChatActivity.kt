package com.marshal.chat

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.marshal.IMPath.CHAT_PATH
import com.marshal.R
import com.marshal.baseview.BaseViewActivity
import com.marshal.chat.chatadapter.ChatAdapter
import com.marshal.databinding.ActivityChatBinding
import com.marshal.pojo.ChatUser

@Route(path = CHAT_PATH)
class ChatActivity : BaseViewActivity<ActivityChatBinding>() {

    private var layoutManager:LinearLayoutManager? = null
    private var chatAdapter: ChatAdapter? = null

    override fun getResLayoutBinding(): View? {
        binding = ActivityChatBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {

        layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding?.rvImList?.layoutManager = layoutManager
        chatAdapter = ChatAdapter()

        chatAdapter?.itemList?.add(ChatUser("",
            R.drawable.img_default_profile,"你好 marshal","",1,"1"))

        chatAdapter?.itemList?.add(ChatUser("",
            R.drawable.img_default_profile,"你好 tom","https://gw.alicdn.com/i2/63986519/O1CN01AH1zKP1y1kkNYZE61_!!63986519.jpg_300x300Q75.jpg_.webp",
            2,"2"))

        chatAdapter?.itemList?.add(ChatUser("",
            R.drawable.img_default_profile,"你好 bill","",1,"1"))

        chatAdapter?.itemList?.add(ChatUser("",
            R.drawable.img_default_profile,"你好 seven","",1,"2"))

        chatAdapter?.itemList?.add(ChatUser("",
            R.drawable.img_default_profile,"你好 norris","https://gw.alicdn.com/i2/63986519/O1CN01AH1zKP1y1kkNYZE61_!!63986519.jpg_300x300Q75.jpg_.webp",2,"1"))


        binding?.rvImList?.adapter = chatAdapter

        chatAdapter?.notifyDataSetChanged()


    }



}