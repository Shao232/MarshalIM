package com.marshal.android.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class WanAndroidFragmentAdapter(manager:FragmentActivity):FragmentStateAdapter(manager) {

    val itemList:ArrayList<Fragment> = arrayListOf()

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun createFragment(position: Int): Fragment {
        return itemList[position]
    }

}