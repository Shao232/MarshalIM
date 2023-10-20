package com.marshal.login

import android.util.Log
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.marshal.AppRouterPath.APP_LOGIN_PAGE
import com.marshal.base_common.baseview.BaseViewActivity
import com.marshal.base_common.utils.GsonUtils
import com.marshal.calendar.pojo.ResponseSuccessBean
import com.marshal.calendar.pojo.UserInfoBean
import com.marshal.databinding.ActivityLoginBinding
import com.marshal.https.HttpRequestFactory
import com.marshal.https.ScheduleService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import putAppInfoToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        val retrofit = HttpRequestFactory.getScheduleRequest()
        if (retrofit != null) {
            val service = retrofit.create(ScheduleService::class.java)
            val map: HashMap<String, String> = HashMap()
            map["username"] = "仲维昌"
            map["mobile"] = loginAccount
            map["password"] = loginPassword
            val json = GsonUtils.objToJson(map)
            Log.d("TAG", "json :${json}")
            val requestBody = json.toRequestBody("application/json".toMediaType())

            service.postScheduleLogin(requestBody).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Log.d("TAG", "success:${response.body()}")
                    val responseBean = GsonUtils.stringFromJson(
                        response.body() ?: "",
                        ResponseSuccessBean::class.java
                    )
                    Log.d("TAG", "response :${responseBean}")
                    val infoBean = GsonUtils.stringFromJson(responseBean.data.toString(),UserInfoBean::class.java)
                    Log.d("TAG", "infoBean :${infoBean}")
                    putAppInfoToken(infoBean.token?:"")

                    finish()
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.e("TAG", "错误:${t.printStackTrace()}")
                }

            })
        }

//        putAppLoginUserAccount(loginAccount)
//        putAppLoginUserPwd(loginPassword)
//

    }
}
