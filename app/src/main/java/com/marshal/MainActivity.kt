package com.marshal

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.tabs.TabLayoutMediator
import com.marshal.baseview.BaseViewActivity
import com.marshal.databinding.ActivityMainBinding
import com.marshal.mainadapter.MainFragmentAdapter

class MainActivity : BaseViewActivity<ActivityMainBinding>() {

    private var fm: FragmentManager? = null
    private var ft: FragmentTransaction? = null

    private var mainAdapter: MainFragmentAdapter? = null
    private var mainFragmentArray:ArrayList<Fragment> = arrayListOf()
    private val mainArray: Array<String> = arrayOf("首页", "我的")

    private var homeFragment:HomeFragment? = null
    private var mineFragment:MineFragment? = null

    override fun getResLayoutBinding(): View? {
        binding = ActivityMainBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {

        initAny()

        mainFragmentArray.add(homeFragment?:return)
        mainFragmentArray.add(mineFragment?:return)
        mainAdapter?.itemList?.add(mainFragmentArray[0])
        mainAdapter?.itemList?.add(mainFragmentArray[1])
        binding?.viewPager2?.adapter = mainAdapter
        //TabLayoutMediator  连接tablayout和viewpage2的中介人
        TabLayoutMediator(binding?.tabLayout?:return,binding?.viewPager2?:return)
        { tab, position ->
        tab.text =   mainArray[position]
        }.attach()

    }

    private fun initAny(){
        fm = supportFragmentManager
        ft = fm?.beginTransaction()
        homeFragment = HomeFragment()
        mineFragment = MineFragment()
        mainAdapter = MainFragmentAdapter(this)
    }


}