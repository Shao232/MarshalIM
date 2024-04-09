package com.marshalim.moudle.open.question.fragment.third.adapter

import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import com.marshal.base_common.baseadapter.BaseRecyclerViewHolder
import com.marshalim.moudle.open.question.R

class OpenThirdEnglishViewHolder(parent: ViewGroup) :
    BaseRecyclerViewHolder(R.layout.item_open_third_english_first, parent) {

    var tvTitleEnglish: AppCompatTextView? = itemView.findViewById(R.id.tv_title_english_first)
    var lvnAnswerSelectGroup:LinearLayout? = itemView.findViewById(R.id.lvn_answer_select_group_third)
    var tvAnswerFirstEnglish:AppCompatTextView? = itemView.findViewById(R.id.tv_answer_first_english)
    var tvAnswerSecondEnglish:AppCompatTextView? = itemView.findViewById(R.id.tv_answer_second_english)
    var tvAnswerThirdEnglish:AppCompatTextView? = itemView.findViewById(R.id.tv_answer_third_english)
    var tvAnswerFourthEnglish:AppCompatTextView? = itemView.findViewById(R.id.tv_answer_fourth_english)
    var tvEnglishToChinese:AppCompatTextView? = itemView.findViewById(R.id.tv_english_to_chinese)
    var tvAnswerParseThird:AppCompatTextView? = itemView.findViewById(R.id.tv_answer_parse_third)


}