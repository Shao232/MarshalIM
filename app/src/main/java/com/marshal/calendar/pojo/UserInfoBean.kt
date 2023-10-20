package com.marshal.calendar.pojo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class UserInfoBean(
    var id:Long?=0,
    var username:String?="",
    var mobile:String?="",
    var token:String?=null

) : Parcelable