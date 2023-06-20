package com.marshal

import android.view.View
import com.marshal.base_common.baseview.BaseViewActivity
import com.marshal.databinding.ActivityLoginBinding

class LoginActivity : BaseViewActivity<ActivityLoginBinding>() {

    override fun getResLayoutBinding(): View? {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {

    }

}