package com.marshal.mine.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.marshal.R
import com.marshal.baseadapter.BaseRecyclerViewHolder

class OpenQuestionViewHolder(context: Context?, resId:Int,
                              parent: ViewGroup
):BaseRecyclerViewHolder(context, resId, parent)  {

    var tvQuestionTitle: AppCompatTextView? = null
    var recyclerListAnswers:RecyclerView? = null
    var clnRightAnswerLayout:ConstraintLayout? = null
    var tvRightAnswerContent:AppCompatTextView? = null

    init {
        with(itemView) {
            tvQuestionTitle = findViewById(R.id.tv_question_title)
            recyclerListAnswers = findViewById(R.id.recycler_list_answers)
            clnRightAnswerLayout = findViewById(R.id.cln_right_answer_layout)
            tvRightAnswerContent = findViewById(R.id.tv_right_answer_content)
        }
    }

}