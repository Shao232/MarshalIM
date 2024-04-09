package com.marshalim.moudle.open.question.fragment.third.adapter

import SizeUtils
import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.marshal.base_common.baseadapter.BaseRecyclerAdapter
import com.marshalim.moudle.open.question.R
import com.marshalim.moudle.open.question.pojo.OpenAnswersBean

class OpenThirdEnglishAdapter : BaseRecyclerAdapter<OpenThirdEnglishViewHolder, OpenAnswersBean>() {

    override fun onViewHolder(parent: ViewGroup, viewType: Int): OpenThirdEnglishViewHolder {
        return OpenThirdEnglishViewHolder(parent)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun bindViewHolderData(holder: OpenThirdEnglishViewHolder, position: Int) {
        val bean = itemList[position]
        holder.tvTitleEnglish?.visibility = View.VISIBLE
        holder.tvTitleEnglish?.text = bean.title.toString().trim()

        when (bean.questionType) {
            1 -> {
                holder.lvnAnswerSelectGroup?.visibility = View.VISIBLE
                holder.tvEnglishToChinese?.visibility = View.GONE

            }

            5 -> {
                holder.lvnAnswerSelectGroup?.visibility = View.GONE
                holder.tvEnglishToChinese?.visibility = View.VISIBLE
                holder.tvEnglishToChinese?.text = bean.rightAnswer.toString().trim()
            }
        }

        if (bean.answerList?.isNotEmpty() == true) {
            holder.tvAnswerFirstEnglish?.text =
                bean.answerList?.get(0)?.answerContent.toString().trim()
            holder.tvAnswerSecondEnglish?.text =
                bean.answerList?.get(1)?.answerContent.toString().trim()
            holder.tvAnswerThirdEnglish?.text =
                bean.answerList?.get(2)?.answerContent.toString().trim()
            holder.tvAnswerFourthEnglish?.text =
                bean.answerList?.get(3)?.answerContent.toString().trim()


            val noSelectEnglishDrawable = mContext?.getDrawable(R.drawable.icon_none_check_rb)
            noSelectEnglishDrawable?.setBounds(
                0,
                0,
                SizeUtils.dip2px(mContext, 20f),
                SizeUtils.dip2px(mContext, 20f)

            )
            val selectedEnglishDrawable = mContext?.getDrawable(R.drawable.icon_select_checked_rb)
            selectedEnglishDrawable?.setBounds(
                0,
                0,
                SizeUtils.dip2px(mContext, 20f),
                SizeUtils.dip2px(mContext, 20f))

            holder.tvAnswerFirstEnglish?.setCompoundDrawables(
                if(bean.answerList?.get(0)?.hasSelectSelf == true) selectedEnglishDrawable else noSelectEnglishDrawable,
                null,
                null,
                null
            )
            holder.tvAnswerSecondEnglish?.setCompoundDrawables(
                if(bean.answerList?.get(1)?.hasSelectSelf == true) selectedEnglishDrawable else noSelectEnglishDrawable,
                null,
                null,
                null
            )
            holder.tvAnswerThirdEnglish?.setCompoundDrawables(
                if(bean.answerList?.get(2)?.hasSelectSelf == true) selectedEnglishDrawable else noSelectEnglishDrawable,
                null,
                null,
                null
            )
            holder.tvAnswerFourthEnglish?.setCompoundDrawables(
                if(bean.answerList?.get(3)?.hasSelectSelf == true) selectedEnglishDrawable else noSelectEnglishDrawable,
                null,
                null,
                null
            )

            holder.tvAnswerFirstEnglish?.setOnClickListener {
                setCheckAnswer(holder,R.id.tv_answer_first_english,bean)
            }

            holder.tvAnswerSecondEnglish?.setOnClickListener {
                setCheckAnswer(holder,R.id.tv_answer_second_english,bean)
            }

            holder.tvAnswerThirdEnglish?.setOnClickListener {
                setCheckAnswer(holder,R.id.tv_answer_third_english,bean)
            }

            holder.tvAnswerFourthEnglish?.setOnClickListener {
                setCheckAnswer(holder,R.id.tv_answer_fourth_english,bean)
            }


            val selectAnswerBean = bean.answerList?.find { it.hasSelectSelf==true }
            holder.tvAnswerParseThird?.visibility = if(selectAnswerBean!=null) View.VISIBLE else View.GONE

            val answerStr = selectAnswerBean?.answerContent?.substring(0,1)

            if(answerStr?.trim()?.contentEquals(bean.rightAnswer?.trim()) == true) {
                holder.tvAnswerParseThird?.setTextColor(mContext?.getColor(R.color.right_answers_color)?:0)
            }else {
                holder.tvAnswerParseThird?.setTextColor(mContext?.getColor(R.color.error_answers_color)?:0)
            }
            holder.tvAnswerParseThird?.text = "正确答案:${bean.rightAnswer},答案解析:${bean.parseAnswer}"

            Log.d("TAG","holder.tvAnswerParseThird 是否可见:${holder.tvAnswerParseThird?.visibility == View.VISIBLE}")
        }


    }

    private fun setCheckAnswer(holder: OpenThirdEnglishViewHolder,checkedId: Int,bean: OpenAnswersBean) {
        when(checkedId) {
            R.id.tv_answer_first_english ->{
                bean.answerList?.get(0)?.hasSelectSelf = true
                bean.answerList?.get(1)?.hasSelectSelf = false
                bean.answerList?.get(2)?.hasSelectSelf = false
                bean.answerList?.get(3)?.hasSelectSelf = false
            }
            R.id.tv_answer_second_english ->{
                bean.answerList?.get(0)?.hasSelectSelf = false
                bean.answerList?.get(1)?.hasSelectSelf = true
                bean.answerList?.get(2)?.hasSelectSelf = false
                bean.answerList?.get(3)?.hasSelectSelf = false
            }
            R.id.tv_answer_third_english ->{
                bean.answerList?.get(0)?.hasSelectSelf = false
                bean.answerList?.get(1)?.hasSelectSelf = false
                bean.answerList?.get(2)?.hasSelectSelf = true
                bean.answerList?.get(3)?.hasSelectSelf = false
            }
            R.id.tv_answer_fourth_english ->{
                bean.answerList?.get(0)?.hasSelectSelf = false
                bean.answerList?.get(1)?.hasSelectSelf = false
                bean.answerList?.get(2)?.hasSelectSelf = false
                bean.answerList?.get(3)?.hasSelectSelf = true
            }
        }

        notifyDataSetChanged()
    }

}