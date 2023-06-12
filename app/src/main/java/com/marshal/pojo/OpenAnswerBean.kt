package com.marshal.pojo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OpenAnswerBean(
    // 可以是abcd这样的题号，也可以是123这样的数字序列号
    var answerTitle:String? = null,
    //题目的选择内容
    var answerContent:String? = null,
    //设置每一个选择是否点击，如果点击为true
    var hasSelectSelf:Boolean? = false
) : Parcelable