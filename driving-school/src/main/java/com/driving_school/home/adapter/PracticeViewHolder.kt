package com.driving_school.home.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.driving_school.R
import com.marshal.base_common.baseadapter.BaseRecyclerViewHolder

class PracticeViewHolder(context: Context?, parent: ViewGroup):BaseRecyclerViewHolder(context,
R.layout.frag_radio,parent) {

    var tvRadioSubject:TextView? = null
    var ivShowImg:ImageView? = null
    var tvRadioItem1:TextView? = null
    var tvRadioItem2:TextView? = null
    var tvRadioItem3:TextView? = null
    var tvRadioItem4:TextView? = null
    var tvRadioExplain:TextView? = null

    init {
        with(itemView){
            tvRadioSubject = findViewById(R.id.tv_radio_subject)
            ivShowImg = findViewById(R.id.iv_radio_subject)
            tvRadioItem1 = findViewById(R.id.tv_radio_item1)
            tvRadioItem2 = findViewById(R.id.tv_radio_item2)
            tvRadioItem3 = findViewById(R.id.tv_radio_item3)
            tvRadioItem4 = findViewById(R.id.tv_radio_item4)
            tvRadioExplain = findViewById(R.id.tv_radio_explain)

        }
    }

}