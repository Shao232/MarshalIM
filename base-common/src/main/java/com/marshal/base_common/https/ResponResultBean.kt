package com.marshal.base_common.https

data class ResponseResultBean<T>(
    var code: Int? = 0,
    var msg: String? = "",
    var data: T? = null
)