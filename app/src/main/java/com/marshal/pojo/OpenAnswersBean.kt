package com.marshal.pojo

data class OpenAnswersBean(
    var id:Int,
    var title:String?="",
    //答案，比如单选4个回答,判断两个回答
    var answerList: ArrayList<String>? = ArrayList(),
    var rightAnswer:String? = ""
)
