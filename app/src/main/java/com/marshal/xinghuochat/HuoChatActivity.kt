package com.marshal.xinghuochat

import FileUtils
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.iflytek.aikit.core.AIChatHandle
import com.iflytek.aikit.core.AiHelper
import com.iflytek.aikit.core.BaseLibrary
import com.iflytek.aikit.core.ChatListener
import com.iflytek.aikit.core.ChatParam
import com.iflytek.aikit.core.CoreListener
import com.iflytek.aikit.core.ErrType
import com.iflytek.aikit.core.LogLvl
import com.marshal.AppRouterPath.APP_AI_CHAT
import com.marshal.base_common.baseview.BaseViewActivity
import com.marshal.databinding.ActivityXingHuoChatBinding
import com.marshal.pojo.ChatUser
import kotlin.random.Random


@Route(path = APP_AI_CHAT)
class HuoChatActivity : BaseViewActivity<ActivityXingHuoChatBinding>() {

    private val appid = "1a4f6115"
    private val apiSecret = "MTIzMzQyMzAyZTNlMGQzZWEwZmUxZDdl"
    private val apiKey = "7cb616c3179747cd6d66f48a40ab6618"

    private var coreListener: CoreListener? = null
    private var chatListener: ChatListener? = null

    // 设定flag，在输出未完成时无法进行发送
    private var sessionFinished = true
    private var aiChatOutput: StringBuffer = StringBuffer()
    private var adapter: HuoChatAdapter? = null
    private var linearLayoutManager: LinearLayoutManager? = null

    override fun hasToolbar(): Boolean = true

    override fun getResLayoutBinding(): View? {
        binding = ActivityXingHuoChatBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {

        if(hasIncludeToolbar){
            setTitle("讯飞星火")
        }

        initAISDK()

        adapter = HuoChatAdapter()
        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding?.recyclerChatLayout?.layoutManager = linearLayoutManager

        binding?.recyclerChatLayout?.adapter = adapter

        adapter?.itemList?.add(
            ChatUser(
                messageText = "你好，我是讯飞星火，很高兴为您服务",
                userId = "0"
            )
        )

        binding?.recyclerChatLayout?.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

            }

        })

        binding?.btnSendChat?.setOnClickListener {
            if (sessionFinished) {
                val inputChatContent = binding?.editSendTextChat?.text.toString().trim()
                if (inputChatContent.isEmpty()) {
                    showToast("请输入内容再发送!")
                    return@setOnClickListener
                }
                startChat(inputChatContent)
                runOnUiThread {
                    binding?.editSendTextChat?.setText("")
                    if ((adapter?.itemCount ?: 0) > 1) {
                        binding?.recyclerChatLayout?.scrollToPosition((adapter?.itemCount ?: 0) - 1)
                    }

                }
            } else {
                showToast("AI还在回复中，请稍等再发")
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        unInitSDK()
    }

    private fun initAISDK() {

        // 初始化SDK
        //SDK初始化监听
        coreListener = CoreListener { type, code ->
            Log.i("TAG", "core listener code:$code")
            runOnUiThread {
                when (type) {
                    ErrType.AUTH -> {
                        Log.d("TAG", "SDK初始化成功：$code")
                    }

                    ErrType.HTTP -> {
                        Log.d("TAG", "SDK初始化失败：$code")
                    }

                    else -> {
                        Log.d("TAG", "SDK初始化失败：其他错误:$code")
                    }
                }
            }
        }

        AiHelper.getInst().registerListener(coreListener)
        // 注册chat回调
        // 注册chat回调
        chatListener = object : ChatListener {
            //成员变量

            override fun onChatOutput(
                handle: AIChatHandle,
                role: String,
                content: String,
                index: Int
            ) {
                Log.d("TAG", "role : $role, content : $content")

                aiChatOutput.append(content)

                sessionFinished = true

                if (handle.usrContext != null) {
                    val context = handle.usrContext as String
                    Log.d("TAG", "context:$context")
                }
            }

            override fun onChatError(handle: AIChatHandle, err: Int, errDesc: String) {
                Log.e("TAG", "errCode:" + err + "errDesc:" + errDesc)
                sessionFinished = true
            }

            override fun onChatToken(
                handle: AIChatHandle,
                completionTokens: Int,
                promptTokens: Int,
                totalTokens: Int
            ) {
                sessionFinished = true

                binding?.recyclerChatLayout?.postDelayed({
                    Log.e(
                        "TAG",
                        "completionTokens:" + completionTokens + " , promptTokens:" + promptTokens + " , " +
                                "totalTokens:" + totalTokens
                    )

                    val randomId = Random.nextInt(2, Int.MAX_VALUE)
                    val randomIDValue = randomId.toString()
                    val chatBean = ChatUser(userId = randomIDValue)
                    chatBean.messageText = aiChatOutput.toString()
                    adapter?.itemList?.add(chatBean)
                    adapter?.notifyDataSetChanged()
                    aiChatOutput.setLength(0)

                    if ((adapter?.itemCount ?: 0) > 1) {
                        binding?.recyclerChatLayout?.scrollToPosition((adapter?.itemCount ?: 0) - 1)
                    }

                }, 300)
            }
        }
        AiHelper.getInst().registerChatListener(chatListener)

        val logFile = FileUtils.getXingHuoLogFile()
        // 初始化SDK
        AiHelper.getInst().setLogInfo(LogLvl.VERBOSE, 1, logFile)
        val params: BaseLibrary.Params.Builder = BaseLibrary.Params.builder()
            .appId(appid)
            .apiKey(apiKey)
            .apiSecret(apiSecret)
            .workDir(FileUtils.getXingHuoDirs())

        Thread {
            val context = applicationContext
            AiHelper.getInst().init(context, params.build())
        }.start()

    }

    private fun startChat(inputChatContent: String) {
        val usrInputText: String = inputChatContent

        adapter?.itemList?.add(ChatUser(messageText = usrInputText, userId = "1"))
        adapter?.notifyDataSetChanged()

        // 配置参数
        val chatParam = ChatParam.builder()
        chatParam.domain("general")
            .auditing("default")
            .uid("uid")
            .maxToken(1024)
        val myContext = "myContext"
        val ret = AiHelper.getInst().asyncChat(chatParam, usrInputText, myContext)
        if (ret != 0) {
            Log.e("TAG", "AIKIT_Chat failed:\n$ret")
            return
        }
        sessionFinished = false
        return
    }

    private fun unInitSDK() {
        AiHelper.getInst().unInit()
    }


}