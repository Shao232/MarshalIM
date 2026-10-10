package com.marshal.moudle.ledger.ui

import android.app.Activity
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.marshal.base_common.baseview.BaseViewActivity
import com.marshal.moudle.ledger.LedgerRouterPath
import com.marshal.moudle.ledger.R
import com.marshal.moudle.ledger.adapter.LedgerCategoryAdapter
import com.marshal.moudle.ledger.databinding.ActivityAddLedgerBinding
import com.marshal.moudle.ledger.db.LedgerBean
import com.marshal.moudle.ledger.db.LedgerRoomUtil
import com.marshal.moudle.ledger.utils.LedgerCategory
import com.marshal.moudle.ledger.utils.LedgerTimeUtils

/**
 * 记一笔：大金额 + 收支分段 + 九宫格分类
 */
@Route(path = LedgerRouterPath.LEDGER_ADD)
class AddLedgerActivity : BaseViewActivity<ActivityAddLedgerBinding>() {

    private var categoryAdapter: LedgerCategoryAdapter? = null
    private var currentCategory: String = LedgerCategory.all().first()
    private var currentType: Int = LedgerBean.TYPE_EXPENSE

    override fun hasToolbar(): Boolean = true

    override fun getResLayoutBinding(): View? {
        binding = ActivityAddLedgerBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {
        if (hasIncludeToolbar) {
            setTitle(getString(R.string.ledger_add_title))
        }

        currentCategory = LedgerCategory.all().first()
        binding?.tvLedgerDate?.text = getString(R.string.ledger_date_today) +
                " " + LedgerTimeUtils.fullLabel(System.currentTimeMillis())

        initTypeSegment()
        initCategoryGrid()
        initSave()
    }

    private fun initTypeSegment() {
        binding?.tvTypeExpense?.setOnClickListener {
            switchType(LedgerBean.TYPE_EXPENSE)
        }
        binding?.tvTypeIncome?.setOnClickListener {
            switchType(LedgerBean.TYPE_INCOME)
        }
        switchType(LedgerBean.TYPE_EXPENSE)
    }

    private fun switchType(type: Int) {
        currentType = type
        val isExpense = type == LedgerBean.TYPE_EXPENSE
        binding?.tvTypeExpense?.isSelected = isExpense
        binding?.tvTypeIncome?.isSelected = !isExpense
    }

    private fun initCategoryGrid() {
        categoryAdapter = LedgerCategoryAdapter { name -> currentCategory = name }
        binding?.recyclerCategory?.layoutManager = GridLayoutManager(this, 3)
        binding?.recyclerCategory?.adapter = categoryAdapter
        binding?.recyclerCategory?.isNestedScrollingEnabled = false
        categoryAdapter?.addListAll(ArrayList(LedgerCategory.all()))
    }

    private fun initSave() {
        binding?.btnSaveLedger?.setOnClickListener {
            val amount = binding?.etLedgerAmount?.text?.toString()?.trim()?.toDoubleOrNull()
            if (amount == null || amount <= 0) {
                showToast(getString(R.string.ledger_amount_error))
                return@setOnClickListener
            }

            val bean = LedgerBean(
                amount = amount,
                category = categoryAdapter?.selectedItem() ?: currentCategory,
                remark = binding?.etLedgerRemark?.text?.toString()?.trim() ?: "",
                createTime = System.currentTimeMillis(),
                type = currentType
            )

            // 子线程落库，主线程收尾（lifecycleScope 在本模块依赖链上解析不到，统一走 Thread）
            Thread {
                LedgerRoomUtil.getLedgerDao().insert(bean)
                runOnUiThread {
                    setResult(Activity.RESULT_OK)
                    finish()
                }
            }.start()
        }
    }
}
