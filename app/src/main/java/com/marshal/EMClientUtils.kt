package com.marshal

import android.util.Log
import com.hyphenate.EMCallBack
import com.hyphenate.EMConnectionListener
import com.hyphenate.chat.EMClient
import com.hyphenate.exceptions.HyphenateException
import com.marshal.base_common.store.getAppAppLoginUserAccount
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * 封装 EMClient
 *
 */
object EMClientUtils {

    private val emConnectionListener:EMConnectionListener = object :EMConnectionListener{
        override fun onConnected() {
            Log.d("TAG", "MainActivity EMConnectionListener 已连接")
        }

        override fun onDisconnected(errorCode: Int) {
            Log.d("TAG", "MainActivity EMConnectionListener error:${errorCode}")
        }

    }

    /**
     * 判断登录
     * true 登录
     * false 未登录
     */
    fun checkEMLogin():Boolean {
        val loginAccount = getAppAppLoginUserAccount()
        return !(EMClient.getInstance().currentUser.isEmpty() || !EMClient.getInstance().isLoggedIn
                || loginAccount?.isEmpty() == true)
    }

    /**
     * 设置连接监听
     */
    fun setEMConnectionListener(){
        EMClient.getInstance().addConnectionListener(emConnectionListener)
    }

    fun setEMLogout(emCallBack: EMCallBack){
        EMClient.getInstance().logout(true,emCallBack)
    }

    /**
     * 在客户端注册账号 test环境
     *
     */
    fun setEMCreateAccount(loginAccount:String,loginPassword:String){
        GlobalScope.launch(Dispatchers.IO) {
            val account = getAppAppLoginUserAccount()
            if (account?.isNotEmpty() == true && account != "marshal" && account != "bill") {
                try {
                    EMClient.getInstance().createAccount(loginAccount, loginPassword)
                } catch (e: HyphenateException) {
                    Log.e("TAG", "msg:${e.description},errorCode:${e.errorCode}")
                }
            }
        }
    }

    /**
     * 登录账号
     *
     */
    fun setEMLogin(loginAccount:String,loginPassword:String,emCallBack: EMCallBack){
        EMClient.getInstance().login(loginAccount, loginPassword,emCallBack)
    }

    //https://images.juheapi.com/jztk/c1c2subject1/3.jpg


}