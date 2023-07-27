package com.driving_school.home.adapter

import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.driving_school.bean.QuestionsBean
import com.marshal.base_common.baseadapter.BaseRecyclerAdapter

class PracticeAdapter : BaseRecyclerAdapter<PracticeViewHolder, QuestionsBean>() {
    override fun onViewHolder(parent: ViewGroup, viewType: Int): PracticeViewHolder {
        return PracticeViewHolder(mContext, parent)
    }

    override fun bindViewHolderData(holder: PracticeViewHolder, position: Int) {
        val bean = itemList[position]
        holder.tvRadioSubject?.text = bean.question
        holder.tvRadioItem1?.text = bean.item1
        holder.tvRadioItem2?.text = bean.item2
        holder.tvRadioItem3?.text = bean.item3
        holder.tvRadioItem4?.text = bean.item4

        Glide.with(mContext?:return).load(bean.url).into(holder.ivShowImg?:return)

    }
}