package com.marshal.mine.open.adapter

import android.view.View
import android.view.ViewGroup
import com.marshal.R
import com.marshal.baseadapter.AdapterItemOnClickListener
import com.marshal.baseadapter.BaseRecyclerAdapter
import com.marshal.pojo.OpenAnswerBean

/**
 * 单选题 答案
 */
class OpenQuestionAnswersAdapter : BaseRecyclerAdapter<OpenQuestionOnlyAnswerViewHolder, OpenAnswerBean>() {

    var onSelectAnswerListener: QuestionSelectAnswerListener? = null

    fun setOnSelectAnswerClickListener(selectAnswerListener: QuestionSelectAnswerListener) {
        onSelectAnswerListener = selectAnswerListener
    }

    override fun onViewHolder(parent: ViewGroup, viewType: Int): OpenQuestionOnlyAnswerViewHolder {
        return OpenQuestionOnlyAnswerViewHolder(mContext, R.layout.item_open_question_choice,parent)
    }

    override fun bindViewHolderData(holder: OpenQuestionOnlyAnswerViewHolder, position: Int) {
        val answer = itemList[position]
        holder.tvOnlyAnswer?.text = "${answer.answerTitle}. ${answer.answerContent}"
        //告诉adapter有选择的答案，修改背景颜色
        holder.clnChoice?.setBackgroundColor( if(answer.hasSelectSelf == true) mContext?.resources
            ?.getColor(R.color.gray_line)?:0 else mContext?.resources
            ?.getColor(R.color.white_mode)?:0)

        itemOnClickListener = object : AdapterItemOnClickListener<OpenAnswerBean>{
            override fun onClick(view: View, bean: OpenAnswerBean) {
                super.onClick(view, bean)
                //通过循环，选择任意一个答案，就会告诉adapter有选择的答案，修改背景颜色
                //这里不判断是否正确
                itemList.forEach {
                    it.hasSelectSelf = it.answerTitle.contentEquals(bean.answerTitle)
                }
                onSelectAnswerListener?.onSelectAnswer(view,bean)
                notifyDataSetChanged()
            }
        }
    }

    interface QuestionSelectAnswerListener{
        fun onSelectAnswer(view:View,bean: OpenAnswerBean)
    }
}