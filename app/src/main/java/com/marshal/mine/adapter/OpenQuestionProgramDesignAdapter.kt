package com.marshal.mine.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.marshal.R
import com.marshal.baseadapter.BaseRecyclerAdapter
import com.marshal.pojo.OpenAnswerBean
import com.marshal.pojo.OpenAnswersBean

class OpenQuestionProgramDesignAdapter : BaseRecyclerAdapter<OpenQuestionViewHolder, OpenAnswersBean>() {

    override fun onViewHolder(parent: ViewGroup, viewType: Int): OpenQuestionViewHolder {
        return OpenQuestionViewHolder(mContext, R.layout.item_open_question_bank, parent)
    }

    @SuppressLint("SetTextI18n")
    override fun bindViewHolderData(holder: OpenQuestionViewHolder, position: Int) {
        val openAnswersBean = itemList[position]
        //设置题目
        holder.tvQuestionTitle?.text = openAnswersBean.title
        val layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        //设置recyclerview的配置
        holder.recyclerListAnswers?.layoutManager = layoutManager
        val adapter = OpenQuestionAnswersAdapter()
        //设置答案的adapter
        holder.recyclerListAnswers?.adapter = adapter
        adapter.addListAll(openAnswersBean.answerList as ArrayList<OpenAnswerBean>)


        holder.clnRightAnswerLayout?.visibility = View.VISIBLE
        holder.tvRightAnswerContent?.text ="${openAnswersBean.rightAnswer}"

    }


}