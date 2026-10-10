package com.marshal.mine

import NoShakeBtnUtil
import android.view.View
import androidx.fragment.app.activityViewModels
import com.alibaba.android.arouter.launcher.ARouter
import com.driving_school.DrivingRouterPath
import com.marshal.AppRouterPath
import com.marshal.R
import com.marshal.android.WanAndroidRouterPath
import com.marshal.base_common.baseview.BaseViewFragment
import com.marshal.base_common.store.getAppAppLoginUserAccount
import com.marshal.base_common.store.putAppLoginUserAccount
import com.marshal.base_common.store.putAppLoginUserPwd
import com.marshal.databinding.FragmentMineBinding
import com.marshal.main.MainViewModel
import com.marshal.moudle.calendar.schedule.putAppInfoToken

class MineFragment : BaseViewFragment<FragmentMineBinding>() {

    override fun getResLayoutId(): Int = R.layout.fragment_mine

    private val viewModel: MainViewModel by activityViewModels()

    override fun getResLayoutBinding(): View? {
        binding = FragmentMineBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {
        fillUserInfo()

        binding?.ivSettingMine?.setOnClickListener {
            if (NoShakeBtnUtil.isFastDoubleClick(it)) return@setOnClickListener
            ARouter.getInstance().build(AppRouterPath.MINE_SETTING_PAGE).navigation(mContext)
        }

        binding?.btnToAboutVersion?.setOnClickListener {
            if (NoShakeBtnUtil.isFastDoubleClick(it)) return@setOnClickListener
            ARouter.getInstance().build(AppRouterPath.ABOUT_APP_PAGE).navigation(mContext)
        }

        binding?.rowDrivingError?.setOnClickListener {
            if (NoShakeBtnUtil.isFastDoubleClick(it)) return@setOnClickListener
            ARouter.getInstance().build(DrivingRouterPath.Driving_Collection_Error_Path).navigation(mContext)
        }

        binding?.rowWanAndroid?.setOnClickListener {
            if (NoShakeBtnUtil.isFastDoubleClick(it)) return@setOnClickListener
            ARouter.getInstance().build(WanAndroidRouterPath.WAN_ANDROID_Home).navigation(mContext)
        }

        binding?.btnToLogout?.setOnClickListener {
            putAppLoginUserAccount("")
            putAppLoginUserPwd("")
            putAppInfoToken("")
            fillUserInfo()
            showToast("退出登录成功")
        }

    }

    /**
     * 品牌卡用户名：没登录 / 账号为空时给个兜底，不留空白
     */
    private fun fillUserInfo() {
        val account = getAppAppLoginUserAccount()
        binding?.tvNickNameMine?.text =
            if (account.isNullOrEmpty()) "未登录" else account
    }
}