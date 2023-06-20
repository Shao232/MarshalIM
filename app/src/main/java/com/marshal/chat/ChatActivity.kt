package com.marshal.chat

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.marshal.IMPath.CHAT_PATH
import com.marshal.R
import com.marshal.base_common.baseview.BaseViewActivity
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
            R.drawable.img_default_profile,"你好 tom","https://seopic.699pic.com/photo/50051/5435.jpg_wh1200.jpg",
            2,"2"))

        chatAdapter?.itemList?.add(ChatUser("",
            R.drawable.img_default_profile,"你好 bill","",1,"1"))

        chatAdapter?.itemList?.add(ChatUser("",
            R.drawable.img_default_profile,"你好 seven","",1,"2"))

        chatAdapter?.itemList?.add(ChatUser("",
            R.drawable.img_default_profile,"你好 norris","https://file03.16sucai.com/2017/1100/16sucai_P591F3A055.JPG",2,"1"))


        binding?.rvImList?.adapter = chatAdapter

        chatAdapter?.notifyDataSetChanged()


    }



}