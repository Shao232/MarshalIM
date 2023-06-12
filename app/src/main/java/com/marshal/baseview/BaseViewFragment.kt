package com.marshal.baseview

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.marshal.R
import com.marshal.utils.NoShakeBtnUtil

abstract class BaseViewFragment<T: ViewBinding>:Fragment() {

    /**
     * 通过子类实现getActivityLayoutId方法创建对象
     */
    var binding:T? = null

    var mContext:Context? = null

    var hasIncludeToolbar:Boolean =false

    /**
     * toolbar
     */
    var ivBackBar: AppCompatImageView? = null
    var tvTitle: AppCompatTextView? = null
    var ivMenu: AppCompatImageView? = null
    var clnTitleLayout: ConstraintLayout? = null
    var ivDown: AppCompatImageView? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = getResLayoutBinding()
        hasIncludeToolbar = hasToolbar()
        if(hasIncludeToolbar) {
            with(view) {
                clnTitleLayout = this?.findViewById(R.id.cln_center_title_layout)
                ivBackBar = this?.findViewById(R.id.iv_back_toolbar)
                tvTitle = this?.findViewById(R.id.tv_title_toolbar)
                ivDown = this?.findViewById(R.id.iv_title_down)
                ivMenu = this?.findViewById(R.id.iv_menu_toolbar)
            }
            ivBackBar?.visibility = View.VISIBLE

            ivBackBar?.setOnClickListener {
                if(NoShakeBtnUtil.isFastDoubleClick(it)){
                    return@setOnClickListener
                }

                (mContext as? AppCompatActivity)?.finish()
            }
            ivMenu?.setOnClickListener {
                if(NoShakeBtnUtil.isFastDoubleClick(it)){
                    return@setOnClickListener
                }
                onClickMenu(it)
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        initView()
        subscribeBack()
    }

    abstract fun getResLayoutId():Int

    abstract fun getResLayoutBinding(): View?

    open fun hasToolbar():Boolean = false

    abstract fun initView()

    open fun subscribeBack(){}

    open fun onClickMenu(view:View){}

    fun setTitle(title:String){
        tvTitle?.text = title
    }

}