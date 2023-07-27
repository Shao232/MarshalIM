package com.driving_school.bean

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DrivingBean(
    val reason:String,
    val result:ArrayList<QuestionsBean>? = ArrayList(),
    val error_code:Int
) : Parcelable {
}