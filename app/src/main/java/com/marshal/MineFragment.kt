package com.marshal

import android.view.View
import com.marshal.baseview.BaseViewFragment
import com.marshal.databinding.FragmentMineBinding

class MineFragment: BaseViewFragment<FragmentMineBinding>() {

    override fun getResLayoutId(): Int = R.layout.fragment_mine

    override fun getResLayoutBinding(): View? {
        binding = FragmentMineBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {

    }


}