package com.marshal.moudle.ledger.ui

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.marshal.base_common.baseview.BaseViewActivity
import com.marshal.moudle.ledger.LedgerRouterPath
import com.marshal.moudle.ledger.R
import com.marshal.moudle.ledger.adapter.LedgerListAdapter
import com.marshal.moudle.ledger.databinding.ActivityLedgerHomeBinding
import com.marshal.moudle.ledger.db.LedgerBean
import com.marshal.moudle.ledger.db.LedgerRoomUtil
import com.marshal.moudle.ledger.pojo.LedgerListItem
import com.marshal.moudle.ledger.utils.LedgerBudget
import com.marshal.moudle.ledger.utils.LedgerTimeUtils
import java.util.Calendar

/**
 * 记账首页：本月汇总卡 + 预算进度 + 按天分组流水
 */
@Route(path = LedgerRouterPath.LEDGER_HOME)
class LedgerHomeActivity : BaseViewActivity<ActivityLedgerHomeBinding>() {

    private var adapter: LedgerListAdapter? = null

    override fun hasToolbar(): Boolean = true

    override fun getResLayoutBinding(): View? {
        binding = ActivityLedgerHomeBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {
        if (hasIncludeToolbar) {
            setTitle(getString(R.string.ledger_home_title))
        }

        adapter = LedgerListAdapter()
        binding?.recyclerLedger?.layoutManager = LinearLayoutManager(this)
        binding?.recyclerLedger?.adapter = adapter

        binding?.fabAddLedger?.setOnClickListener {
            ARouter.getInstance()
                .build(LedgerRouterPath.LEDGER_ADD)
                .navigation(this@LedgerHomeActivity, REQUEST_ADD)
        }

        loadData()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ADD && resultCode == Activity.RESULT_OK) {
            loadData()
        }
    }

    private fun loadData() {
        Thread {
            val start = LedgerTimeUtils.monthStart()
            val end = LedgerTimeUtils.monthEnd()
            val dao = LedgerRoomUtil.getLedgerDao()
            val list = dao.queryByRange(start, end)
            val expense = dao.sumByRange(LedgerBean.TYPE_EXPENSE, start, end)
            val income = dao.sumByRange(LedgerBean.TYPE_INCOME, start, end)

            runOnUiThread {
                adapter?.addListAll(buildItems(list))
                fillSummary(expense, income)
                binding?.tvEmptyTip?.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
            }
        }.start()
    }

    private fun fillSummary(expense: Double, income: Double) {
        binding?.tvMonthExpense?.text = String.format("%.2f", expense)
        binding?.tvMonthIncome?.text =
            getString(R.string.ledger_month_income, String.format("%.2f", income))
        binding?.tvMonthBalance?.text =
            getString(R.string.ledger_month_balance, String.format("%.2f", income - expense))

        val percent = ((expense / LedgerBudget.MONTH_TOTAL) * 100).toInt().coerceAtMost(100)
        binding?.progressBudget?.progress = percent
        binding?.tvBudgetTip?.text = getString(
            R.string.ledger_budget_tip,
            String.format("%.0f", LedgerBudget.MONTH_TOTAL),
            percent
        )
    }

    private fun buildItems(list: MutableList<LedgerBean>): ArrayList<LedgerListItem> {
        val result = ArrayList<LedgerListItem>()
        val groups = ArrayList<Pair<Long, MutableList<LedgerBean>>>()

        list.sortedByDescending { it.createTime }.forEach { bean ->
            val day = dayStart(bean.createTime)
            val last = groups.lastOrNull()
            if (last != null && last.first == day) {
                last.second.add(bean)
            } else {
                groups.add(day to mutableListOf(bean))
            }
        }

        groups.forEach { (day, items) ->
            val dayExpense = items.filter { it.type == LedgerBean.TYPE_EXPENSE }
                .sumOf { it.amount }
            result.add(
                LedgerListItem(
                    type = LedgerListItem.TYPE_DATE,
                    date = day,
                    dayCount = items.size,
                    dayTotal = dayExpense
                )
            )
            items.forEach { result.add(LedgerListItem(type = LedgerListItem.TYPE_RECORD, bean = it)) }
        }
        return result
    }

    private fun dayStart(time: Long): Long {
        val calendar = Calendar.getInstance().apply { timeInMillis = time }
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    companion object {
        const val REQUEST_ADD = 1001
    }
}
