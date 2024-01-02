package com.marshal.pojo

import android.os.Parcelable
import com.marshal.base_common.utils.GsonUtils
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReadSmSBean(
    var phoneNumber: String? = "",
    var smsContent: String? = ""
) : Parcelable {

    override fun toString(): String {
        return GsonUtils.objToJson(this)
    }
}
