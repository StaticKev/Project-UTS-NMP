package com.statickev.projectnmp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.statickev.projectnmp.databinding.ActivityMainBinding

class  MainActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val fragments: ArrayList<Fragment> = ArrayList()

    companion object {
        var FRIEND_COUNT = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //isi arraylist dengan fragment yg sudah dibuat, gausa newinstance soalnya no need ui gimana"
        fragments.add(HomeFragment())
        fragments.add(FriendsFragment())
        fragments.add(SettingsFragment()) //urutannya ngefek

        //Setting viewpager
        binding.viewPager.adapter= ViewPagerAdapter(this,fragments)
        binding.viewPager.isUserInputEnabled = true
        //make a connection between vp n bottomnav
        //if swiped, the bottom nav should change its focus accordingly
        binding.viewPager.registerOnPageChangeCallback(object:
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.bottomMenu.selectedItemId =
                    binding.bottomMenu.menu.getItem(position).itemId
            // Or: binding.bottomNav.selectedItemId = binding.bottomNav.menu[position].itemId
            }
        })
        binding.bottomMenu.setOnItemSelectedListener {
            binding.viewPager.currentItem = when(it.itemId) {
                R.id.home -> 0
                R.id.friends -> 1
                R.id.settings -> 2
                else -> 0 // default to home
            }
            true
        }
//        with(binding.) {
//            layoutManager = LinearLayoutManager(context)
//            setHasFixedSize(true)
//            adapter = StudentAdapter()
//        }
    }
}