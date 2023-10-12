package com.marshal.mine

import NoShakeBtnUtil
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import com.alibaba.android.arouter.launcher.ARouter
import com.marshal.AppRouterPath
import com.marshal.R
import com.marshal.base_common.baseview.BaseViewFragment
import com.marshal.base_common.store.getAppAppLoginUserAccount
import com.marshal.base_common.store.putAppLoginUserAccount
import com.marshal.base_common.store.putAppLoginUserPwd
import com.marshal.databinding.FragmentMineBinding
import com.marshal.main.MainViewModel

class MineFragment : BaseViewFragment<FragmentMineBinding>() {

    override fun getResLayoutId(): Int = R.layout.fragment_mine

    private val viewModel: MainViewModel by activityViewModels()

    override fun getResLayoutBinding(): View? {
        binding = FragmentMineBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {

        binding?.tvNickNameMine?.text = getAppAppLoginUserAccount()

        binding?.ivSettingMine?.setOnClickListener {
            if (NoShakeBtnUtil.isFastDoubleClick(it)) return@setOnClickListener
            ARouter.getInstance().build(AppRouterPath.MINE_SETTING_PAGE).navigation(mContext)
        }

        binding?.btnToAboutVersion?.setOnClickListener {
            if (NoShakeBtnUtil.isFastDoubleClick(it)) return@setOnClickListener
            ARouter.getInstance().build(AppRouterPath.ABOUT_APP_PAGE).navigation(mContext)
        }

        binding?.btnToLogout?.setOnClickListener {
            putAppLoginUserAccount("")
            putAppLoginUserPwd("")
            showToast("退出登录成功")
        }

    }
}