package com.driving_school.home

import android.view.View
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.driving_school.DrivingRouterPath.Driving_HOME_PATH
import com.driving_school.R
import com.driving_school.databinding.ActivityDrivingSchoolHomeBinding
import com.marshal.base_common.baseview.BaseViewActivity


@Route(path = Driving_HOME_PATH)
class DrivingSchoolHomeActivity : BaseViewActivity<ActivityDrivingSchoolHomeBinding>() {

    override fun hasToolbar(): Boolean = true

    private var homeFragment:DrivingHomeFragment? = null
    private val viewModel:DrivingSchoolViewModel by viewModels()

    override fun getResLayoutBinding(): View? {
        binding = ActivityDrivingSchoolHomeBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {
        if (hasIncludeToolbar) {
            setTitle("驾校模拟")
        }
        viewModel.initSubjectData()

        homeFragment = DrivingHomeFragment()
        if(homeFragment?.isAdded == false) {
            supportFragmentManager.beginTransaction().add(R.id.fln_driving_main,homeFragment?:return,
                "fragment_drivingHome").commitAllowingStateLoss()
        }


    }
}