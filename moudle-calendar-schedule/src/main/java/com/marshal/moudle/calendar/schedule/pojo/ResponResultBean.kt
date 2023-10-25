package com.marshal.moudle.calendar.schedule.pojo

data class ResponseResultBean<T>(
    var code: Int? = 0,
    var msg: String? = "",
    var data: T? = null
)