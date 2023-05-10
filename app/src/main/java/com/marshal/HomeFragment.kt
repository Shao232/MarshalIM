package com.marshal

import android.view.View
import com.marshal.databinding.FragmentHomeBinding

class HomeFragment:BaseViewFragment<FragmentHomeBinding>() {
    override fun getResLayoutId(): Int = R.layout.fragment_home

    override fun getResLayoutBinding(): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {

    }


}