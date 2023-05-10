package com.marshal

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseViewActivity<T:ViewBinding> :AppCompatActivity(){

    /**
     * 通过子类实现getActivityLayoutId方法创建对象
     */
    var binding:T? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view:View? = getResLayoutBinding()
        if(view != null) {
            setContentView(view)
        }

        initView()

        subscribeBack()
    }

    abstract fun getResLayoutBinding(): View?

    abstract fun initView()

    open fun subscribeBack(){}

}