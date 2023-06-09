package com.marshal.mine.open

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marshal.R
import com.marshal.baseview.BaseViewFragment
import com.marshal.databinding.IncludeOpenQuestionListFragmentBinding

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
        binding?.recyclerList?.adapter =getAdapter()
        viewCreate()
    }


    abstract fun getAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>?

    open fun viewCreate(){}



}