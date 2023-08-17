package com.marshal.pojo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChatUser(
    //@SerializedName("userProfile")
    var userProfileStr:String = "",//头像 网络资源
    var userProfileRes:Int = 0, //头像 本地资源
    var messageText:String = "", //消息 文字
    var messageImg:String = "", //消息 图片资源
    var messageType:Int = 0,//消息类型 1 文字 2 图片 3 语音
    var userId:String = ""// 用户id

) : Parcelable
