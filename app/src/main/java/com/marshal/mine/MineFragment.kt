package com.marshal.mine

import NoShakeBtnUtil
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.marshal.IMPath
import com.marshal.R
import com.marshal.base_common.baseview.BaseViewFragment
import com.marshal.databinding.FragmentMineBinding

class MineFragment : BaseViewFragment<FragmentMineBinding>() {

    override fun getResLayoutId(): Int = R.layout.fragment_mine

    override fun getResLayoutBinding(): View? {
        binding = FragmentMineBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {

        binding?.btnToBlueTooth?.setOnClickListener {
            if(NoShakeBtnUtil.isFastDoubleClick(it)) return@setOnClickListener
            ARouter.getInstance().build(IMPath.MINE_TO_BLUE_TOOTH).navigation(mContext)
        }

        binding?.btnToUpd?.setOnClickListener {
            if(NoShakeBtnUtil.isFastDoubleClick(it)) return@setOnClickListener
            ARouter.getInstance().build(IMPath.MINE_TO_CONNECT_UDP).navigation(mContext)
        }

        binding?.btnToReadQuestion?.setOnClickListener {
            if(NoShakeBtnUtil.isFastDoubleClick(it)) return@setOnClickListener
            ARouter.getInstance().build(IMPath.OPEN_QUESTION_BANK).navigation(mContext)
        }

        binding?.ivSettingMine?.setOnClickListener {
            if(NoShakeBtnUtil.isFastDoubleClick(it)) return@setOnClickListener
            ARouter.getInstance().build(IMPath.MINE_SETTING_PAGE).navigation(mContext)
        }
    }
}