package com.marshal.roomdata

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.marshal.IMPath.ROOM_DATA_PAGE
import com.marshal.base_common.baseview.BaseViewActivity
import com.marshal.databinding.ActivityRoomDataBinding

@Route(path = ROOM_DATA_PAGE)
class RoomDataActivity : BaseViewActivity<ActivityRoomDataBinding>() {


    override fun getResLayoutBinding(): View? {
        binding = ActivityRoomDataBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {
//            RoomUtil.getUserDao().insertAll(User(null, "jack", "love", 10, "AA"))
//            val all = RoomUtil.getUserDao().getAll()
//            Log.d("TAG", "all$all")




    }


}