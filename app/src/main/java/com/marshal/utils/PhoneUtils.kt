package com.marshal.utils

import android.util.Log

object PhoneUtils {

    private const val PHONE_MATCHES = "^1[3-9]\\d{9}\$"

    fun checkPhone(phone: String): Boolean {
        if (phone.isEmpty() || phone.length != 11) {
            return false
        }
        return phone.matches(PHONE_MATCHES.toRegex())
    }


}