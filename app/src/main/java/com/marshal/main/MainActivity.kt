package com.marshal.main

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.tabs.TabLayoutMediator
import com.marshal.base_common.baseview.BaseViewActivity
import com.marshal.databinding.ActivityMainBinding
import com.marshal.main.mainadapter.MainFragmentAdapter
import com.marshal.mine.MineFragment

/**
 *
ImmersionBar.with(this)
.statusBarColor(com.gamebox.common.R.color.gamebox_only_black)
.fitsSystemWindows(true)//解决状态栏和布局重叠问题，任选其一，默认为false，当为true时一定要指定statusBarColor()，不然状态栏为透明色
.init()

全局广播 使用registerReceiver 进行注册
registerReceiver(mainBroadcastReceiver, intentFilter)



 *
 */
class MainActivity : BaseViewActivity<ActivityMainBinding>() {

    private var fm: FragmentManager? = null
    private var ft: FragmentTransaction? = null

    private var mainAdapter: MainFragmentAdapter? = null
    private var mainFragmentArray: ArrayList<Fragment> = arrayListOf()
    private val mainArray: Array<String> = arrayOf("首页", "我的")

    private var homeFragment: HomeFragment? = null
    private var mineFragment: MineFragment? = null

    private var intentStartMainService: Intent? = null

    private var permissionArray: Array<String> = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.READ_CALENDAR,
        Manifest.permission.WRITE_CALENDAR,
        Manifest.permission.READ_SMS
    )

    private var mainBroadcastReceiver: MainBroadcastReceiver? = null
    private val mainViewModel: MainViewModel by viewModels()


    override fun getResLayoutBinding(): View? {
        binding = ActivityMainBinding.inflate(layoutInflater)
        return binding?.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun initView() {
        initFragment()

        mainFragmentArray.add(homeFragment ?: return)
        mainFragmentArray.add(mineFragment ?: return)
        mainAdapter?.itemList?.add(mainFragmentArray[0])
        mainAdapter?.itemList?.add(mainFragmentArray[1])

        binding?.viewPager2?.adapter = mainAdapter
        //TabLayoutMediator  连接tablayout和viewpage2的中介人
        TabLayoutMediator(binding?.tabLayout ?: return, binding?.viewPager2 ?: return)
        { tab, position ->
            tab.text = mainArray[position]
        }.attach()

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            Log.d("TAG", "-----请求权限-----")
            ActivityCompat.requestPermissions(this, permissionArray, 12)
        } else {
            intentStartMainService = Intent(this, MainService::class.java)
            startService(intentStartMainService)
        }

        initHttp()

        initChat()

        mainBroadcastReceiver = MainBroadcastReceiver(this)
        val intentFilter = IntentFilter()
        intentFilter.addAction("com.marshal.login.user")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(mainBroadcastReceiver, intentFilter,Context.RECEIVER_NOT_EXPORTED)
        }else {
            registerReceiver(mainBroadcastReceiver, intentFilter)
        }
    }

    private fun initChat() {

    }

    private fun initServer() {
    }

    private fun initHttp() {
    }


    private fun initFragment() {
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
            permissions.forEach { Log.d("TAG", "permissions: $it") }

            if (dataResults.isNotEmpty()) {
                Log.d("TAG", "权限请求成功!!!!!")
                intentStartMainService = Intent(this, MainService::class.java)
                startService(intentStartMainService)
            } else {
                Log.d("TAG", "dataResults is empty ")
            }
        }
    }

    class MainBroadcastReceiver(private val activity: MainActivity) : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            //当登录成功 登录界面会发送登录成功的广播
            //在主页收到登录成功的广播发送viewmodel字段post
            //刷新ui
            if (intent?.action == "com.marshal.login.user") {
                Log.d("TAG", "登录成功发送的广播")
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        if (intentStartMainService != null) {
            stopService(intentStartMainService)
        }
    }


}