package com.marshal.mine.open

import android.util.Log
import com.marshal.mine.adapter.OpenQuestionProgramDesignAdapter
import com.marshal.mine.event.OpenQuestionEventBean
import com.marshal.pojo.OpenAnswersBean


/**
 * 程序设计
 */
class OpenProgramDesignFragment : OpenQuestionBaseFragment()  {

    private var adapter: OpenQuestionProgramDesignAdapter? = null
    private var singleQuestions = ArrayList<OpenAnswersBean>()

    override fun viewCreate() {
        super.viewCreate()
        Log.d("TAG","OpenProgramDesignFragment >>>>>")

        adapter = OpenQuestionProgramDesignAdapter()
        binding?.recyclerList?.adapter = adapter
        layoutManager?.scrollToPosition(0)
        adapter?.itemList?.clear()
        adapter?.addListAll(singleQuestions)
    }

    fun addSingleQuestionsShow(data: OpenQuestionEventBean){
        singleQuestions = data.questionList as ArrayList<OpenAnswersBean>
    }

}