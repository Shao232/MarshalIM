package com.marshalim.moudle.open.question.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import com.marshal.base_common.baseadapter.BaseRecyclerViewHolder
import com.marshalim.moudle.open.question.R

class TextContentViewHolder(context: Context?, resId:Int,
                            parent: ViewGroup
): BaseRecyclerViewHolder(resId, parent)  {

   val tvContent: AppCompatTextView? = itemView.findViewById(R.id.tv_content)

}