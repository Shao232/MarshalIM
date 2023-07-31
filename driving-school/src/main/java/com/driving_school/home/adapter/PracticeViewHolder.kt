package com.driving_school.home.adapter

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.widget.NestedScrollView
import com.driving_school.R
import com.marshal.base_common.baseadapter.BaseRecyclerViewHolder

class PracticeViewHolder(parent: ViewGroup):BaseRecyclerViewHolder(R.layout.frag_radio,parent) {

    var scrollView:NestedScrollView? = null
    var tvRadioSubject:TextView? = null
    var ivShowImg:ImageView? = null
    var lvnSelectItem1:LinearLayout? = null
    var lvnSelectItem2:LinearLayout? = null
    var lvnSelectItem3:LinearLayout? = null
    var lvnSelectItem4:LinearLayout? = null
    var tvRadioItem1:TextView? = null
    var tvRadioItem2:TextView? = null
    var tvRadioItem3:TextView? = null
    var tvRadioItem4:TextView? = null
    var ivRadioItem1:ImageView? = null
    var ivRadioItem2:ImageView? = null
    var ivRadioItem3:ImageView? = null
    var ivRadioItem4:ImageView? = null
    var tvRadioExplain:TextView? = null
    var btnBeforeQuestion:AppCompatButton? = null
    var btnNextQuestion:AppCompatButton? = null

    init {
        with(itemView){
            scrollView = findViewById(R.id.scrollview)
            tvRadioSubject = findViewById(R.id.tv_radio_subject)
            ivShowImg = findViewById(R.id.iv_radio_subject)
            lvnSelectItem1 = findViewById(R.id.lvn_radio_item1)
            lvnSelectItem2 = findViewById(R.id.lvn_radio_item2)
            lvnSelectItem3 = findViewById(R.id.lvn_radio_item3)
            lvnSelectItem4 = findViewById(R.id.lvn_radio_item4)
            tvRadioItem1 = findViewById(R.id.tv_radio_item1)
            tvRadioItem2 = findViewById(R.id.tv_radio_item2)
            tvRadioItem3 = findViewById(R.id.tv_radio_item3)
            tvRadioItem4 = findViewById(R.id.tv_radio_item4)
            ivRadioItem1 = findViewById(R.id.iv_radio_item1)
            ivRadioItem2 = findViewById(R.id.iv_radio_item2)
            ivRadioItem3 = findViewById(R.id.iv_radio_item3)
            ivRadioItem4 = findViewById(R.id.iv_radio_item4)
            tvRadioExplain = findViewById(R.id.tv_radio_explain)
            btnBeforeQuestion = findViewById(R.id.btn_before_question)
            btnNextQuestion = findViewById(R.id.btn_next_question)

        }
    }

}