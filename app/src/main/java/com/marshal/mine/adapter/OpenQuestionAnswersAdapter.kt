package com.marshal.mine.adapter

import android.view.ViewGroup
import com.marshal.R
import com.marshal.baseadapter.BaseRecyclerAdapter

/**
 * 单选题 答案
 */
class OpenQuestionAnswersAdapter : BaseRecyclerAdapter<OpenQuestionOnlyAnswerViewHolder, String>() {
    override fun onViewHolder(parent: ViewGroup, viewType: Int): OpenQuestionOnlyAnswerViewHolder {
        return OpenQuestionOnlyAnswerViewHolder(mContext, R.layout.item_open_euestion_choice,parent)
    }

    override fun bindViewHolderData(holder: OpenQuestionOnlyAnswerViewHolder, position: Int) {
        var answer = itemList[position]
        holder.tvOnlyAnswer?.text = answer
    }


}