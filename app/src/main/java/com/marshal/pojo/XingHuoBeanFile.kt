package com.marshal.pojo

//返回的json结果拆解
data class JsonParse(
    var header: Header? = null,
    var payload: Payload? = null
)

data class Header(
    var code: Int = 0,
    var status:Int = 0,
    var sid: String? = null
)

data class Payload(
    var choices: Choices? = null
)

data class Choices(
    var text: List<TextBean>? = null
)

data class TextBean(
    var role: String? = null,
    var content: String? = null
)
