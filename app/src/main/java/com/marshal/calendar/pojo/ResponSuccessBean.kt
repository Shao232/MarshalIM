package com.marshal.calendar.pojo

data class ResponseSuccessBean<T>(
    var code: Int? = 0,
    var msg: String? = "",
    var data: T? = null
)