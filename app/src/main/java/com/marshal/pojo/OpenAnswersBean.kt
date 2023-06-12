package com.marshal.pojo

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
     */
    var questionType:Int,
    var title:String?="",
    //答案，比如单选4个回答,判断两个回答
    var answerList: ArrayList<OpenAnswerBean>? = ArrayList(),
    var rightAnswer:String? = ""
) : Parcelable {

    override fun toString(): String {
        return Gson().toJson(this)
    }
}
