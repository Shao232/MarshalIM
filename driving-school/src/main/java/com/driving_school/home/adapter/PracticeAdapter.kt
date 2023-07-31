package com.driving_school.home.adapter

import android.animation.ValueAnimator
import android.text.Html
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
        return PracticeViewHolder(parent)
    }

    override fun bindViewHolderData(holder: PracticeViewHolder, position: Int) {
        val bean = itemList[position]
        holder.tvRadioSubject?.text = bean.question

        setAnswerStatus(bean, holder)

        if(bean.url.isNotEmpty()){
            holder.ivShowImg?.visibility = View.VISIBLE
            Glide.with(mContext ?: return).load(bean.url).into(holder.ivShowImg ?: return)
        }else {
            holder.ivShowImg?.visibility = View.GONE
        }
        holder.tvRadioExplain?.text = Html.fromHtml(bean.explains)
        val count = if(itemCount == 0) 0 else  itemCount - 1
        if(position == count) {
            holder.btnNextQuestion?.text = "完成"
        }else {
            holder.btnNextQuestion?.text = "下一题"
        }

        //判断当前题目是不是回答完毕
        if(bean.isCompleteAnswer) {
            //如果完成当前题目，题库未完成，更改当前题目的样式
            setAnswerUpdate(position, bean.selectItemAnswer.toInt(), holder, bean)
            setSelectItemClickEnable(holder,false)

        }else {
            //如果没有回答，显示初始化ui
            updateInitStatus(holder)
            setSelectItemClickEnable(holder,true)
        }

        holder.lvnSelectItem1?.setOnClickListener {
            itemAnswerClick(bean, position,1, holder)
            setSelectItemClickEnable(holder,false)
        }

        holder.lvnSelectItem2?.setOnClickListener {
            itemAnswerClick(bean, position,2, holder)
            setSelectItemClickEnable(holder,false)
        }

        holder.lvnSelectItem3?.setOnClickListener {
            itemAnswerClick(bean, position,3, holder)
            setSelectItemClickEnable(holder,false)
        }

        holder.lvnSelectItem4?.setOnClickListener {
            itemAnswerClick(bean, position,4, holder)
            setSelectItemClickEnable(holder,false)
        }

        holder.btnBeforeQuestion?.setOnClickListener {
            //点击上一题触发onItemOneSelectClick
            nextQuestionSelectClick?.onItemOneSelectClick(position,1)
        }

        holder.btnNextQuestion?.setOnClickListener {
            //点击下一题触发onItemOneSelectClick
            nextQuestionSelectClick?.onItemOneSelectClick(position,2)
        }
    }

    /**
     * 当当前答案选择后，全部设置为不可点击
     */
    private fun setSelectItemClickEnable(holder: PracticeViewHolder,isEnable:Boolean = true) {
        holder.lvnSelectItem1?.isEnabled = isEnable
        holder.lvnSelectItem2?.isEnabled = isEnable
        holder.lvnSelectItem3?.isEnabled = isEnable
        holder.lvnSelectItem4?.isEnabled = isEnable
    }

    private fun setAnswerStatus(
        bean: QuestionsBean,
        holder: PracticeViewHolder
    ) {
        if (bean.item1.isNotEmpty()) {
            holder.lvnSelectItem1?.visibility = View.VISIBLE
            holder.tvRadioItem1?.text = bean.item1
        }

        if (bean.item2.isNotEmpty()) {
            holder.lvnSelectItem2?.visibility = View.VISIBLE
            holder.tvRadioItem2?.text = bean.item2
        }

        if (bean.item3.isNotEmpty()) {
            holder.lvnSelectItem3?.visibility = View.VISIBLE
            holder.tvRadioItem3?.text = bean.item3
        } else {
            holder.lvnSelectItem3?.visibility = View.GONE
        }

        if (bean.item4.isNotEmpty()) {
            holder.lvnSelectItem4?.visibility = View.VISIBLE
            holder.tvRadioItem4?.text = bean.item4
        } else {
            holder.lvnSelectItem4?.visibility = View.GONE
        }
    }

    /**
     * 设置点击答案
     */
    private fun itemAnswerClick(
        bean: QuestionsBean,
        position: Int,
        itemType: Int,
        holder: PracticeViewHolder
    ) {
        //当当前题目回答后，设置已经回答过，过滤掉点击事件
        if (bean.isCompleteAnswer) {
            return
        }

        val strItemType =  itemType.toString()
        //更新ui
        setAnswerUpdate(position, itemType, holder, bean)
        bean.isCompleteAnswer = true
        bean.selectItemAnswer =  itemType.toString()
        val correct = bean.answer == strItemType
        nextQuestionSelectClick?.onItemTwoSelectQuestion(position, correct)
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

    /**
     * 设置当前界面view的样式
     * @params position 当前条目位置
     * @param  itemType 当前选择的答案 1,2,3,4
     * @param  holder   当前view
     * @param  bean     当前条目的数据源
     */
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
         * 当点击下一题button时和点击上一题button
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