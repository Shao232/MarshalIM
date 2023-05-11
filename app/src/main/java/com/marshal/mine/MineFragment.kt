package com.marshal.mine

import android.util.Log
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.marshal.IMPath
import com.marshal.R
import com.marshal.baseview.BaseViewFragment
import com.marshal.databinding.FragmentMineBinding

class MineFragment: BaseViewFragment<FragmentMineBinding>() {

    override fun getResLayoutId(): Int = R.layout.fragment_mine

    override fun getResLayoutBinding(): View? {
        binding = FragmentMineBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {

        binding?.btnToBlueTooth?.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {

                ARouter.getInstance().build(IMPath.MINE_TO_BLUE_TOOTH).navigation(mContext)
            }

        })
    }


}