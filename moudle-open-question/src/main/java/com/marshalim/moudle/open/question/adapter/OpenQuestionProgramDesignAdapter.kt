package com.marshalim.moudle.open.question.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.marshal.base_common.baseadapter.BaseRecyclerAdapter
import com.marshalim.moudle.open.question.R
import com.marshalim.moudle.open.question.pojo.OpenAnswerBean
import com.marshalim.moudle.open.question.pojo.OpenAnswersBean

class OpenQuestionProgramDesignAdapter : BaseRecyclerAdapter<OpenQuestionViewHolder, OpenAnswersBean>() {

    override fun onViewHolder(parent: ViewGroup, viewType: Int): OpenQuestionViewHolder {
        return OpenQuestionViewHolder(mContext, R.layout.item_open_question_bank, parent)
    }

    @SuppressLint("SetTextI18n")
    override fun bindViewHolderData(holder: OpenQuestionViewHolder, position: Int) {
        val openAnswersBean = itemList[position]
        //设置题目
        holder.tvQuestionTitle?.text = openAnswersBean.title
        val layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        //设置recyclerview的配置
        holder.recyclerListAnswers?.layoutManager = layoutManager
        val adapter = OpenQuestionAnswersAdapter()
        //设置答案的adapter
        holder.recyclerListAnswers?.adapter = adapter
        adapter.addListAll(openAnswersBean.answerList as ArrayList<OpenAnswerBean>)

        //程序设计里有填空题，特别处理，直接显示参考答案
        if(position <=2) {
            holder.clnRightAnswerLayout?.visibility = View.VISIBLE
            holder.tvRightAnswerContent?.text =openAnswersBean.rightAnswer
            holder.tvRightAnswerTag?.setTextColor(
                mContext?.resources?.getColor(R.color.right_answers_color) ?: 0
            )
            holder.tvRightAnswerContent?.setTextColor(
                mContext?.resources?.getColor(R.color.right_answers_color) ?: 0
            )
        }else {
            //通过查找当前是否有选择的回答，设置界面，避免界面复用导致混乱
            val selectSelfAnswerBean =
                adapter.itemList.find { answerBean -> answerBean.hasSelectSelf == true }

            if (selectSelfAnswerBean != null) {
                holder.clnRightAnswerLayout?.visibility = View.VISIBLE
                //找出正确的答案
                val rightBean = adapter.itemList.find { it.answerTitle == openAnswersBean.rightAnswer
                        || it.answerContent?.contains(openAnswersBean.rightAnswer?:"",false) == true
                }
                if(rightBean !=null) {
                    holder.tvRightAnswerContent?.text =
                        "${rightBean.answerContent}"
                }else {
                    holder.tvRightAnswerContent?.text =
                        "${openAnswersBean.rightAnswer}"
                }
                //判断选择的回答是否正确，设置正确或者错误的字体颜色
                val hasRight = openAnswersBean.rightAnswer == selectSelfAnswerBean.answerTitle
                        || selectSelfAnswerBean.answerContent?.contains(openAnswersBean.rightAnswer?:"",false) == true

                if (hasRight) {
                    holder.tvRightAnswerTag?.setTextColor(
                        mContext?.resources?.getColor(R.color.right_answers_color) ?: 0
                    )
                    holder.tvRightAnswerContent?.setTextColor(
                        mContext?.resources?.getColor(R.color.right_answers_color) ?: 0
                    )
                } else {
                    holder.tvRightAnswerTag?.setTextColor(
                        mContext?.resources?.getColor(R.color.error_answers_color) ?: 0
                    )
                    holder.tvRightAnswerContent?.setTextColor(
                        mContext?.resources?.getColor(R.color.error_answers_color) ?: 0
                    )
                }
            } else {
                holder.clnRightAnswerLayout?.visibility = View.GONE
            }
        }

        adapter.setOnSelectAnswerClickListener(object :
            OpenQuestionAnswersAdapter.QuestionSelectAnswerListener {
            @SuppressLint("SetTextI18n")
            override fun onSelectAnswer(view: View, bean: OpenAnswerBean) {
                if (bean.hasSelectSelf == true) {
                    //当用户点击回答时，判断回答是否正确
                    val rightBean =
                        adapter.itemList.find {
                            it.answerTitle contentEquals(openAnswersBean.rightAnswer)
                                    || it.answerContent?.contains(bean.answerContent?:"",false) == true
                        }
                    if(rightBean !=null) {
                        holder.tvRightAnswerContent?.text =
                            "${rightBean.answerContent}"
                    }else {
                        holder.tvRightAnswerContent?.text =
                            "${openAnswersBean.rightAnswer}"
                    }

                    val hasRight = rightBean !=null
                    if (hasRight) {
                        holder.tvRightAnswerTag?.setTextColor(
                            mContext?.resources?.getColor(R.color.right_answers_color) ?: 0
                        )
                        holder.tvRightAnswerContent?.setTextColor(
                            mContext?.resources?.getColor(R.color.right_answers_color) ?: 0
                        )
                    } else {
                        holder.tvRightAnswerTag?.setTextColor(
                            mContext?.resources?.getColor(R.color.error_answers_color) ?: 0
                        )
                        holder.tvRightAnswerContent?.setTextColor(
                            mContext?.resources?.getColor(R.color.error_answers_color) ?: 0
                        )
                    }
                }
                notifyDataSetChanged()
            }
        })
    }
}