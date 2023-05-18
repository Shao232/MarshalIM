package com.marshal.utils

object PhoneUtils {

    private const val PHONE_MATCHES = "^(1\\d+)\$"

    fun checkPhone(phone: String): Boolean {
        if (phone.isEmpty()) {
            return false
        }
        return phone.matches(PHONE_MATCHES.toRegex())
    }


}