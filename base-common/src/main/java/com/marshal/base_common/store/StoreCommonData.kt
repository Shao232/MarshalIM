package com.marshal.base_common.store

//登录信息
const val appLoginUserAccountKey = "app_login_user_account"
const val appLoginUserPwdKey = "app_login_user_pwd"

fun putAppLoginUserAccount(value: String){
    StoreManager.putData(appLoginUserAccountKey,value)
}

fun getAppAppLoginUserAccount():String? = StoreManager.getData(appLoginUserAccountKey,"")

fun putAppLoginUserPwd(value: String){
    StoreManager.putData(appLoginUserPwdKey,value)
}

fun getAppAppLoginUserPwd():String? = StoreManager.getData(appLoginUserPwdKey,"")