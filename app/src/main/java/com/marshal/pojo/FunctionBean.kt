package com.marshal.pojo

import java.io.Serializable




data class FunctionBean(
    //功能名
    val functionName:String = "",
    //功能type，用type来区别功能，进行跳转
    val functionPath:String = ""
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = -3161004436760672651L
    }

}
