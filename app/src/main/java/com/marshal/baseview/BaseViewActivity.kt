package com.marshal.baseview

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewbinding.ViewBinding
import com.marshal.R
import com.marshal.utils.NoShakeBtnUtil

abstract class BaseViewActivity<T:ViewBinding> :AppCompatActivity(){

    /**
     * 通过子类实现getActivityLayoutId方法创建对象
     */
    var binding:T? = null

    var context: Context? = null

    var hasIncludeToolbar:Boolean =false

    /**
     * toolbar
     */
    var ivBackBar: AppCompatImageView? = null
    var tvTitle: AppCompatTextView? = null
    var ivMenu:AppCompatImageView? = null
    var clnTitleLayout:ConstraintLayout? = null
    var ivDown:AppCompatImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        context = this

        val view:View? = getResLayoutBinding()
        if(view != null) {
            setContentView(view)
        }
        hasIncludeToolbar = hasToolbar()

        if(hasIncludeToolbar) {
            clnTitleLayout = findViewById(R.id.cln_center_title_layout)
            ivBackBar = findViewById(R.id.iv_back_toolbar)
            tvTitle = findViewById(R.id.tv_title_toolbar)
            ivDown = findViewById(R.id.iv_title_down)
            ivMenu = findViewById(R.id.iv_menu_toolbar)
            ivBackBar?.visibility = View.VISIBLE
            ivMenu?.visibility = View.VISIBLE

            ivBackBar?.setOnClickListener {
                finish()
            }
            ivMenu?.setOnClickListener {
                if(NoShakeBtnUtil.isFastDoubleClick()){
                    return@setOnClickListener
                }

                onClickMenu(it)
            }
        }

        initView()

        subscribeBack()
    }

    fun showToast(toast:String){
        Toast.makeText(context,toast,Toast.LENGTH_SHORT).show()
    }


    abstract fun getResLayoutBinding(): View?

    abstract fun initView()

    open fun hasToolbar():Boolean = false

    open fun subscribeBack(){}

    open fun onClickMenu(view:View){}


}