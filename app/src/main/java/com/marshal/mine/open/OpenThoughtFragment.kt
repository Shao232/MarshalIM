package com.marshal.mine.open

import android.util.Log
import com.marshal.mine.adapter.OpenQuestionThoughtAdapter
import com.marshal.mine.event.OpenQuestionEventBean
import com.marshal.pojo.OpenAnswersBean


/**
 * 思维导论
 */

class OpenThoughtFragment :OpenQuestionBaseFragment()  {

    private var adapter: OpenQuestionThoughtAdapter? = null
    private var singleQuestions = ArrayList<OpenAnswersBean>()

    override fun viewCreate() {
        super.viewCreate()
        Log.d("TAG","OpenThoughtFragment >>>>>")

        adapter = OpenQuestionThoughtAdapter()
        binding?.recyclerList?.adapter = adapter
        layoutManager?.scrollToPosition(0)
        adapter?.itemList?.clear()
        adapter?.addListAll(singleQuestions)
    }

    fun addSingleQuestionsShow(data: OpenQuestionEventBean){
        singleQuestions = data.questionList as ArrayList<OpenAnswersBean>
    }

}