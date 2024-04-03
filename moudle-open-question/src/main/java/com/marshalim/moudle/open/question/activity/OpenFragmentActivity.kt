package com.marshalim.moudle.open.question.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.marshal.base_common.baseview.BaseViewActivity
import com.marshalim.moudle.open.question.R
import com.marshalim.moudle.open.question.databinding.ActivityOpenFragmentBinding
import com.marshalim.moudle.open.question.fragment.first.OpenAppFunctionFragment
import com.marshalim.moudle.open.question.fragment.first.OpenProgramDesignFragment
import com.marshalim.moudle.open.question.fragment.first.OpenThoughtFragment
import com.marshalim.moudle.open.question.fragment.second.OpenSecondEnglishFragment
import com.marshalim.moudle.open.question.fragment.third.OpenDatabaseFragment
import com.marshalim.moudle.open.question.fragment.third.OpenSoftwareFragment
import com.marshalim.moudle.open.question.fragment.third.OpenThirdEnglishFragment

class OpenFragmentActivity:BaseViewActivity<ActivityOpenFragmentBinding>() {

    companion object{
        fun startFragment(context: Context, newFragmentName:String){
            val intent = Intent(context,OpenFragmentActivity::class.java)
            intent.putExtra("fragmentName",newFragmentName)
            context.startActivity(intent)
        }
    }

    private var fragmentTransaction:FragmentTransaction? = null

    override fun getResLayoutBinding(): View? {
        binding = ActivityOpenFragmentBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {

        var fragmentName = ""
        if(intent.getStringExtra("fragmentName") !=null) {
            fragmentName = intent.getStringExtra("fragmentName") ?:""
        }

        fragmentTransaction = supportFragmentManager.beginTransaction()
        if(fragmentName.isNotEmpty()) {
            var fragment:Fragment?= null
            when(fragmentName) {
                OpenProgramDesignFragment::class.java.simpleName ->{
                    fragment = OpenProgramDesignFragment()
                }
                OpenThoughtFragment::class.java.simpleName ->{
                    fragment = OpenThoughtFragment()
                }
                OpenAppFunctionFragment::class.java.simpleName->{
                    fragment = OpenAppFunctionFragment()
                }
                OpenSecondEnglishFragment::class.java.simpleName->{
                    fragment = OpenSecondEnglishFragment()
                }
                OpenSoftwareFragment::class.java.simpleName->{
                    fragment = OpenSoftwareFragment()
                }
                OpenDatabaseFragment::class.java.simpleName->{
                    fragment = OpenDatabaseFragment()
                }
                OpenThirdEnglishFragment::class.java.simpleName->{
                    fragment = OpenThirdEnglishFragment()
                }

            }
            fragmentTransaction?.replace(R.id.frame_layout, fragment?:return,"frag")
                ?.commitNowAllowingStateLoss()
        }
    }
}