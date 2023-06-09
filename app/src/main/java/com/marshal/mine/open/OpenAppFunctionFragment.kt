package com.marshal.mine.open

import android.util.Log
import com.marshal.mine.adapter.OpenQuestionAdapter
import com.marshal.mine.event.OpenQuestionEventBean
import com.marshal.pojo.OpenAnswersBean


/**
 * 应用基础
 */
class OpenAppFunctionFragment : OpenQuestionBaseFragment() {

    private var adapter: OpenQuestionAdapter? = null
    private var singleQuestions = ArrayList<OpenAnswersBean>()
    private var multipleQuestions = ArrayList<OpenAnswersBean>()
    private var estimateQuestions = ArrayList<OpenAnswersBean>()

    override fun viewCreate() {
        super.viewCreate()
        Log.d("TAG","OpenAppFunctionFragment >>>>>")

        adapter = OpenQuestionAdapter()
        binding?.recyclerList?.adapter = adapter
        layoutManager?.scrollToPosition(0)
        adapter?.itemList?.clear()
        adapter?.addListAll(singleQuestions)

    }

    fun addSingleQuestionsShow(data: OpenQuestionEventBean){
        adapter?.itemList?.clear()
        singleQuestions = data.questionList as ArrayList<OpenAnswersBean>
        adapter?.addListAll(singleQuestions)
        layoutManager?.scrollToPosition(0)
    }

    fun setMultipleQuestionList(data: OpenQuestionEventBean){
        multipleQuestions = data.questionList as ArrayList<OpenAnswersBean>
    }

    fun setEstimateQuestionList(data: OpenQuestionEventBean){
        estimateQuestions = data.questionList as ArrayList<OpenAnswersBean>
    }

    fun updateList(type:Int){
        adapter?.itemList?.clear()
        when(type){
            1->{
                adapter?.addListAll(singleQuestions)
            }
            2->{
                adapter?.addListAll(multipleQuestions)
            }
            3->{
                adapter?.addListAll(estimateQuestions)
            }
        }
        layoutManager?.scrollToPosition(0)
    }


}