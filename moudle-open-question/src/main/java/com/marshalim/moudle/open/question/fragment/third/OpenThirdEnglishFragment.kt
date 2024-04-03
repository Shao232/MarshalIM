package com.marshalim.moudle.open.question.fragment.third

import android.view.View
import com.marshal.base_common.baseview.BaseViewFragment
import com.marshalim.moudle.open.question.R
import com.marshalim.moudle.open.question.databinding.FragmentOpenThirdEnglishBinding

class OpenThirdEnglishFragment : BaseViewFragment<FragmentOpenThirdEnglishBinding>() {


    override fun getResLayoutId(): Int? = R.layout.fragment_open_third_english

    override fun getResLayoutBinding(): View? {
        binding = FragmentOpenThirdEnglishBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {

    }
}