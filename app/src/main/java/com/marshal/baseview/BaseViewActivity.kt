package com.marshal.baseview

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseViewActivity<T:ViewBinding> :AppCompatActivity(){

    /**
     * 通过子类实现getActivityLayoutId方法创建对象
     */
    var binding:T? = null

    var context: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        context = this

        val view:View? = getResLayoutBinding()
        if(view != null) {
            setContentView(view)
        }

        initView()

        subscribeBack()
    }

    fun showToast(toast:String){
        Toast.makeText(context,toast,Toast.LENGTH_SHORT).show()
    }


    abstract fun getResLayoutBinding(): View?

    abstract fun initView()

    open fun subscribeBack(){}




}