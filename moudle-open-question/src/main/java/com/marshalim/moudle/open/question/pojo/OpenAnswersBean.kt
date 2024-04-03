package com.marshalim.moudle.open.question.pojo

import android.os.Parcelable
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class OpenAnswersBean(
    var id:Int,
    /**
     * 1 单选
     * 2.多选
     * 3.判断
     * 4.填空
     * 5.英汉翻译
     */
    var questionType:Int?=0,
    var title:String?="",
    //答案，比如单选4个回答,判断两个回答
    var answerList: ArrayList<OpenAnswerBean>? = ArrayList(),
    var rightAnswer:String? = "",
    var parseAnswer:String? = ""
) : Parcelable {

    override fun toString(): String {
        return Gson().toJson(this)
    }
}
