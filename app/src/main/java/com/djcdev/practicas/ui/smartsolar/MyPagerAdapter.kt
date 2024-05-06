package com.djcdev.practicas.ui.smartsolar

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyPagerAdapter(manager: FragmentActivity) :
    FragmentStateAdapter(manager) {

    private val fragmentList = mutableListOf<Fragment>()
    private val fragmentTitleList = mutableListOf<String>()

    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        fragmentTitleList.add(title)
    }
    override fun getItemCount(): Int = fragmentList.size

    fun getTabTitle(position : Int): String{
        return fragmentTitleList[position]
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

}
