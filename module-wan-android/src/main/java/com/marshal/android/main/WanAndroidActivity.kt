package com.marshal.android.main

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.tabs.TabLayoutMediator
import com.marshal.android.WanAndroidRouterPath.WAN_ANDROID_Home
import com.marshal.android.WanHomeFragment
import com.marshal.android.WanKnowledgeFragment
import com.marshal.android.WanMineFragment
import com.marshal.android.WanNavFragment
import com.marshal.android.WanWxArticleFragment
import com.marshal.base_common.baseview.BaseViewActivity
import com.marshal.module.wan.android.databinding.ActivityWanAndroidBinding

@Route(path = WAN_ANDROID_Home)
class WanAndroidActivity : BaseViewActivity<ActivityWanAndroidBinding>() {

    private var fm: FragmentManager? = null
    private var ft: FragmentTransaction? = null
    private var wanAndroidAdapter:WanAndroidFragmentAdapter? = null
    private lateinit var wanHomeFragment: WanHomeFragment
    private lateinit var wanKnowledgeFragment:WanKnowledgeFragment
    private lateinit var wanWxArticleFragment: WanWxArticleFragment
    private lateinit var wanNavFragment: WanNavFragment
    private lateinit var wanMineFragment: WanMineFragment

    private var wanFragmentArray: ArrayList<Fragment> = arrayListOf()
    private val wanArray: Array<String> = arrayOf("首页","知识体系","公众号","导航", "我的")


    override fun getResLayoutBinding(): View? {
        binding = ActivityWanAndroidBinding.inflate(layoutInflater)
        return binding?.root
    }



    override fun initView() {
        wanAndroidAdapter = WanAndroidFragmentAdapter(this)
        wanHomeFragment = WanHomeFragment()
        wanKnowledgeFragment = WanKnowledgeFragment()
        wanWxArticleFragment = WanWxArticleFragment()
        wanNavFragment = WanNavFragment()
        wanMineFragment = WanMineFragment()
        wanFragmentArray.add(wanHomeFragment)
        wanFragmentArray.add(wanKnowledgeFragment)
        wanFragmentArray.add(wanWxArticleFragment)
        wanFragmentArray.add(wanNavFragment)
        wanFragmentArray.add(wanMineFragment)

        wanAndroidAdapter?.itemList?.addAll(wanFragmentArray)

        binding?.apply {
            viewPagerWanMain.adapter = wanAndroidAdapter
            TabLayoutMediator(tabLayoutWanMain, viewPagerWanMain)
            { tab, position ->
                tab.text = wanArray[position]
            }.attach()
        }



    }
}