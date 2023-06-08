package com.marshal.mine.event

import com.marshal.pojo.OpenAnswersBean

class OpenQuestionEventBean {
    /**
     * 1 单选
     * 2 多选
     * 3 判断
     */
    var what:Int = 0
    var questionList:ArrayList<OpenAnswersBean>? = ArrayList()
}