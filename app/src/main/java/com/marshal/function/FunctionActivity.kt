package com.marshal.function

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.driving_school.DrivingRouterPath
import com.marshal.AppRouterPath
import com.marshal.AppRouterPath.APP_FUNCTION_PAGE
import com.marshal.base_common.baseadapter.AdapterItemOnClickListener
import com.marshal.base_common.baseview.BaseViewActivity
import com.marshal.databinding.ActivityFunctionBinding
import com.marshal.moudle.calendar.schedule.getAppInfoToken
import com.marshal.moudle.calendar.schedule.utils.CalendarRouterPath
import com.marshal.pojo.FunctionBean
import com.marshalim.moudle.open.question.OpenQuestionRouter

/**
 * 金刚页
 */
@Route(path = APP_FUNCTION_PAGE)
class FunctionActivity : BaseViewActivity<ActivityFunctionBinding>() {

    private var adapter: FunctionAdapter? = null

    override fun hasToolbar(): Boolean = true

    override fun getResLayoutBinding(): View? {
        binding = ActivityFunctionBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {
        if (hasIncludeToolbar) {
            setTitle("功能")
        }

        adapter = FunctionAdapter()
        initData()
        val gridLayoutManager = GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false)
        binding?.recyclerListFunction?.layoutManager = gridLayoutManager
        binding?.recyclerListFunction?.adapter = adapter

        adapter?.setAdapterItemOnClickListener(object : AdapterItemOnClickListener<FunctionBean> {
            override fun onClick(view: View, bean: FunctionBean) {
                if(bean.functionPath == CalendarRouterPath.APP_ADD_EVENT_CALENDAR
                    && getAppInfoToken().isNullOrEmpty()) {
                    ARouter.getInstance().build(AppRouterPath.APP_LOGIN_PAGE).navigation(this@FunctionActivity)
                    return
                }

                ARouter.getInstance().build(bean.functionPath).navigation(this@FunctionActivity)
            }
        })

    }

    private fun initData() {
        adapter?.itemList?.add(FunctionBean("蓝牙连接", AppRouterPath.MINE_TO_BLUE_TOOTH))
        adapter?.itemList?.add(FunctionBean("UDP连接", AppRouterPath.MINE_TO_CONNECT_UDP))
        adapter?.itemList?.add(FunctionBean("协程学习", AppRouterPath.HOME_COROUTINES))
        adapter?.itemList?.add(FunctionBean("保存数据", AppRouterPath.STORE_DATA_PAGE))
        adapter?.itemList?.add(FunctionBean("讯飞星火", AppRouterPath.APP_AI_CHAT))
        adapter?.itemList?.add(FunctionBean("自定义View", AppRouterPath.APP_CUSTOM_VIEW_PAGE))
        adapter?.itemList?.add(FunctionBean("开大题库(仅限学习)", OpenQuestionRouter.OPEN_QUESTION_BANK))
        adapter?.itemList?.add(FunctionBean("开大题库(仅供学习web版)", OpenQuestionRouter.OPEN_QUESTION_WEB_BANK))
        adapter?.itemList?.add(FunctionBean("驾校模拟", DrivingRouterPath.Driving_HOME_PATH))
        adapter?.itemList?.add(FunctionBean("添加日程", CalendarRouterPath.APP_ADD_EVENT_CALENDAR))
        adapter?.itemList?.add(FunctionBean("短信列表", AppRouterPath.APP_SMS_LIST_PAGE))

    }
}