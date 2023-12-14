package com.marshalim.moudle.open.question

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.marshal.base_common.baseview.BaseViewFragment
import com.marshalim.moudle.open.question.databinding.IncludeOpenQuestionListFragmentBinding

/**
 * fragment都基于recyclerview，抽取公共方法到父类中
 */
abstract class OpenQuestionBaseFragment : BaseViewFragment<IncludeOpenQuestionListFragmentBinding>() {

    protected var layoutManager: LinearLayoutManager? = null

    override fun getResLayoutId(): Int = R.layout.include_open_question_list_fragment

    override fun getResLayoutBinding(): View? {
        binding = IncludeOpenQuestionListFragmentBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding?.recyclerList?.layoutManager = layoutManager
        getAdapter()
        viewCreate()

        binding?.recyclerList?.scrollBarSize

    }

    abstract fun getAdapter()

    open fun viewCreate(){}

}