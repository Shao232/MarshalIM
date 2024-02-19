package com.marshalim.moudle.open.question.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.marshal.base_common.baseview.BaseViewActivity
import com.marshalim.moudle.open.question.R
import com.marshalim.moudle.open.question.databinding.ActivityOpenFragmentBinding

class OpenFragmentActivity:BaseViewActivity<ActivityOpenFragmentBinding>() {


    companion object{
        private var fragment:Fragment? = null

        fun startFragment(context: Context, newFragment:Fragment){
            fragment = newFragment
            val intent: Intent = Intent(context,OpenFragmentActivity::class.java)
            context.startActivity(intent)
        }
    }

    private var fragmentTransaction:FragmentTransaction? = null

    override fun getResLayoutBinding(): View? {
        binding = ActivityOpenFragmentBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initView() {

        fragmentTransaction = supportFragmentManager.beginTransaction()

        if(fragment!=null) {
            fragmentTransaction?.replace(R.id.frame_layout, fragment?:return,"frag")
                ?.commitNowAllowingStateLoss()
        }
    }
}