package com.marshal.mine.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import com.marshal.R
import com.marshal.baseadapter.BaseRecyclerAdapter
import com.marshal.pojo.OpenAnswersBean

class OpenQuestionThoughtAdapter : BaseRecyclerAdapter<OpenQuestionViewHolder, OpenAnswersBean>() {

    override fun onViewHolder(parent: ViewGroup, viewType: Int): OpenQuestionViewHolder {
        return OpenQuestionViewHolder(mContext, R.layout.item_open_question_bank, parent)
    }

    @SuppressLint("SetTextI18n")
    override fun bindViewHolderData(holder: OpenQuestionViewHolder, position: Int) {
        val openAnswersBean = itemList[position]
        //设置题目
        holder.tvQuestionTitle?.text = openAnswersBean.title
        holder.recyclerListAnswers?.visibility = View.GONE
        holder.clnRightAnswerLayout?.visibility = View.VISIBLE
        holder.tvRightAnswerContent?.text ="${openAnswersBean.rightAnswer}"

    }


}