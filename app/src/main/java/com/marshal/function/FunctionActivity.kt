package com.marshal.function

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.driving_school.DrivingRouterPath
import com.marshal.AppRouterPath
import com.marshal.AppRouterPath.APP_FUNCTION_PAGE
import com.marshal.android.WanAndroidRouterPath
import com.marshal.base_common.baseadapter.AdapterItemOnClickListener
import com.marshal.base_common.baseview.BaseViewActivity
import com.marshal.databinding.ActivityFunctionBinding
import com.marshal.moudle.calendar.schedule.utils.CalendarRouterPath
import com.marshal.moudle.ledger.LedgerRouterPath
import com.marshal.pojo.FunctionBean
import NoShakeBtnUtil

/**
 * 金刚页：常用功能 + 实验室（默认收起）
 */
@Route(path = APP_FUNCTION_PAGE)
class FunctionActivity : BaseViewActivity<ActivityFunctionBinding>() {

    private var mainAdapter: FunctionAdapter? = null
    private var labAdapter: FunctionAdapter? = null
    private var labExpanded = false

    override fun hasToolbar(): Boolean = true

    override fun getResLayoutBinding(): View? {
        binding = ActivityFunctionBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {
        if (hasIncludeToolbar) {
            setTitle("功能")
        }

        mainAdapter = FunctionAdapter()
        labAdapter = FunctionAdapter()
        initData()

        val spanSize = 3
        binding?.recyclerListFunctionMain?.layoutManager =
            GridLayoutManager(this, spanSize, GridLayoutManager.VERTICAL, false)
        binding?.recyclerListFunctionMain?.isNestedScrollingEnabled = false
        binding?.recyclerListFunctionMain?.adapter = mainAdapter

        binding?.recyclerListFunctionLab?.layoutManager =
            GridLayoutManager(this, spanSize, GridLayoutManager.VERTICAL, false)
        binding?.recyclerListFunctionLab?.isNestedScrollingEnabled = false
        binding?.recyclerListFunctionLab?.adapter = labAdapter

        val clickListener = object : AdapterItemOnClickListener<FunctionBean> {
            override fun onClick(view: View, bean: FunctionBean) {
                ARouter.getInstance().build(bean.functionPath).navigation(this@FunctionActivity)
            }
        }
        mainAdapter?.setAdapterItemOnClickListener(clickListener)
        labAdapter?.setAdapterItemOnClickListener(clickListener)

        // 实验室展开 / 收起
        binding?.headerLab?.setOnClickListener {
            if (NoShakeBtnUtil.isFastDoubleClick(it)) return@setOnClickListener
            labExpanded = !labExpanded
            binding?.containerLab?.visibility =
                if (labExpanded) View.VISIBLE else View.GONE
            binding?.tvLabArrow?.text = if (labExpanded) "︿" else "›"
        }
    }

    private fun initData() {
        // ---- 常用功能：10/17 上架版核心 ----
        mainAdapter?.itemList?.add(FunctionBean("记账", LedgerRouterPath.LEDGER_HOME))
        mainAdapter?.itemList?.add(FunctionBean("驾校模拟", DrivingRouterPath.Driving_HOME_PATH))
        mainAdapter?.itemList?.add(FunctionBean("添加日程", CalendarRouterPath.APP_ADD_EVENT_CALENDAR))
        mainAdapter?.itemList?.add(FunctionBean("聊天入口", AppRouterPath.CHAT_PATH))
        mainAdapter?.itemList?.add(FunctionBean("玩安卓", WanAndroidRouterPath.WAN_ANDROID_Home))

        // ---- 实验室：默认收起 ----
        labAdapter?.itemList?.add(FunctionBean("蓝牙连接", AppRouterPath.MINE_TO_BLUE_TOOTH))
        labAdapter?.itemList?.add(FunctionBean("UDP连接", AppRouterPath.MINE_TO_CONNECT_UDP))
        labAdapter?.itemList?.add(FunctionBean("协程学习", AppRouterPath.HOME_COROUTINES))
        labAdapter?.itemList?.add(FunctionBean("保存数据", AppRouterPath.STORE_DATA_PAGE))
        labAdapter?.itemList?.add(FunctionBean("讯飞星火", AppRouterPath.APP_AI_CHAT))
        labAdapter?.itemList?.add(FunctionBean("自定义View", AppRouterPath.APP_CUSTOM_VIEW_PAGE))
        labAdapter?.itemList?.add(FunctionBean("短信列表", AppRouterPath.APP_SMS_LIST_PAGE))
    }
}
