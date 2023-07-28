package com.driving_school.home.adapter

import android.animation.ValueAnimator
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.driving_school.R
import com.driving_school.bean.QuestionsBean
import com.marshal.base_common.baseadapter.BaseRecyclerAdapter

class PracticeAdapter : BaseRecyclerAdapter<PracticeViewHolder, QuestionsBean>() {

    var nextQuestionSelectClick:PracticeItemSelectClick? = null

    fun setOnNextQuestionSelect(onNextQuestionSelect:PracticeItemSelectClick?){
        nextQuestionSelectClick = onNextQuestionSelect
    }

    override fun onViewHolder(parent: ViewGroup, viewType: Int): PracticeViewHolder {
        return PracticeViewHolder(mContext, parent)
    }

    override fun bindViewHolderData(holder: PracticeViewHolder, position: Int) {
        val bean = itemList[position]
        holder.tvRadioSubject?.text = bean.question

        if(bean.item1.isNotEmpty()) {
            holder.lvnSelectItem1?.visibility = View.VISIBLE
            holder.tvRadioItem1?.text = bean.item1
        }

        if(bean.item2.isNotEmpty()) {
            holder.lvnSelectItem2?.visibility = View.VISIBLE
            holder.tvRadioItem2?.text = bean.item2
        }

        if(bean.item3.isNotEmpty()) {
            holder.lvnSelectItem3?.visibility = View.VISIBLE
            holder.tvRadioItem3?.text = bean.item3
        }else {
            holder.lvnSelectItem3?.visibility = View.GONE
        }

        if(bean.url.isNotEmpty()){
            holder.ivShowImg?.visibility = View.VISIBLE
            Glide.with(mContext ?: return).load(bean.url).into(holder.ivShowImg ?: return)
        }else {
            holder.ivShowImg?.visibility = View.GONE
        }

        if(bean.item4.isNotEmpty()) {
            holder.lvnSelectItem4?.visibility = View.VISIBLE
            holder.tvRadioItem4?.text = bean.item4
        }else {
            holder.lvnSelectItem4?.visibility = View.GONE
        }

        holder.tvRadioExplain?.text = bean.explains

        val count = if(itemCount == 0) 0 else  itemCount - 1
        if(position == count) {
            holder.btnNextQuestion?.text = "完成"
        }else {
            holder.btnNextQuestion?.text = "下一题"
        }

        updateInitStatus(holder)

        holder.lvnSelectItem1?.setOnClickListener {
            setAnswerUpdate(position,1, holder, bean)
            val correct = bean.answer == "1"
            nextQuestionSelectClick?.onItemTwoSelectQuestion(position,correct)
        }

        holder.lvnSelectItem2?.setOnClickListener {
            setAnswerUpdate(position,2, holder, bean)
            val correct = bean.answer == "2"
            nextQuestionSelectClick?.onItemTwoSelectQuestion(position,correct)
        }

        holder.lvnSelectItem3?.setOnClickListener {
            setAnswerUpdate(position,3, holder, bean)
            val correct = bean.answer == "3"
            nextQuestionSelectClick?.onItemTwoSelectQuestion(position,correct)
        }

        holder.lvnSelectItem4?.setOnClickListener {
            setAnswerUpdate(position,4, holder, bean)
            val correct = bean.answer == "4"
            nextQuestionSelectClick?.onItemTwoSelectQuestion(position,correct)
        }

        holder.btnBeforeQuestion?.setOnClickListener {
            nextQuestionSelectClick?.onItemOneSelectClick(position,1)
        }

        holder.btnNextQuestion?.setOnClickListener {
            nextQuestionSelectClick?.onItemOneSelectClick(position,2)
        }
    }

    private fun updateInitStatus(holder: PracticeViewHolder){
        holder.ivRadioItem1?.setImageResource(R.drawable.radio_defult_img)
        holder.ivRadioItem2?.setImageResource(R.drawable.radio_defult_img)
        holder.ivRadioItem3?.setImageResource(R.drawable.radio_defult_img)
        holder.ivRadioItem4?.setImageResource(R.drawable.radio_defult_img)
        holder.tvRadioExplain?.visibility = View.GONE
        holder.btnNextQuestion?.visibility = View.GONE
        holder.btnBeforeQuestion?.visibility = View.GONE
    }

    private fun setAnswerUpdate(position: Int,itemType: Int, holder: PracticeViewHolder, bean: QuestionsBean) {
        val answerParams = itemType.toString()
        holder.tvRadioExplain?.visibility = View.VISIBLE
        holder.btnNextQuestion?.visibility = View.VISIBLE
        holder.btnBeforeQuestion?.visibility = if(position == 0) View.GONE else View.VISIBLE
        holder.btnNextQuestion?.postDelayed({
            if(!bean.isStartAnim) {
                bean.isStartAnim = true
                val lastChildView = holder.scrollView?.getChildAt(0)
                val animator = ValueAnimator.ofFloat(0f, 1f) // 创建一个从0到1的动画效果
                animator.addUpdateListener { animation ->
                    val value =animation.animatedFraction * (lastChildView?.height ?: 0) // 获取当前动画进度对应的高度
                    if (value == 1f) { // 如果动画已经完成，则将NestedScrollView滚动到底部
                        holder.scrollView?.scrollTo(0, lastChildView?.height ?: 0)
                    } else {
                        holder.scrollView?.scrollTo(0, value.toInt()) // 否则，将NestedScrollView滚动到当前位置
                    }
                }
                animator.duration = 500L // 设置动画持续时间
                animator.start() // 开始动画
            }
        },100)
        //当选择了题目后,打算让布局滚动到底部

        when (itemType) {
            1 -> {
                if (bean.answer == answerParams) {
                    holder.ivRadioItem1?.setImageResource(R.drawable.radio_yes_img)
                } else {
                    holder.ivRadioItem1?.setImageResource(R.drawable.radio_fail_img)
                }
                holder.ivRadioItem2?.setImageResource(R.drawable.radio_defult_img)
                holder.ivRadioItem3?.setImageResource(R.drawable.radio_defult_img)
                holder.ivRadioItem4?.setImageResource(R.drawable.radio_defult_img)
            }

            2 -> {
                if (bean.answer == answerParams) {
                    holder.ivRadioItem2?.setImageResource(R.drawable.radio_yes_img)
                } else {
                    holder.ivRadioItem2?.setImageResource(R.drawable.radio_fail_img)
                }
                holder.ivRadioItem1?.setImageResource(R.drawable.radio_defult_img)
                holder.ivRadioItem3?.setImageResource(R.drawable.radio_defult_img)
                holder.ivRadioItem4?.setImageResource(R.drawable.radio_defult_img)
            }

            3 -> {
                if (bean.answer == answerParams) {
                    holder.ivRadioItem3?.setImageResource(R.drawable.radio_yes_img)
                } else {
                    holder.ivRadioItem3?.setImageResource(R.drawable.radio_fail_img)
                }
                holder.ivRadioItem1?.setImageResource(R.drawable.radio_defult_img)
                holder.ivRadioItem2?.setImageResource(R.drawable.radio_defult_img)
                holder.ivRadioItem4?.setImageResource(R.drawable.radio_defult_img)
            }

            4 -> {
                if (bean.answer == answerParams) {
                    holder.ivRadioItem4?.setImageResource(R.drawable.radio_yes_img)
                } else {
                    holder.ivRadioItem4?.setImageResource(R.drawable.radio_fail_img)
                }
                holder.ivRadioItem1?.setImageResource(R.drawable.radio_defult_img)
                holder.ivRadioItem2?.setImageResource(R.drawable.radio_defult_img)
                holder.ivRadioItem3?.setImageResource(R.drawable.radio_defult_img)
            }
        }


    }

    interface PracticeItemSelectClick {
        /**
         * 当点击下一题button时
         * position item条目
         * direction 方向 1 上一题 2 下一题
         */
        fun onItemOneSelectClick(position: Int,direction:Int=2)

        /**
         * 当选择题目后
         * position item条目
         * correct  选择是否正确
         */
        fun onItemTwoSelectQuestion(position: Int,correct:Boolean)

    }

}