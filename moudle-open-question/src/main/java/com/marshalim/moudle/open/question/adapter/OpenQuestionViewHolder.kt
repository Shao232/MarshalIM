package com.marshalim.moudle.open.question.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.marshal.base_common.baseadapter.BaseRecyclerViewHolder
import com.marshalim.moudle.open.question.R

class OpenQuestionViewHolder(context: Context?, resId:Int,
                              parent: ViewGroup
): BaseRecyclerViewHolder(resId, parent)  {

    var tvQuestionTitle: TextView? = null
    var recyclerListAnswers:RecyclerView? = null
    var clnRightAnswerLayout:ConstraintLayout? = null
    var tvRightAnswerTag:TextView? = null
    var tvRightAnswerContent:TextView? = null

    init {
        with(itemView) {
            tvQuestionTitle = findViewById(R.id.tv_question_title)
            recyclerListAnswers = findViewById(R.id.recycler_list_answers)
            clnRightAnswerLayout = findViewById(R.id.cln_right_answer_layout)
            tvRightAnswerTag = findViewById(R.id.tv_right_answers_tag)
            tvRightAnswerContent = findViewById(R.id.tv_right_answer_content)
        }
    }

}