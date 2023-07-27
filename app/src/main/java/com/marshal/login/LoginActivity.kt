package com.marshal.login

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.hyphenate.EMCallBack
import com.marshal.AppRouterPath.APP_LOGIN_PAGE
import com.marshal.EMClientUtils
import com.marshal.base_common.baseview.BaseViewActivity
import com.marshal.base_common.store.putAppLoginUserAccount
import com.marshal.base_common.store.putAppLoginUserPwd
import com.marshal.databinding.ActivityLoginBinding

/**
 * 测试账号 marshal 123456
 *         bill    123456
 */

@Route(path = APP_LOGIN_PAGE)
class LoginActivity : BaseViewActivity<ActivityLoginBinding>() {

    override fun hasToolbar(): Boolean = true

    override fun getResLayoutBinding(): View? {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {
        if (hasIncludeToolbar) {
            setTitle("登录")
        }

        binding?.btnLogin?.setOnClickListener {
            val loginAccount = binding?.editUserNameLogin?.text.toString()
            val loginPassword = binding?.editUserPasswordLogin?.text.toString()

            if (checkTextEmpty(loginAccount, loginPassword)) return@setOnClickListener

            login(loginAccount, loginPassword)

        }

    }

    private fun checkTextEmpty(loginAccount: String, loginPassword: String): Boolean {
        if (loginAccount.trim().isEmpty()) {
            showToast("账号不能为空")
            return true
        }
        if (loginPassword.trim().isEmpty()) {
            showToast("密码不能为空")
            return true
        }
        return false
    }

    private fun login(loginAccount: String, loginPassword: String) {
        EMClientUtils.setEMLogin(loginAccount, loginPassword, object : EMCallBack {
            override fun onSuccess() {
                Log.d("TAG", "登录成功")
                showToast("登录成功")
                //登录信息保存本地
                putAppLoginUserAccount(loginAccount)
                putAppLoginUserPwd(loginPassword)

                val intent = Intent("com.marshal.login.user")
                LocalBroadcastManager.getInstance(this@LoginActivity).sendBroadcast(intent)
                finish()
            }

            override fun onError(code: Int, error: String?) {
                Log.e("TAG", "msg:${error},errorCode:${code}")
            }
        })
    }
}
