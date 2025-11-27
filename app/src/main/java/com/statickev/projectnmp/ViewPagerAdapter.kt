package com.statickev.projectnmp

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(val activity: AppCompatActivity, val fragments: ArrayList<Fragment>): FragmentStateAdapter(activity) {
    override fun createFragment(position: Int) = fragments[position]
    override fun getItemCount()= fragments.size
}
