package com.marshal.sharedata

import com.marshal.pojo.OpenAnswersBean

/**
 * 在程序内共享数据
 */
object CommitShareData {

    var appSingleQuestions = ArrayList<OpenAnswersBean>()
    var appMultipleQuestions = ArrayList<OpenAnswersBean>()
    var appEstimateQuestions = ArrayList<OpenAnswersBean>()

    var programSingleQuestions = ArrayList<OpenAnswersBean>()
    var thoughtSingleQuestions = ArrayList<OpenAnswersBean>()
}