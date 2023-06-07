package com.marshal.mine.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import com.marshal.R
import com.marshal.baseadapter.BaseRecyclerViewHolder

class OpenQuestionOnlyAnswerViewHolder(context: Context?, resId:Int,
                                       parent: ViewGroup
): BaseRecyclerViewHolder(context, resId, parent) {

    var tvOnlyAnswer:AppCompatTextView? = null

    init {
        with(itemView) {
            tvOnlyAnswer = findViewById(R.id.tv_question_answer_num)
        }
    }

}