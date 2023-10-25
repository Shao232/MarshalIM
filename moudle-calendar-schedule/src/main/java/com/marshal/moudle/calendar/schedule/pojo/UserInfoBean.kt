package com.marshal.moudle.calendar.schedule.pojo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal


@Parcelize
data class UserInfoBean(
    var id: BigDecimal?=null,
    var username:String?="",
    var mobile:String?="",
    var token:String?=null

) : Parcelable