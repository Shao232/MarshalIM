package com.marshal.mine.open

import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.marshal.mine.open.adapter.OpenQuestionProgramDesignAdapter
import com.marshal.pojo.OpenAnswersBean
import getAppProgramSingleData


/**
 * 程序设计
 */
class OpenProgramDesignFragment : OpenQuestionBaseFragment()  {

    private var adapter= OpenQuestionProgramDesignAdapter()
    private var programSingleQuestions =  ArrayList<OpenAnswersBean>()


    override fun getAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>? {
        return adapter as?  RecyclerView.Adapter<RecyclerView.ViewHolder>
    }

    override fun viewCreate() {
        super.viewCreate()
        val appProgramSingleData = getAppProgramSingleData()
        if(appProgramSingleData?.isNotEmpty() == true) {
            val type = object : TypeToken<ArrayList<OpenAnswersBean>>() {}.type
            programSingleQuestions = Gson().fromJson(appProgramSingleData,type)
        }
        adapter.addListAll(programSingleQuestions)
    }

}