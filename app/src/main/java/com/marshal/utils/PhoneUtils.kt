package com.marshal.utils

/**
 * 验证类
 * 通过正则表达式验证手机或者邮箱等
 */
object PhoneUtils {

    private const val PHONE_MATCHES = "^(1\\d+)\$"

    fun checkPhone(phone: String): Boolean {
        if (phone.isEmpty()) {
            return false
        }
        return phone.matches(PHONE_MATCHES.toRegex())
    }


}