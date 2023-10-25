package com.marshal.moudle.calendar.schedule.pojo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal


@Parcelize
data class SuperVisorBean(
    var id:BigDecimal? =null,
    var username:String? = "",
    var industryId:Int? = 0,
    var occupationId:Int? = 0,
    var roleId:Int? = 0
) : Parcelable {}

@Parcelize
data class DayScheduleBean(
    var id:BigDecimal? = null,
    var title:String? ="",
    var remark:String?="",
    var startTime:Long? = 0,
    var endTime:Long?=0,
    var taskType:Int? =0,
    var importance:Int?=0,
    var address:String? ="",
    var finishStatus:Int? =0,
    var userId:String? = "",
    var createTime:Long? =0,
    var updateTime:Long? = 0,
    var aheadTimeList:Array<Int>? = null,
    var supervisorList:ArrayList<SuperVisorBean>? = null
) : Parcelable {




}

