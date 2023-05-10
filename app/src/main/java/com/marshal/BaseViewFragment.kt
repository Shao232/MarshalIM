package com.marshal

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View? = getResLayoutBinding()
        if(view != null) {
            return inflater.inflate(getResLayoutId(), view as? ViewGroup,true)
        }

        return super.onCreateView(inflater, container, savedInstanceState)
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