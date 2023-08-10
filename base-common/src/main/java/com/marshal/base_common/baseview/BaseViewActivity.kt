package com.marshal.base_common.baseview

import NoShakeBtnUtil
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewbinding.ViewBinding
import com.marshal.base_common.MApplication
import com.marshal.base_common.R
import com.marshal.base_common.store.getAppAppLoginUserAccount
import com.marshal.base_common.store.getAppAppLoginUserPwd

abstract class BaseViewActivity<T : ViewBinding> : AppCompatActivity() {

    /**
     * 通过子类实现getActivityLayoutId方法创建对象
     */
    var binding: T? = null

    var context: Context? = null

    var hasIncludeToolbar: Boolean = false

    /**
     * toolbar
     */
    var ivBackBar: AppCompatImageView? = null
    var tvTitle: AppCompatTextView? = null
    var ivMenu: AppCompatImageView? = null
    var clnTitleLayout: ConstraintLayout? = null
    var ivDown: AppCompatImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        context = MApplication.getInstance().applicationContext

        val view: View? = getResLayoutBinding()
        if (view != null) {
            setContentView(view)
        }
        hasIncludeToolbar = hasToolbar()

        if (hasIncludeToolbar) {
            clnTitleLayout = findViewById(R.id.cln_center_title_layout)
            ivBackBar = findViewById(R.id.iv_back_toolbar)
            tvTitle = findViewById(R.id.tv_title_toolbar)
            ivDown = findViewById(R.id.iv_title_down)
            ivMenu = findViewById(R.id.iv_menu_toolbar)
            ivBackBar?.visibility = View.VISIBLE

            ivBackBar?.setOnClickListener {
                if (NoShakeBtnUtil.isFastDoubleClick(it)) {
                    return@setOnClickListener
                }

                finish()
            }
            ivMenu?.setOnClickListener {
                if (NoShakeBtnUtil.isFastDoubleClick(it)) {
                    return@setOnClickListener
                }

                onClickMenu(it)
            }
        }

        initView()

        subscribeBack()
    }

    abstract fun getResLayoutBinding(): View?

    open fun hasToolbar(): Boolean = false

    abstract fun initView()

    open fun subscribeBack() {}

    open fun onClickMenu(view: View) {}

    fun setTitle(title: String) {
        tvTitle?.text = title
    }

    fun showProgressDialog(){
        showToast("加载中,请稍等")
    }

    open fun showToast(toast: String) {
        runOnUiThread {
            Toast.makeText(context, toast, Toast.LENGTH_SHORT).show()
        }
    }

    fun checkLoginInfo(): Boolean {
        val account = getAppAppLoginUserAccount()
        val pwd = getAppAppLoginUserPwd()
        return account?.isNotEmpty() == true && pwd?.isNotEmpty() == true
    }

}