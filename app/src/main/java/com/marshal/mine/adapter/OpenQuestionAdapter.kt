package com.marshal.mine.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.marshal.R
import com.marshal.baseadapter.BaseRecyclerAdapter
import com.marshal.pojo.OpenAnswersBean

class OpenQuestionAdapter : BaseRecyclerAdapter<OpenQuestionViewHolder, OpenAnswersBean>() {

    override fun onViewHolder(parent: ViewGroup, viewType: Int): OpenQuestionViewHolder {
        return OpenQuestionViewHolder(mContext, R.layout.item_open_question_bank, parent)
    }

    override fun bindViewHolderData(holder: OpenQuestionViewHolder, position: Int) {
        val bean = itemList[position]
        holder.tvQuestionTitle?.text = bean.title
        val layoutManager = LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false)
        holder.recyclerListAnswers?.layoutManager = layoutManager
        val adapter = OpenQuestionAnswersAdapter()
        holder.recyclerListAnswers?.adapter = adapter
        adapter.addListAll(bean.answerList as ArrayList<String>)
    }
}