package com.marshal.main

import NoShakeBtnUtil
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.driving_school.DrivingRouterPath
import com.marshal.AppRouterPath
import com.marshal.R
import com.marshal.android.WanAndroidRouterPath
import com.marshal.base_common.baseview.BaseViewFragment
import com.marshal.databinding.FragmentHomeBinding
import com.marshal.moudle.calendar.schedule.utils.CalendarRouterPath
import com.marshal.moudle.ledger.LedgerRouterPath
import com.marshal.moudle.ledger.db.LedgerBean
import com.marshal.moudle.ledger.db.LedgerRoomUtil
import java.util.Calendar


class HomeFragment : BaseViewFragment<FragmentHomeBinding>() {
    override fun getResLayoutId(): Int = R.layout.fragment_home

    override fun getResLayoutBinding(): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding?.root
    }

    // 首页是 Tab 页，不要标题栏：base 的 toolbar 会强制显示返回键，点了会直接关掉 MainActivity
    override fun hasToolbar(): Boolean = false

    override fun initView() {
        // 概览卡：玩安卓热榜
        binding?.cardWanHot?.setOnClickListener {
            if (NoShakeBtnUtil.isFastDoubleClick(it)) return@setOnClickListener
            ARouter.getInstance().build(WanAndroidRouterPath.WAN_ANDROID_Home).navigation(mContext)
        }

        // 概览卡：今日支出 → 记账首页
        binding?.cardTodayExpense?.setOnClickListener {
            if (NoShakeBtnUtil.isFastDoubleClick(it)) return@setOnClickListener
            ARouter.getInstance().build(LedgerRouterPath.LEDGER_HOME).navigation(mContext)
        }

        // 概览卡：今日日程 → 添加日程
        binding?.cardTodaySchedule?.setOnClickListener {
            if (NoShakeBtnUtil.isFastDoubleClick(it)) return@setOnClickListener
            ARouter.getInstance().build(CalendarRouterPath.APP_ADD_EVENT_CALENDAR).navigation(mContext)
        }

        // 概览卡：今日刷题 → 驾校做题
        binding?.cardTodayQuestion?.setOnClickListener {
            if (NoShakeBtnUtil.isFastDoubleClick(it)) return@setOnClickListener
            ARouter.getInstance().build(DrivingRouterPath.Driving_HOME_PATH).navigation(mContext)
        }

        // 常用功能：记账 / 驾校 / 玩安卓 / 音乐 / 日程
        binding?.fnLedger?.setOnClickListener {
            if (NoShakeBtnUtil.isFastDoubleClick(it)) return@setOnClickListener
            ARouter.getInstance().build(LedgerRouterPath.LEDGER_HOME).navigation(mContext)
        }
        binding?.fnDriving?.setOnClickListener {
            if (NoShakeBtnUtil.isFastDoubleClick(it)) return@setOnClickListener
            ARouter.getInstance().build(DrivingRouterPath.Driving_HOME_PATH).navigation(mContext)
        }
        binding?.fnWan?.setOnClickListener {
            if (NoShakeBtnUtil.isFastDoubleClick(it)) return@setOnClickListener
            ARouter.getInstance().build(WanAndroidRouterPath.WAN_ANDROID_Home).navigation(mContext)
        }
        binding?.fnMusic?.setOnClickListener {
            if (NoShakeBtnUtil.isFastDoubleClick(it)) return@setOnClickListener
            ARouter.getInstance().build(AppRouterPath.APP_MUSIC_PAGE).navigation(mContext)
        }
        binding?.fnSchedule?.setOnClickListener {
            if (NoShakeBtnUtil.isFastDoubleClick(it)) return@setOnClickListener
            ARouter.getInstance().build(CalendarRouterPath.APP_ADD_EVENT_CALENDAR).navigation(mContext)
        }

        // 更多 → 金刚页
        binding?.tvMoreFunction?.setOnClickListener {
            if (NoShakeBtnUtil.isFastDoubleClick(it)) return@setOnClickListener
            ARouter.getInstance().build(AppRouterPath.APP_FUNCTION_PAGE).navigation(mContext)
        }

        loadTodayExpense()
    }

    /**
     * 今日支出：子线程查记账库，回主线程刷 UI
     */
    private fun loadTodayExpense() {
        Thread {
            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
            val dayStart = calendar.timeInMillis
            val dayEnd = dayStart + 24 * 60 * 60 * 1000 - 1
            val sum = LedgerRoomUtil.getLedgerDao()
                .sumByRange(LedgerBean.TYPE_EXPENSE, dayStart, dayEnd)
            activity?.runOnUiThread {
                binding?.tvExpenseValue?.text = String.format("¥%.2f", sum)
            }
        }.start()
    }
}
