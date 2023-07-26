package com.marshal.pojo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class FunctionBean(
    //功能名
    val functionName:String = "",
    //功能type，用type来区别功能，进行跳转
    val functionPath:String = ""
) : Parcelable
