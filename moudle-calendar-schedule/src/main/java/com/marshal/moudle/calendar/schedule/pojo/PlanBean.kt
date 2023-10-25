package com.marshal.moudle.calendar.schedule.pojo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal


@Parcelize
data class PlanBean(
    var id: BigDecimal? = null,
    var title:String? ="",
    var remark:String?="",
    var startTime:Long? = 0,
    var endTime:Long?=0,
    var finishStatus:Int? =0,
    var userId:String? = "",
    var createTime:Long? =0,
    var updateTime:Long? = 0,
    var subPlanList:ArrayList<PlanBean>? = null
) : Parcelable {
}