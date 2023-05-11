package com.marshal.baseview

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseViewFragment<T: ViewBinding>:Fragment() {

    /**
     * 通过子类实现getActivityLayoutId方法创建对象
     */
    var binding:T? = null

    var mContext:Context? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return getResLayoutBinding()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        subscribeBack()
    }

    abstract fun getResLayoutId():Int

    abstract fun getResLayoutBinding(): View?

    abstract fun initView()

    open fun subscribeBack(){}

}