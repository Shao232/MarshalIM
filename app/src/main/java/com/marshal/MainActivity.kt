package com.marshal

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.tabs.TabLayoutMediator
import com.marshal.baseview.BaseViewActivity
import com.marshal.databinding.ActivityMainBinding
import com.marshal.mainadapter.MainFragmentAdapter
import com.marshal.mine.MineFragment

/**
 *  ImmersionBar.with(this)
.statusBarColor(com.gamebox.common.R.color.gamebox_only_black)
.fitsSystemWindows(true)//解决状态栏和布局重叠问题，任选其一，默认为false，当为true时一定要指定statusBarColor()，不然状态栏为透明色
.init()
 *
 */

class MainActivity : BaseViewActivity<ActivityMainBinding>() {

    private var fm: FragmentManager? = null
    private var ft: FragmentTransaction? = null

    private var mainAdapter: MainFragmentAdapter? = null
    private var mainFragmentArray:ArrayList<Fragment> = arrayListOf()
    private val mainArray: Array<String> = arrayOf("首页", "我的")

    private var homeFragment:HomeFragment? = null
    private var mineFragment: MineFragment? = null

    private var permissionArray:Array<String> = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

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

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("TAG", "请求读写权限")

            ActivityCompat.requestPermissions(this, permissionArray, 12)
        }
    }

    private fun initAny(){
        fm = supportFragmentManager
        ft = fm?.beginTransaction()
        homeFragment = HomeFragment()
        mineFragment = MineFragment()
        mainAdapter = MainFragmentAdapter(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 12) {
            val dataResults = grantResults.filter { it == PackageManager.PERMISSION_GRANTED }
            Log.d("TAG", "permissions: ${permissions.forEach {Log.d("TAG",it) }}")
            if (dataResults.isNotEmpty()) {
                Log.d("TAG", "权限请求成功!!!!!")
            } else {
                Log.d("TAG", "dataResults is empty ")
            }
        }

    }

}