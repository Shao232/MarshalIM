package com.marshal.mine.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.marshal.R
import com.marshal.baseadapter.BaseRecyclerViewHolder

class OpenQuestionOnlyAnswerViewHolder(context: Context?, resId:Int,
                                       parent: ViewGroup
): BaseRecyclerViewHolder(context, resId, parent) {

    var tvOnlyAnswer:TextView? = null
    var clnChoice:ConstraintLayout? =null

    init {
        with(itemView) {
            clnChoice = findViewById(R.id.cln_question_single_choice)
            tvOnlyAnswer = findViewById(R.id.tv_question_answer_num)
        }
    }

}