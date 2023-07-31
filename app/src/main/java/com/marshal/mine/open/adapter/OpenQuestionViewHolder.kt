package com.marshal.mine.open.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.marshal.R
import com.marshal.base_common.baseadapter.BaseRecyclerViewHolder

class OpenQuestionViewHolder(context: Context?, resId:Int,
                              parent: ViewGroup
): BaseRecyclerViewHolder(resId, parent)  {

    var clnRootViewLayout:ConstraintLayout? = null
    var tvQuestionTitle: TextView? = null
    var recyclerListAnswers:RecyclerView? = null
    var clnRightAnswerLayout:ConstraintLayout? = null
    var tvRightAnswerTag:TextView? = null
    var tvRightAnswerContent:TextView? = null

    init {
        with(itemView) {
            clnRootViewLayout = findViewById(R.id.cln_question_bank)
            tvQuestionTitle = findViewById(R.id.tv_question_title)
            recyclerListAnswers = findViewById(R.id.recycler_list_answers)
            clnRightAnswerLayout = findViewById(R.id.cln_right_answer_layout)
            tvRightAnswerTag = findViewById(R.id.tv_right_answers_tag)
            tvRightAnswerContent = findViewById(R.id.tv_right_answer_content)
        }
    }

}