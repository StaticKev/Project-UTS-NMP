package com.statickev.projectnmp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.statickev.projectnmp.databinding.ActivityMainBinding
import androidx.core.view.get

class  MainActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val fragments: ArrayList<Fragment> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        val prefs = getSharedPreferences("theme_prefs", 0)
        val isDarkMode = prefs.getBoolean("dark_mode", false)

        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode)
                AppCompatDelegate.MODE_NIGHT_YES
            else
                AppCompatDelegate.MODE_NIGHT_NO
        )

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //isi arraylist dengan fragment yg sudah dibuat, gausa newinstance soalnya no need ui gimana"
        fragments.add(HomeFragment())
        fragments.add(FriendsFragment())
        fragments.add(SettingsFragment()) //urutannya ngefek

        //Setting viewpager
        binding.viewPager.adapter = ViewPagerAdapter(this, fragments)
//        binding.viewPager.isUserInputEnabled = true
        //make a connection between vp n bottomnav
        binding.bottomMenu.setOnItemSelectedListener {
            binding.viewPager.currentItem = when(it.itemId) {
                R.id.nav_friends -> 1
                R.id.nav_settings -> 2
                else -> 0
            }
            true
        }
        binding.viewPager.registerOnPageChangeCallback(
            object: ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    binding.bottomMenu.selectedItemId = binding.bottomMenu.menu[position].itemId
                }
            }
        )

//        with(binding.) {
//            layoutManager = LinearLayoutManager(context)
//            setHasFixedSize(true)
//            adapter = StudentAdapter()
//        }
    }
}