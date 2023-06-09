package com.marshal.mine.event

import com.marshal.pojo.OpenAnswersBean

class OpenQuestionEventBean {
    /**
     * 计算机应用基础
     * 0x11 单选
     * 0x12 多选
     * 0x13 判断
     *
     * 思维导论
     * 0x21
     *
     * 程序设计
     * 0x31
     *
     */
    var what:Int = 0
    var questionList:ArrayList<OpenAnswersBean>? = ArrayList()
}